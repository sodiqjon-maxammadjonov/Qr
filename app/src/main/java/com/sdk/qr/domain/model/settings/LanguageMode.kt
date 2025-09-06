package com.sdk.qr.domain.model.settings

import com.sdk.qr.R

enum class LanguageMode(val value: String, val labelRes: Int) {
    SYSTEM("system", R.string.system),
    ENGLISH("en", R.string.english),
    RUSSIAN("ru", R.string.russian),
    UZBEK("uz", R.string.uzbek);

    companion object {
        fun fromValue(value: String): LanguageMode {
            return entries.find { it.value == value } ?: SYSTEM
        }
    }
}
