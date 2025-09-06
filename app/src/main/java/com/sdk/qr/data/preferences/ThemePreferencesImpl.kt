package com.sdk.qr.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sdk.qr.data.local.PreferencesKeys
import com.sdk.qr.data.local.SETTINGS_DATASTORE
import com.sdk.qr.domain.model.settings.ThemeMode
import com.sdk.qr.domain.model.settings.ThemePreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = SETTINGS_DATASTORE)

@Singleton
class ThemePreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ThemePreferences {

    private val theme = stringPreferencesKey(PreferencesKeys.THEME_MODE_KEY.toString())

    override val themeMode: Flow<ThemeMode> = context.dataStore.data.map { prefs ->
        val value = prefs[theme] ?: ThemeMode.SYSTEM.value
        ThemeMode.fromValue(value)
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { prefs ->
            prefs[theme] = mode.value
        }
    }
}