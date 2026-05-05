package org.thepalaceproject.webpub.core

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * The OPDS 2.0 catalog section.
 */

@WPMExtension("PalaceLibraryRegistry")
@JsonIgnoreProperties(ignoreUnknown = true)
data class WPMCatalog(

  /**
   * The catalog metadata.
   */

  @JsonProperty(
    value = "metadata",
    required = true
  )
  val metadata : WPMMetadata,

  /**
   * The set of catalog links.
   */

  @JsonProperty(
    value = "links"
  )
  val links : List<WPMLink> = listOf(),

  /**
   * The set of image links.
   */

  @JsonProperty(
    value = "images"
  )
  val images : List<WPMLink> = listOf()
) : WPMElement()
