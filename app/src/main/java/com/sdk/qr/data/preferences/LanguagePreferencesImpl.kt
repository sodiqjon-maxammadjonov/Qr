package com.sdk.qr.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sdk.qr.data.local.PreferencesKeys
import com.sdk.qr.domain.model.settings.LanguageMode
import com.sdk.qr.domain.model.settings.LanguagePreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguagePreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LanguagePreferences {

    private val key = stringPreferencesKey(PreferencesKeys.LANGUAGE_KEY.toString())

    override val languageMode: Flow<LanguageMode> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val value = prefs[key] ?: LanguageMode.SYSTEM.value
            LanguageMode.fromValue(value)
        }

    override suspend fun setLanguageMode(mode: LanguageMode) {
        try {
            context.dataStore.edit { prefs ->
                prefs[key] = mode.value
            }
        } catch (exception: IOException) {
            throw exception
        }
    }
}