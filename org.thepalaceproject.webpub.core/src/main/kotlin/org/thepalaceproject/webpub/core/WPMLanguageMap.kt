package org.thepalaceproject.webpub.core

import java.util.Locale
import java.util.SortedMap

/**
 * A language-mapped string.
 *
 * @see "https://github.com/readium/webpub-manifest/blob/master/schema/language-map.schema.json"
 */

sealed class WPMLanguageMap() : WPMElement() {

  abstract val defaultValue : String

  data class Scalar(
    val value : String
  ) : WPMLanguageMap() {
    override val defaultValue : String =
      this.value
  }

  data class Mapped(
    val byLanguage : SortedMap<String, String>
  ) : WPMLanguageMap() {

    private fun defaultValueNow() : String {
      val locale = Locale.getDefault()
      val tag = locale.toLanguageTag()
      val value = this.byLanguage.get(tag)
      if (value == null) {
        val first = this.byLanguage.firstKey()
        return this.byLanguage[first]!!
      }
      return value
    }

    override val defaultValue : String
      get() = this.defaultValueNow()
  }
}
