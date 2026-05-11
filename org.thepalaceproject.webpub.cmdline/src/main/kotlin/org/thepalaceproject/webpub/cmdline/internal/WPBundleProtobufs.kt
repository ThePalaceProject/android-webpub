package org.thepalaceproject.webpub.cmdline.internal

import org.thepalaceproject.webpub.cmdline.internal.DBSerialization.DBSAccountProviderDescription1
import org.thepalaceproject.webpub.cmdline.internal.DBSerialization.DBSLink1
import org.thepalaceproject.webpub.core.WPMCatalog
import org.thepalaceproject.webpub.core.WPMLink
import org.thepalaceproject.webpub.core.WPMLinkBasic
import org.thepalaceproject.webpub.core.WPMLinkTemplated

object WPBundleProtobufs {

  fun catalogToP1(
    catalog: WPMCatalog
  ): DBSAccountProviderDescription1 {
    val builder = DBSAccountProviderDescription1.newBuilder()
    builder.description = catalog.metadata.description
    builder.title = catalog.metadata.title.defaultValue
    builder.id = catalog.metadata.identifier.toString()
    builder.updated = catalog.metadata.updated.toString()

    for (link in catalog.links) {
      builder.addLinks(this.linkToP1(link))
    }
    for (link in catalog.images) {
      builder.addImages(this.linkToP1(link))
    }
    val r = builder.build()
    return r
  }

  fun linkToP1(
    link: WPMLink
  ): DBSLink1 {
    val builder = DBSLink1.newBuilder()
    link.bitrate?.let { x -> builder.bitrate = x.toDouble() }
    link.duration?.let { x -> builder.duration = x.toDouble() }
    link.height?.let { x -> builder.height = x }
    if (!link.relation.isEmpty()) {
      builder.relation = link.relation.first()
    }
    link.title?.let { x -> builder.title = x }
    link.type?.let { x -> builder.type = x.fullType }

    return when (link) {
      is WPMLinkBasic -> {
        builder.href = link.href.toString()
        builder.templated = false
        builder.build()
      }

      is WPMLinkTemplated -> {
        builder.href = link.href
        builder.templated = true
        builder.build()
      }
    }
  }
}