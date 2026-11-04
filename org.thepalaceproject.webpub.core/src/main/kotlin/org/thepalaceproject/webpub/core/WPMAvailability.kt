package org.thepalaceproject.webpub.core

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class WPMAvailability(
  @JsonProperty(
    value = "state",
    required = true
  )
  val state : String,

  @JsonProperty(
    value = "indirectAcquisition"
  )
  val indirectAcquisitionTree : WPMIndirectAcquisitionTree? = null,

  @JsonProperty(
    value = "since"
  )
  val since : OffsetDateTime? = null,

  @JsonProperty(
    value = "until"
  )
  val until : OffsetDateTime? = null
) : WPMElement()
