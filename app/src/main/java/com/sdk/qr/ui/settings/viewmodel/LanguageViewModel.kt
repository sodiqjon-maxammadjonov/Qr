package com.sdk.qr.ui.settings.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.qr.data.util.LanguageManager
import com.sdk.qr.domain.model.settings.LanguageMode
import com.sdk.qr.domain.repository.settings.LanguageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val repository: LanguageRepository,
    private val languageManager: LanguageManager
) : ViewModel() {

    val languageMode = repository.languageMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 10_000,
            replayExpirationMillis = 0
        ),
        initialValue = LanguageMode.SYSTEM
    )

    fun changeLanguageMode(mode: LanguageMode) {
        if (languageMode.value == mode) return

        viewModelScope.launch {
            try {
                repository.setLanguageMode(mode)
                languageManager.setAppLanguage(mode)
            } catch (e: Exception) {

            }
        }
    }

    fun isCurrentMode(mode: LanguageMode): Boolean = languageMode.value == mode
}