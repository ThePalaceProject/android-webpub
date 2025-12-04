package org.thepalaceproject.webpub.core

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * An OPDS 2.0 feed.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
data class WPMFeed @JsonCreator constructor(
  /**
   * The metadata.
   */

  @JsonProperty(
    value = "metadata",
    required = true
  )
  val metadata : WPMMetadata,

  /**
   * The navigation links, if any.
   */

  @JsonProperty(
    value = "navigation"
  )
  val navigation : List<WPMLink> = listOf(),

  /**
   * The publications
   */

  @JsonProperty(
    value = "publications"
  )
  val publications : List<WPMPublication> = listOf(),

  /**
   * The images
   */

  @JsonProperty(
    value = "images"
  )
  val images : List<WPMLink> = listOf(),

  /**
   * The facets
   */

  @JsonProperty(
    value = "facets"
  )
  val facets : List<WPMFacet> = listOf(),

  /**
   * The facets
   */

  @JsonProperty(
    value = "groups"
  )
  val groups : List<WPMGroup> = listOf(),

  /**
   * The links
   */

  @JsonProperty(
    value = "links"
  )
  val links : List<WPMLink> = listOf(),

  /**
   * The catalogs
   */

  @JsonProperty(
    value = "catalogs"
  )
  val catalogs : List<WPMCatalog> = listOf()
) : WPMElement()
