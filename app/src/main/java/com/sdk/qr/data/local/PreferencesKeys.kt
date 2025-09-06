package com.sdk.qr.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    val LANGUAGE_KEY = stringPreferencesKey("language_mode")

    val FONT_SIZE_KEY = stringPreferencesKey("font_size")
    val AUTO_BACKUP_KEY = stringPreferencesKey("auto_backup")
    val VIBRATION_ENABLED_KEY = stringPreferencesKey("vibration_enabled")
}
