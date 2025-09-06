package com.sdk.qr.domain.model.settings

import kotlinx.coroutines.flow.Flow

interface ThemePreferences {
    val themeMode: Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)
}