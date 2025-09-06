package com.sdk.qr.data.util

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.sdk.qr.domain.model.settings.LanguageMode
import java.util.Locale

@SuppressLint("LocalContextConfigurationRead")
@Composable
fun getLocalizedLanguageString(
    stringRes: Int,
    languageMode: LanguageMode
): String {
    val context = LocalContext.current

    return remember(stringRes, languageMode) {
        val locale = when (languageMode) {
            LanguageMode.ENGLISH -> Locale.forLanguageTag("en")
            LanguageMode.UZBEK -> Locale.forLanguageTag("uz")
            LanguageMode.RUSSIAN -> Locale.forLanguageTag("ru")
            LanguageMode.SYSTEM -> Locale.getDefault()
        }

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        val localizedContext = context.createConfigurationContext(config)
        localizedContext.getString(stringRes)
    }
}