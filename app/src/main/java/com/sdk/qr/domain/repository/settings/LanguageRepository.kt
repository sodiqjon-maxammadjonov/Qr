package com.sdk.qr.domain.repository.settings

import com.sdk.qr.domain.model.settings.LanguageMode
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    val languageMode: Flow<LanguageMode>
    suspend fun setLanguageMode(mode: LanguageMode)
}
