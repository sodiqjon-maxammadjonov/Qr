package com.sdk.qr.data.repositoryimpl.settigns

import com.sdk.qr.domain.model.settings.LanguageMode
import com.sdk.qr.domain.model.settings.LanguagePreferences
import com.sdk.qr.domain.repository.settings.LanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageRepositoryImpl @Inject constructor(
    private val prefs: LanguagePreferences
) : LanguageRepository {

    override val languageMode: Flow<LanguageMode> = prefs.languageMode
        .distinctUntilChanged()

    override suspend fun setLanguageMode(mode: LanguageMode) {
        prefs.setLanguageMode(mode)
    }
}