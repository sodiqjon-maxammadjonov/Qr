package com.sdk.qr.data.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

object PreferencesUtil {

    fun <T> Flow<Preferences>.safeMap(
        transform: (Preferences) -> T
    ): Flow<T> = this
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map(transform)

    inline fun <reified T : Enum<T>> getEnumValue(
        value: String?,
        defaultValue: T,
        valueOf: (String) -> T?
    ): T {
        return if (value != null) {
            try {
                valueOf(value) ?: defaultValue
            } catch (e: Exception) {
                defaultValue
            }
        } else {
            defaultValue
        }
    }
}