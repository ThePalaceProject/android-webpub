package org.thepalaceproject.webpub.tests

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.json.JsonMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import org.slf4j.LoggerFactory
import org.thepalaceproject.webpub.core.WPMArticle
import org.thepalaceproject.webpub.core.WPMChapter
import org.thepalaceproject.webpub.core.WPMCollection
import org.thepalaceproject.webpub.core.WPMEpisode
import org.thepalaceproject.webpub.core.WPMFeed
import org.thepalaceproject.webpub.core.WPMIssue
import org.thepalaceproject.webpub.core.WPMManifest
import org.thepalaceproject.webpub.core.WPMMappers
import org.thepalaceproject.webpub.core.WPMPeriodical
import org.thepalaceproject.webpub.core.WPMSeason
import org.thepalaceproject.webpub.core.WPMSeries
import org.thepalaceproject.webpub.core.WPMStoryArc
import org.thepalaceproject.webpub.core.WPMVolume
import java.io.IOException
import java.io.InputStream
import java.nio.file.Path
import java.util.stream.Stream
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class WPMParseTest {

  private val logger =
    LoggerFactory.getLogger(WPMParseTest::class.java)

  private lateinit var mapper : JsonMapper
  private lateinit var directory : Path

  @BeforeEach
  fun setup(@TempDir directory : Path) {
    this.directory =
      directory
    this.mapper =
      WPMMappers.createMapper()
  }

  @Test
  fun testGroups20250821() {
    val feed =
      this.mapper.readValue<WPMManifest>(
        this.resource("groups-20250821.json"),
        WPMManifest::class.java
      )

    assertEquals(0, feed.catalogs.size)
    assertEquals(14, feed.links.size)
    assertEquals(217, feed.publications.size)

    this.roundTripManifest(feed)
  }

  @Test
  fun testRegistry20250826() {
    val feed =
      this.mapper.readValue<WPMManifest>(
        this.resource("registry-20250826.json"),
        WPMManifest::class.java
      )

    assertEquals(708, feed.catalogs.size)
    assertEquals(4, feed.links.size)
    assertEquals(0, feed.publications.size)

    this.roundTripManifest(feed)
  }

  @Test
  fun testRegistryCrawlable20260505() {
    val feed =
      this.mapper.readValue<WPMManifest>(
        this.resource("registry-crawlable-20260505.json"),
        WPMManifest::class.java
      )

    assertEquals(1122, feed.metadata.numberOfItems)
    assertEquals(20, feed.catalogs.size)
    assertEquals(7, feed.links.size)
    assertEquals(0, feed.publications.size)

    this.roundTripManifest(feed)
  }

  @Test
  fun testArticle0() {
    val p0 =
      this.mapper.readValue<WPMArticle>(
        this.resource("values/article-0.json"),
        WPMArticle::class.java
      )

    this.roundTripValue(WPMArticle::class.java, p0)
  }

  @Test
  fun testArticle1() {
    val p0 =
      this.mapper.readValue<WPMArticle>(
        this.resource("values/article-1.json"),
        WPMArticle::class.java
      )

    this.roundTripValue(WPMArticle::class.java, p0)
  }

  @Test
  fun testArticle2() {
    val p0 =
      this.mapper.readValue<WPMArticle>(
        this.resource("values/article-2.json"),
        WPMArticle::class.java
      )

    this.roundTripValue(WPMArticle::class.java, p0)
  }

  @Test
  fun testArticleAllNest() {
    val p0 =
      this.mapper.readValue<WPMArticle>(
        this.resource("values/article-all-nest.json"),
        WPMArticle::class.java
      )

    this.roundTripValue(WPMArticle::class.java, p0)
  }

  @Test
  fun testArticleError0() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMArticle>(
        this.resource("values/article-error-0.json"),
        WPMArticle::class.java
      )
    }
  }

  @Test
  fun testArticleError1() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMArticle>(
        this.resource("values/article-error-1.json"),
        WPMArticle::class.java
      )
    }
  }

  @Test
  fun testChapter0() {
    val p0 =
      this.mapper.readValue<WPMChapter>(
        this.resource("values/chapter-0.json"),
        WPMChapter::class.java
      )

    this.roundTripValue(WPMChapter::class.java, p0)
  }

  @Test
  fun testChapter1() {
    val p0 =
      this.mapper.readValue<WPMChapter>(
        this.resource("values/chapter-1.json"),
        WPMChapter::class.java
      )

    this.roundTripValue(WPMChapter::class.java, p0)
  }

  @Test
  fun testChapter2() {
    val p0 =
      this.mapper.readValue<WPMChapter>(
        this.resource("values/chapter-2.json"),
        WPMChapter::class.java
      )

    this.roundTripValue(WPMChapter::class.java, p0)
  }

  @Test
  fun testChapter3() {
    val p0 =
      this.mapper.readValue<WPMChapter>(
        this.resource("values/chapter-3.json"),
        WPMChapter::class.java
      )

    this.roundTripValue(WPMChapter::class.java, p0)
  }

  @Test
  fun testChapterAllNest() {
    val p0 =
      this.mapper.readValue<WPMChapter>(
        this.resource("values/chapter-all-nest.json"),
        WPMChapter::class.java
      )

    this.roundTripValue(WPMChapter::class.java, p0)
  }

  @Test
  fun testChapterError0() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMChapter>(
        this.resource("values/chapter-error-0.json"),
        WPMChapter::class.java
      )
    }
  }

  @Test
  fun testChapterError1() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMChapter>(
        this.resource("values/chapter-error-1.json"),
        WPMChapter::class.java
      )
    }
  }

  @Test
  fun testCollection0() {
    val p0 =
      this.mapper.readValue<WPMCollection>(
        this.resource("values/collection-0.json"),
        WPMCollection::class.java
      )

    this.roundTripValue(WPMCollection::class.java, p0)
  }

  @Test
  fun testCollection1() {
    val p0 =
      this.mapper.readValue<WPMCollection>(
        this.resource("values/collection-1.json"),
        WPMCollection::class.java
      )

    this.roundTripValue(WPMCollection::class.java, p0)
  }

  @Test
  fun testCollection2() {
    val p0 =
      this.mapper.readValue<WPMCollection>(
        this.resource("values/collection-2.json"),
        WPMCollection::class.java
      )

    this.roundTripValue(WPMCollection::class.java, p0)
  }

  @Test
  fun testCollectionAllNest() {
    val p0 =
      this.mapper.readValue<WPMCollection>(
        this.resource("values/collection-all-nest.json"),
        WPMCollection::class.java
      )

    this.roundTripValue(WPMCollection::class.java, p0)
  }

  @Test
  fun testCollectionError0() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMCollection>(
        this.resource("values/collection-error-0.json"),
        WPMCollection::class.java
      )
    }
  }

  @Test
  fun testCollectionError1() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMCollection>(
        this.resource("values/collection-error-1.json"),
        WPMCollection::class.java
      )
    }
  }

  @Test
  fun testEpisode0() {
    val p0 =
      this.mapper.readValue<WPMEpisode>(
        this.resource("values/episode-0.json"),
        WPMEpisode::class.java
      )

    this.roundTripValue(WPMEpisode::class.java, p0)
  }

  @Test
  fun testEpisode1() {
    val p0 =
      this.mapper.readValue<WPMEpisode>(
        this.resource("values/episode-1.json"),
        WPMEpisode::class.java
      )

    this.roundTripValue(WPMEpisode::class.java, p0)
  }

  @Test
  fun testEpisode2() {
    val p0 =
      this.mapper.readValue<WPMEpisode>(
        this.resource("values/episode-2.json"),
        WPMEpisode::class.java
      )

    this.roundTripValue(WPMEpisode::class.java, p0)
  }

  @Test
  fun testEpisode3() {
    val p0 =
      this.mapper.readValue<WPMEpisode>(
        this.resource("values/episode-3.json"),
        WPMEpisode::class.java
      )

    this.roundTripValue(WPMEpisode::class.java, p0)
  }

  @Test
  fun testEpisodeAllNest() {
    val p0 =
      this.mapper.readValue<WPMEpisode>(
        this.resource("values/episode-all-nest.json"),
        WPMEpisode::class.java
      )

    this.roundTripValue(WPMEpisode::class.java, p0)
  }

  @Test
  fun testEpisodeError0() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMEpisode>(
        this.resource("values/episode-error-0.json"),
        WPMEpisode::class.java
      )
    }
  }

  @Test
  fun testEpisodeError1() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMEpisode>(
        this.resource("values/episode-error-1.json"),
        WPMEpisode::class.java
      )
    }
  }

  @Test
  fun testIssue0() {
    val p0 =
      this.mapper.readValue<WPMIssue>(
        this.resource("values/issue-0.json"),
        WPMIssue::class.java
      )

    this.roundTripValue(WPMIssue::class.java, p0)
  }

  @Test
  fun testIssue1() {
    val p0 =
      this.mapper.readValue<WPMIssue>(
        this.resource("values/issue-1.json"),
        WPMIssue::class.java
      )

    this.roundTripValue(WPMIssue::class.java, p0)
  }

  @Test
  fun testIssue2() {
    val p0 =
      this.mapper.readValue<WPMIssue>(
        this.resource("values/issue-2.json"),
        WPMIssue::class.java
      )

    this.roundTripValue(WPMIssue::class.java, p0)
  }

  @Test
  fun testIssue3() {
    val p0 =
      this.mapper.readValue<WPMIssue>(
        this.resource("values/issue-3.json"),
        WPMIssue::class.java
      )

    this.roundTripValue(WPMIssue::class.java, p0)
  }

  @Test
  fun testIssueAllNest() {
    val p0 =
      this.mapper.readValue<WPMIssue>(
        this.resource("values/issue-all-nest.json"),
        WPMIssue::class.java
      )

    this.roundTripValue(WPMIssue::class.java, p0)
  }

  @Test
  fun testIssueError0() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMIssue>(
        this.resource("values/issue-error-0.json"),
        WPMIssue::class.java
      )
    }
  }

  @Test
  fun testIssueError1() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMIssue>(
        this.resource("values/issue-error-1.json"),
        WPMIssue::class.java
      )
    }
  }


  @Test
  fun testPeriodical0() {
    val p0 =
      this.mapper.readValue<WPMPeriodical>(
        this.resource("values/periodical-0.json"),
        WPMPeriodical::class.java
      )

    this.roundTripValue(WPMPeriodical::class.java, p0)
  }

  @Test
  fun testPeriodical1() {
    val p0 =
      this.mapper.readValue<WPMPeriodical>(
        this.resource("values/periodical-1.json"),
        WPMPeriodical::class.java
      )

    this.roundTripValue(WPMPeriodical::class.java, p0)
  }

  @Test
  fun testPeriodical2() {
    val p0 =
      this.mapper.readValue<WPMPeriodical>(
        this.resource("values/periodical-2.json"),
        WPMPeriodical::class.java
      )

    this.roundTripValue(WPMPeriodical::class.java, p0)
  }

  @Test
  fun testPeriodicalAllNest() {
    val p0 =
      this.mapper.readValue<WPMPeriodical>(
        this.resource("values/periodical-all-nest.json"),
        WPMPeriodical::class.java
      )

    this.roundTripValue(WPMPeriodical::class.java, p0)
  }

  @Test
  fun testPeriodicalError0() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMPeriodical>(
        this.resource("values/periodical-error-0.json"),
        WPMPeriodical::class.java
      )
    }
  }

  @Test
  fun testPeriodicalError1() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMPeriodical>(
        this.resource("values/periodical-error-1.json"),
        WPMPeriodical::class.java
      )
    }
  }

  @Test
  fun testSeason0() {
    val p0 =
      this.mapper.readValue<WPMSeason>(
        this.resource("values/season-0.json"),
        WPMSeason::class.java
      )

    this.roundTripValue(WPMSeason::class.java, p0)
  }

  @Test
  fun testSeason1() {
    val p0 =
      this.mapper.readValue<WPMSeason>(
        this.resource("values/season-1.json"),
        WPMSeason::class.java
      )

    this.roundTripValue(WPMSeason::class.java, p0)
  }

  @Test
  fun testSeason2() {
    val p0 =
      this.mapper.readValue<WPMSeason>(
        this.resource("values/season-2.json"),
        WPMSeason::class.java
      )

    this.roundTripValue(WPMSeason::class.java, p0)
  }

  @Test
  fun testSeason3() {
    val p0 =
      this.mapper.readValue<WPMSeason>(
        this.resource("values/season-3.json"),
        WPMSeason::class.java
      )

    this.roundTripValue(WPMSeason::class.java, p0)
  }

  @Test
  fun testSeasonAllNest() {
    val p0 =
      this.mapper.readValue<WPMSeason>(
        this.resource("values/season-all-nest.json"),
        WPMSeason::class.java
      )

    this.roundTripValue(WPMSeason::class.java, p0)
  }

  @Test
  fun testSeasonError0() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMSeason>(
        this.resource("values/season-error-0.json"),
        WPMSeason::class.java
      )
    }
  }

  @Test
  fun testSeasonError1() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMSeason>(
        this.resource("values/season-error-1.json"),
        WPMSeason::class.java
      )
    }
  }

  @Test
  fun testSeries0() {
    val p0 =
      this.mapper.readValue<WPMSeries>(
        this.resource("values/series-0.json"),
        WPMSeries::class.java
      )

    this.roundTripValue(WPMSeries::class.java, p0)
  }

  @Test
  fun testSeries1() {
    val p0 =
      this.mapper.readValue<WPMSeries>(
        this.resource("values/series-1.json"),
        WPMSeries::class.java
      )

    this.roundTripValue(WPMSeries::class.java, p0)
  }

  @Test
  fun testSeries2() {
    val p0 =
      this.mapper.readValue<WPMSeries>(
        this.resource("values/series-2.json"),
        WPMSeries::class.java
      )

    this.roundTripValue(WPMSeries::class.java, p0)
  }

  @Test
  fun testSeriesAllNest() {
    val p0 =
      this.mapper.readValue<WPMSeries>(
        this.resource("values/series-all-nest.json"),
        WPMSeries::class.java
      )

    this.roundTripValue(WPMSeries::class.java, p0)
  }

  @Test
  fun testSeriesError0() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMSeries>(
        this.resource("values/series-error-0.json"),
        WPMSeries::class.java
      )
    }
  }

  @Test
  fun testSeriesError1() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMSeries>(
        this.resource("values/series-error-1.json"),
        WPMSeries::class.java
      )
    }
  }

  @Test
  fun testStoryArc0() {
    val p0 =
      this.mapper.readValue<WPMStoryArc>(
        this.resource("values/story-arc-0.json"),
        WPMStoryArc::class.java
      )

    this.roundTripValue(WPMStoryArc::class.java, p0)
  }

  @Test
  fun testStoryArc1() {
    val p0 =
      this.mapper.readValue<WPMStoryArc>(
        this.resource("values/story-arc-1.json"),
        WPMStoryArc::class.java
      )

    this.roundTripValue(WPMStoryArc::class.java, p0)
  }

  @Test
  fun testStoryArc2() {
    val p0 =
      this.mapper.readValue<WPMStoryArc>(
        this.resource("values/story-arc-2.json"),
        WPMStoryArc::class.java
      )

    this.roundTripValue(WPMStoryArc::class.java, p0)
  }

  @Test
  fun testStoryArc3() {
    val p0 =
      this.mapper.readValue<WPMStoryArc>(
        this.resource("values/story-arc-3.json"),
        WPMStoryArc::class.java
      )

    this.roundTripValue(WPMStoryArc::class.java, p0)
  }

  @Test
  fun testStoryArcAllNest() {
    val p0 =
      this.mapper.readValue<WPMStoryArc>(
        this.resource("values/story-arc-all-nest.json"),
        WPMStoryArc::class.java
      )

    this.roundTripValue(WPMStoryArc::class.java, p0)
  }

  @Test
  fun testStoryArcError0() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMStoryArc>(
        this.resource("values/story-arc-error-0.json"),
        WPMStoryArc::class.java
      )
    }
  }

  @Test
  fun testStoryArcError1() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMStoryArc>(
        this.resource("values/story-arc-error-1.json"),
        WPMStoryArc::class.java
      )
    }
  }

  @Test
  fun testVolume0() {
    val p0 =
      this.mapper.readValue<WPMVolume>(
        this.resource("values/volume-0.json"),
        WPMVolume::class.java
      )

    this.roundTripValue(WPMVolume::class.java, p0)
  }

  @Test
  fun testVolume1() {
    val p0 =
      this.mapper.readValue<WPMVolume>(
        this.resource("values/volume-1.json"),
        WPMVolume::class.java
      )

    this.roundTripValue(WPMVolume::class.java, p0)
  }

  @Test
  fun testVolume2() {
    val p0 =
      this.mapper.readValue<WPMVolume>(
        this.resource("values/volume-2.json"),
        WPMVolume::class.java
      )

    this.roundTripValue(WPMVolume::class.java, p0)
  }

  @Test
  fun testVolume3() {
    val p0 =
      this.mapper.readValue<WPMVolume>(
        this.resource("values/volume-3.json"),
        WPMVolume::class.java
      )

    this.roundTripValue(WPMVolume::class.java, p0)
  }

  @Test
  fun testVolumeAllNest() {
    val p0 =
      this.mapper.readValue<WPMVolume>(
        this.resource("values/volume-all-nest.json"),
        WPMVolume::class.java
      )

    this.roundTripValue(WPMVolume::class.java, p0)
  }

  @Test
  fun testVolumeError0() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMVolume>(
        this.resource("values/volume-error-0.json"),
        WPMVolume::class.java
      )
    }
  }

  @Test
  fun testVolumeError1() {
    assertThrows<MismatchedInputException> {
      this.mapper.readValue<WPMVolume>(
        this.resource("values/volume-error-1.json"),
        WPMVolume::class.java
      )
    }
  }

  private fun <T> roundTripValue(
    clazz : Class<T>,
    value : T
  ) {
    val serializedText =
      this.mapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(value)
    val rereadObject =
      this.mapper.readValue(
        serializedText,
        clazz
      )

    assertEquals(value, rereadObject)
  }

  private val testCases = listOf(
    "aldiko/argentina.json",
    "aldiko/belgique.json",
    "aldiko/canada.json",
    "aldiko/canada/alberta.json",
    "aldiko/canada/britishcolumbia.json",
    "aldiko/canada/epl_cantook_home.json",
    "aldiko/canada/epl_cantook_resources.json",
    "aldiko/canada/manitoba.json",
    "aldiko/canada/novascotia.json",
    "aldiko/canada/ontario.json",
    "aldiko/canada/quebec.json",
    "aldiko/canada/yukon.json",
    "aldiko/france.json",
    "aldiko/home.json",
    "aldiko/international.json",
    "aldiko/italia.json",
    "aldiko/suisse.json",
    "anna_karenina_toc.audiobook-manifest.json",
    "bestnewhorror.audiobook-manifest.json",
    "feedbooks_0.json",
    "feedbooks_1.json",
    "feedbooks_2.json",
    "feedbooks_3.json",
    "feedbooks_rights_no_end.json",
    "feedbooks_rights_no_start.json",
    "feedbooks_rights_ok.json",
    "findaway-20201015.json",
    "findaway.json",
    "findaway_leading_0.json",
    "flatland.audiobook-manifest.json",
    "flatland_toc.audiobook-manifest.json",
    "fulcrum/amherst",
    "fulcrum/bigten",
    "fulcrum/fulcrum.json",
    "fulcrum/heb",
    "fulcrum/leverpress",
    "fulcrum/umpebc",
    "fulcrum/umpebc_oa",
    "groups-20250821.json",
    "igen.json",
    "null_link_type.json",
    "null_titles.json",
    "oapen/TwentyThree_English_only.json",
    "ok_minimal_0.json",
    "ok_minimal_1.json",
    "open_access_0.json",
    "random-game-audio-2.json",
    "random-game-audio.json",
    "rbdigital_large.json",
    "registry-20250826.json",
    "soundzter/soundzter-20250826.json",
    "summerwives.audiobook-manifest.json",
    "unlimitedlistens/0bdf082d-b0b0-4135-a11f-f92bda7cc51d",
  )

  @TestFactory
  fun testRoundTripManifestMany() : Stream<DynamicTest> {
    return this.testCases.stream().map { x -> this.roundTripDynamicTestManifestOf(x) }
  }

  @TestFactory
  fun testRoundTripFeedMany() : Stream<DynamicTest> {
    return this.testCases.stream().map { x -> this.roundTripDynamicTestFeedOf(x) }
  }

  private fun roundTripDynamicTestManifestOf(
    name : String
  ) : DynamicTest {
    return DynamicTest.dynamicTest("testRoundTrip_$name", {
      this.roundTripManifest(
        this.mapper.readValue(
          this.resource(name),
          WPMManifest::class.java
        )
      )
    })
  }

  private fun roundTripDynamicTestFeedOf(
    name : String
  ) : DynamicTest {
    return DynamicTest.dynamicTest("testRoundTrip_$name", {
      this.roundTripFeed(
        this.mapper.readValue(
          this.resource(name),
          WPMFeed::class.java
        )
      )
    })
  }

  private fun roundTripManifest(
    feed : WPMManifest
  ) {
    val serializedText =
      this.mapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(feed)
    val rereadObject =
      this.mapper.readValue(
        serializedText,
        WPMManifest::class.java
      )

    for ((c0, c1) in feed.catalogs.zip(rereadObject.catalogs)) {
      this.assertDataClassEquals(c0.metadata, c1.metadata)
      for ((l0, l1) in c0.links.zip(c1.links)) {
        assertEquals(l0, l1)
      }
      assertEquals(c0.links, c1.links)
      assertEquals(c0.images, c1.images)
      assertEquals(c0, c1)
    }

    this.assertDataClassEquals(feed.catalogs, rereadObject.catalogs)
    this.assertDataClassEquals(feed.publications, rereadObject.publications)
    this.assertDataClassEquals(feed.links, rereadObject.links)
    this.assertDataClassEquals(feed.metadata, rereadObject.metadata)

    assertEquals(feed.navigation, rereadObject.navigation)
    assertEquals(feed, rereadObject)
  }

  private fun roundTripFeed(
    feed : WPMFeed
  ) {
    val serializedText =
      this.mapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(feed)
    val rereadObject =
      this.mapper.readValue(
        serializedText,
        WPMFeed::class.java
      )

    for ((c0, c1) in feed.catalogs.zip(rereadObject.catalogs)) {
      this.assertDataClassEquals(c0.metadata, c1.metadata)
      for ((l0, l1) in c0.links.zip(c1.links)) {
        assertEquals(l0, l1)
      }
      assertEquals(c0.links, c1.links)
      assertEquals(c0.images, c1.images)
      assertEquals(c0, c1)
    }

    this.assertDataClassEquals(feed.facets, rereadObject.facets)
    this.assertDataClassEquals(feed.groups, rereadObject.groups)
    this.assertDataClassEquals(feed.catalogs, rereadObject.catalogs)
    this.assertDataClassEquals(feed.publications, rereadObject.publications)
    this.assertDataClassEquals(feed.links, rereadObject.links)
    this.assertDataClassEquals(feed.metadata, rereadObject.metadata)

    assertEquals(feed.navigation, rereadObject.navigation)
    assertEquals(feed, rereadObject)
  }

  fun <T : Any> assertDataClassEquals(
    expected : T,
    actual : T
  ) {
    val kClass = expected::class
    this.logger.debug("Comparing class {}", kClass)

    if (!kClass.isData) {
      this.logger.debug("{} is not a data class, ignoring it.", kClass)
      return
    }

    val memberProperties = kClass.memberProperties
    this.logger.debug(
      "Class {} has {} properties",
      kClass,
      memberProperties.size
    )

    for (prop in memberProperties) {
      @Suppress("UNCHECKED_CAST")
      val property = prop as KProperty1<T, Any?>
      this.logger.debug(
        "Comparing property {} of type {}",
        property.name,
        property.javaClass
      )
      val expectedValue =
        property.get(expected)
      val actualValue =
        property.get(actual)
      assertEquals(
        expectedValue,
        actualValue,
        "Mismatch in property: ${prop.name}"
      )
    }
  }

  private fun resource(
    name : String
  ) : InputStream {
    val path =
      "/org/thepalaceproject/webpub/tests/$name"
    val stream =
      WPMParseTest::class.java
        .getResourceAsStream(path)

    if (stream == null) {
      throw IOException("No such resource: $path")
    }

    return stream
  }
}