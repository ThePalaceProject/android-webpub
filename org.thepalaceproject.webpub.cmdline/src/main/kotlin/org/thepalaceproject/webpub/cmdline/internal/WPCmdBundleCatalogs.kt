package org.thepalaceproject.webpub.cmdline.internal

import com.fasterxml.jackson.databind.json.JsonMapper
import com.io7m.quarrel.core.QCommandContextType
import com.io7m.quarrel.core.QCommandMetadata
import com.io7m.quarrel.core.QCommandStatus
import com.io7m.quarrel.core.QCommandType
import com.io7m.quarrel.core.QParameterNamed1
import com.io7m.quarrel.core.QParameterNamedType
import com.io7m.quarrel.core.QStringType
import com.io7m.quarrel.ext.logback.QLogback
import org.slf4j.LoggerFactory
import org.sqlite.SQLiteConfig
import org.sqlite.SQLiteDataSource
import org.sqlite.SQLiteOpenMode
import org.thepalaceproject.webpub.cmdline.WPVersion
import org.thepalaceproject.webpub.core.WPMManifest
import org.thepalaceproject.webpub.core.WPMMappers
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class WPCmdBundleCatalogs : QCommandType {

  private lateinit var visited : ConcurrentHashMap.KeySetView<URI, Boolean>
  private lateinit var mapper : JsonMapper
  private lateinit var client : HttpClient
  private val manifests = ConcurrentLinkedQueue<WPMManifest>()

  private val logger =
    LoggerFactory.getLogger(WPCmdBundleCatalogs::class.java)

  private val metadata : QCommandMetadata =
    QCommandMetadata(
      "bundle-catalogs",
      QStringType.QConstant("Bundle catalogs into an SQLite database"),
      Optional.empty()
    )

  private val START_ADDRESS =
    QParameterNamed1(
      "--start",
      mutableListOf(),
      QStringType.QConstant("The starting address."),
      Optional.empty(),
      URI::class.java
    )

  private val OUTPUT_FILE =
    QParameterNamed1(
      "--output-file",
      mutableListOf(),
      QStringType.QConstant("The output file."),
      Optional.empty(),
      Path::class.java
    )

  override fun onListNamedParameters() : List<QParameterNamedType<*>> {
    return QLogback.plusParameters(
      listOf(
        this.START_ADDRESS,
        this.OUTPUT_FILE
      )
    )
  }

  override fun onExecute(
    context : QCommandContextType
  ) : QCommandStatus {
    val start =
      context.parameterValue(this.START_ADDRESS)
    val outputFile =
      context.parameterValue(this.OUTPUT_FILE)

    this.visited =
      ConcurrentHashMap.newKeySet()
    this.mapper =
      WPMMappers.createMapper()
    this.client =
      HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build()

    this.process(start)
    this.writeDatabase(outputFile)
    return QCommandStatus.SUCCESS
  }

  private fun writeDatabase(
    outputFile : Path
  ) {
    val config = SQLiteConfig()
    config.setLockingMode(SQLiteConfig.LockingMode.NORMAL)
    config.setJournalMode(SQLiteConfig.JournalMode.WAL)
    config.setOpenMode(SQLiteOpenMode.CREATE)
    config.setOpenMode(SQLiteOpenMode.READWRITE)

    val dataSource = SQLiteDataSource(config)
    dataSource.url = "jdbc:sqlite:${outputFile}"

    dataSource.connection.use { connection ->
      connection.autoCommit = false
      connection.createStatement().use { statement ->
        statement.execute("""
CREATE TABLE account_provider_descriptions (
  apd_id                 TEXT    NOT NULL,
  apd_updated_time_last  TEXT    NOT NULL,
  apd_title              TEXT    NOT NULL,
  apd_description        TEXT    NOT NULL,
  apd_data_format        TEXT    NOT NULL,
  apd_data               BLOB    NOT NULL,

  CONSTRAINT account_provider_descriptions_primary_key
    PRIMARY KEY (apd_id)
) STRICT
        """.trimIndent())
        connection.commit()
      }

      connection.prepareStatement("""
INSERT INTO account_provider_descriptions (
  apd_id,
  apd_updated_time_last,
  apd_title,
  apd_description,
  apd_data_format,
  apd_data
) VALUES (
  ?,
  ?,
  ?,
  ?,
  ?,
  ?
)
      """.trimIndent()).use { statement ->
        var count = 0
        for (manifest in this.manifests) {
          for (catalog in manifest.catalogs) {
            val data = WPBundleProtobufs.catalogToP1(catalog)
            val dataBytes = data.toByteArray()
            val timestamp = catalog.metadata.updated.toString()
            statement.setString(1, catalog.metadata.identifier!!.toString())
            statement.setString(2, catalog.metadata.title.defaultValue)
            statement.setString(3, catalog.metadata.description)
            statement.setString(4, timestamp)
            statement.setString(5, "DBSerializationProto1")
            statement.setBytes(6, dataBytes)
            statement.addBatch()
            ++count
          }
        }

        this.logger.debug("Committing {} providers", count)
        statement.executeBatch()
        connection.commit()
      }
    }
  }

  private fun process(
    target : URI
  ) {
    if (this.visited.contains(target)) {
      this.logger.info("[{}] Already visited, ignoring.", target)
      return
    }
    this.visited.add(target)

    val outFeedFile =
      Files.createTempFile("wpm", ".json")
    val outLinkFile =
      Files.createTempFile("wpm", ".link")

    this.logger.info("[{}] Download to {}", target, outFeedFile)
    Files.createDirectories(outFeedFile.parent)
    Files.writeString(outLinkFile, target.toString())

    val request =
      HttpRequest.newBuilder(target)
        .header("User-Agent", this.userAgent())
        .build()

    val response =
      this.client.send(
        request,
        HttpResponse.BodyHandlers.ofFile(
          outFeedFile,
          StandardOpenOption.CREATE,
          StandardOpenOption.WRITE,
          StandardOpenOption.TRUNCATE_EXISTING
        )
      )

    val statusCode = response.statusCode()
    if (statusCode >= 400) {
      this.logger.error("[{}] {}", target, statusCode)
      return
    }

    val contentType =
      response.headers()
        .firstValue("Content-Type")
        .orElse("application/octet-stream")

    this.logger.debug("[{}] Content-Type: {}", target, contentType)
    if (this.isContentTypeAcceptable(contentType)) {
      try {
        val manifest =
          this.mapper.readValue(
            outFeedFile.toFile(),
            WPMManifest::class.java
          )
        this.manifests.add(manifest)
      } catch (e : Exception) {
        this.logger.debug("[{}] Parsing failed: ", target, e)
      }
    }
  }

  private fun isContentTypeAcceptable(
    contentType : String
  ) : Boolean {
    if (contentType.startsWith("application/opds+json")) {
      return true
    }
    return false
  }

  private fun userAgent() : String {
    return "org.thepalaceproject.webpub ${WPVersion.MAIN_VERSION} (https://github.com/ThePalaceProject/android-webpub)"
  }

  override fun metadata() : QCommandMetadata {
    return this.metadata
  }
}