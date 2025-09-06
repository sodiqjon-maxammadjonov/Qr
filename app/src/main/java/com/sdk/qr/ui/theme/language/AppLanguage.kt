package com.sdk.qr.ui.theme.language

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sdk.qr.domain.model.settings.LanguageMode
import com.sdk.qr.ui.settings.viewmodel.LanguageViewModel

@SuppressLint("LocalContextConfigurationRead")
@Composable
fun AppLanguage(viewModel: LanguageViewModel = viewModel(), content: @Composable () -> Unit) {
    val language by viewModel.languageMode.collectAsState()
    val context = LocalContext.current

    val locale = when (language) {
        LanguageMode.ENGLISH -> Locale.forLanguageTag("en")
        LanguageMode.UZBEK -> Locale.forLanguageTag("uz")
        LanguageMode.RUSSIAN -> Locale.forLanguageTag("ru")
        LanguageMode.SYSTEM -> Locale.getDefault()
    }

    val config = remember(locale) {
        Configuration(context.resources.configuration).apply {
            setLocale(locale)
        }
    }

    val localizedContext = remember(context, config) {
        context.createConfigurationContext(config)
    }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalConfiguration provides config
    ) {
        content()
    }
}