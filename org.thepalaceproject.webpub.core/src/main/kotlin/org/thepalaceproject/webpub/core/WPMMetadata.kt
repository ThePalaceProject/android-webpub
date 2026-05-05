package org.thepalaceproject.webpub.core

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URI
import java.time.OffsetDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class WPMMetadata @JsonCreator constructor(
  /**
   * The type of the publication.
   */

  @JsonProperty(
    value = "@type"
  )
  val type : String? = null,

  @JsonProperty(
    value = "conformsTo"
  )
  val conformsTo : List<URI> = listOf(),

  /**
   * The title of the publication.
   */

  @JsonProperty(
    value = "title"
  )
  val title : WPMLanguageMap,

  /**
   * The text value used to sort the publication.
   */

  @JsonProperty(
    value = "sortAs"
  )
  val sortAs : WPMLanguageMap?,

  /**
   * The subtitle of the publication.
   */

  @JsonProperty(
    value = "subtitle"
  )
  val subtitle : WPMLanguageMap?,

  /**
   * The unique identifier for the publication.
   */

  @JsonProperty(
    value = "identifier"
  )
  @JsonAlias("id")
  val identifier : URI?,

  /**
   * The alternative identifier(s).
   */

  @JsonProperty(
    value = "altIdentifier"
  )
  val altIdentifier : List<String> = listOf(),

  /**
   * The accessibility metadata.
   */

  @JsonProperty(
    value = "accessibility"
  )
  val accessibility : WPMAccessibility?,

  /**
   * The time the publication was last modified.
   */

  @JsonProperty(
    value = "modified"
  )
  val modified : OffsetDateTime?,

  /**
   * The time the publication was published.
   */

  @JsonProperty(
    value = "published"
  )
  val published : OffsetDateTime?,

  /**
   * The languages that apply to the publication.
   */

  @JsonProperty(
    value = "languages"
  )
  val languages : List<String> = listOf(),

  /**
   * The author of the publication.
   */

  @JsonProperty(
    value = "author"
  )
  val author : List<WPMContributorOrString> = listOf(),

  /**
   * The translator of the publication.
   */

  @JsonProperty(
    value = "translator"
  )
  val translator : List<WPMContributorOrString> = listOf(),

  /**
   * The editor of the publication.
   */

  @JsonProperty(
    value = "editor"
  )
  val editor : List<WPMContributorOrString> = listOf(),

  /**
   * The artist of the publication.
   */

  @JsonProperty(
    value = "artist"
  )
  val artist : List<WPMContributorOrString> = listOf(),

  /**
   * The illustrator of the publication.
   */

  @JsonProperty(
    value = "illustrator"
  )
  val illustrator : List<WPMContributorOrString> = listOf(),

  /**
   * The letterer of the publication.
   */

  @JsonProperty(
    value = "letterer"
  )
  val letterer : List<WPMContributorOrString> = listOf(),

  /**
   * The penciler of the publication.
   */

  @JsonProperty(
    value = "penciler"
  )
  val penciler : List<WPMContributorOrString> = listOf(),

  /**
   * The colorist of the publication.
   */

  @JsonProperty(
    value = "colorist"
  )
  val colorist : List<WPMContributorOrString> = listOf(),

  /**
   * The inker of the publication.
   */

  @JsonProperty(
    value = "inker"
  )
  val inker : List<WPMContributorOrString> = listOf(),

  /**
   * The narrator of the publication.
   */

  @JsonProperty(
    value = "narrator"
  )
  val narrator : List<WPMContributorOrString> = listOf(),

  /**
   * The contributor of the publication.
   */

  @JsonProperty(
    value = "contributor"
  )
  val contributor : List<WPMContributorOrString> = listOf(),

  /**
   * The publisher of the publication.
   */

  @JsonProperty(
    value = "publisher"
  )
  val publisher : List<WPMContributorOrString> = listOf(),

  /**
   * The imprint of the publication.
   */

  @JsonProperty(
    value = "imprint"
  )
  val imprint : List<WPMContributorOrString> = listOf(),

  /**
   * The subjects that apply to the publication.
   */

  @JsonProperty(
    value = "subject"
  )
  val subjects : List<WPMSubjectOrString> = listOf(),

  /**
   * The layout of the publication.
   */

  @JsonProperty(
    value = "layout"
  )
  val layout : WPMLayout?,

  /**
   * The description of the publication.
   */

  @JsonProperty(
    value = "description"
  )
  val description : String = "",

  /**
   * The duration of the publication.
   */

  @JsonProperty(
    value = "duration"
  )
  val duration : Number?,

  /**
   * The number of pages in the publication.
   */

  @JsonProperty(
    value = "numberOfPages"
  )
  val numberOfPages : Number?,

  /**
   * The owner of the publication.
   */

  @JsonProperty(
    value = "belongsTo"
  )
  val belongsTo : WPMBelongsTo?,

  /**
   * The objects contained within the publication.
   */

  @JsonProperty(
    value = "contains"
  )
  val contains : WPMContains?,

  /**
   * The number of pages in the publication.
   */

  @WPMExtension("PalaceLibraryRegistry")
  @JsonProperty(
    value = "numberOfItems"
  )
  val numberOfItems : Number?,
) : WPMElement()
