package com.sdk.qr.data.util

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.sdk.qr.domain.model.settings.LanguageMode
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun setAppLanguage(languageMode: LanguageMode) {
        val locale = when (languageMode) {
            LanguageMode.ENGLISH -> "en"
            LanguageMode.UZBEK -> "uz"
            LanguageMode.RUSSIAN -> "ru"
            LanguageMode.SYSTEM -> return resetToSystemLanguage()
        }

        val appLocale = LocaleListCompat.forLanguageTags(locale)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    private fun resetToSystemLanguage() {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
    }
}