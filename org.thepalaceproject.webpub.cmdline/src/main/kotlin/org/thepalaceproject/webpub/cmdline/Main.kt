package org.thepalaceproject.webpub.cmdline

import com.io7m.quarrel.core.QApplication
import com.io7m.quarrel.core.QApplicationMetadata
import com.io7m.quarrel.core.QApplicationType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.thepalaceproject.webpub.cmdline.internal.WPCmdBundleCatalogs
import org.thepalaceproject.webpub.cmdline.internal.WPCmdCrawl
import java.lang.Long
import java.net.URI
import java.util.Optional
import kotlin.Array
import kotlin.Int
import kotlin.String

class Main(
  private val args : Array<String>
) : Runnable {
  private val application : QApplicationType
  private var exitCode : Int

  init {
    val metadata =
      QApplicationMetadata(
        "webpub",
        "org.thepalaceproject.webpub",
        WPVersion.MAIN_VERSION,
        WPVersion.MAIN_VERSION,
        "The webpub command-line application.",
        Optional.of<URI?>(
          URI.create(
            "https://github.com/ThePalaceProject/android-webpub"
          )
        )
      )

    val builder = QApplication.builder(metadata)
    builder.allowAtSyntax(true)
    builder.addCommand(WPCmdCrawl())
    builder.addCommand(WPCmdBundleCatalogs())

    this.application = builder.build()
    this.exitCode = 0
  }

  /**
   * @return The program exit code
   */

  fun exitCode() : Int {
    return this.exitCode
  }

  override fun run() {
    this.exitCode =
      this.application.run(LOG, this.args.toList())
        .exitCode()
  }

  override fun toString() : String {
    return String.format(
      "[Main 0x%s]",
      Long.toUnsignedString(System.identityHashCode(this).toLong(), 16)
    )
  }

  companion object {
    private val LOG : Logger =
      LoggerFactory.getLogger(Main::class.java)

    @JvmStatic
    fun main(
      args : Array<String>
    ) {
      System.exit(mainExitless(args))
    }

    fun mainExitless(
      args : Array<String>
    ) : Int {
      val cm = Main(args)
      cm.run()
      return cm.exitCode()
    }
  }
}
