package com.sdk.qr.ui.settings.state

import com.sdk.qr.domain.model.settings.LanguageMode
import com.sdk.qr.domain.model.settings.ThemeMode

data class SettingsState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val languageMode: LanguageMode = LanguageMode.SYSTEM,
    val isLoading: Boolean = false
)