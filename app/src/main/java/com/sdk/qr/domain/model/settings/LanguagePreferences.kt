package com.sdk.qr.domain.model.settings

import kotlinx.coroutines.flow.Flow

interface LanguagePreferences {
    val languageMode: Flow<LanguageMode>
    suspend fun setLanguageMode(mode: LanguageMode)

}