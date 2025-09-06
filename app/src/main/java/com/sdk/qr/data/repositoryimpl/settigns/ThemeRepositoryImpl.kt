package com.sdk.qr.data.repositoryimpl.settigns

import com.sdk.qr.domain.model.settings.ThemeMode
import com.sdk.qr.domain.model.settings.ThemePreferences
import com.sdk.qr.domain.repository.settings.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val prefs: ThemePreferences
) : ThemeRepository {

    override val themeMode: Flow<ThemeMode> = prefs.themeMode

    override suspend fun setThemeMode(mode: ThemeMode) {
        prefs.setThemeMode(mode)
    }
}