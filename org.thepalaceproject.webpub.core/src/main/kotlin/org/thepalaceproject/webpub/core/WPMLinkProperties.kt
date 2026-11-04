package org.thepalaceproject.webpub.core

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode

/**
 * @see "https://github.com/readium/webpub-manifest/blob/master/schema/link.schema.json"
 * @see "https://github.com/readium/webpub-manifest/blob/master/schema/extensions/encryption/properties.schema.json"
 * @see "https://github.com/readium/webpub-manifest/blob/master/schema/extensions/epub/properties.schema.json"
 * @see "https://specs.opds.io/schema/properties.schema.json"
 */

@JsonIgnoreProperties(ignoreUnknown = true)
data class WPMLinkProperties(
  @JsonProperty(
    value = "availability"
  )
  val availability : WPMAvailability?,

  @JsonProperty(
    value = "encrypted"
  )
  val encrypted : WPMEncrypted?,

  @JsonProperty(
    value = "indirectAcquisition"
  )
  val indirectAcquisition : List<WPMIndirectAcquisitionTree>?
) : WPMElement() {
  private val extensionPropertiesBacking : MutableMap<String, JsonNode> = mutableMapOf()

  @JsonAnySetter
  fun onUnknownProperty(name : String, value : JsonNode) {
    this.extensionPropertiesBacking[name] = value
  }

  @get:JsonAnyGetter
  val extensionProperties : Map<String, JsonNode>
    get() = this.extensionPropertiesBacking
}
