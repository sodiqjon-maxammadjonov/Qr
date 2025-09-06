package com.sdk.qr.domain.model.settings

import com.sdk.qr.R

enum class ThemeMode(val value: String, val labelRes: Int) {
    LIGHT("light", R.string.light_theme),
    DARK("dark", R.string.dark_theme),
    SYSTEM("system", R.string.system);

    companion object {
        fun fromValue(value: String): ThemeMode {
            return entries.find { it.value == value } ?: SYSTEM
        }
    }
}
