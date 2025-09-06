package com.sdk.qr.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.qr.domain.model.settings.LanguageMode
import com.sdk.qr.domain.model.settings.ThemeMode
import com.sdk.qr.domain.repository.settings.LanguageRepository
import com.sdk.qr.domain.repository.settings.ThemeRepository
import com.sdk.qr.ui.settings.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeRepository: ThemeRepository,
    private val languageRepository: LanguageRepository
) : ViewModel() {
    val settingsState = combine(
        themeRepository.themeMode,
        languageRepository.languageMode
    ) { theme, language ->
        SettingsState(
            themeMode = theme,
            languageMode = language,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(10_000),
        initialValue = SettingsState(isLoading = true)
    )

    fun changeThemeMode(mode: ThemeMode) {
        if (settingsState.value.themeMode == mode) return

        viewModelScope.launch {
            try {
                themeRepository.setThemeMode(mode)
            } catch (e: Exception) {
                // Error handling
            }
        }
    }

    fun changeLanguageMode(mode: LanguageMode) {
        if (settingsState.value.languageMode == mode) return

        viewModelScope.launch {
            try {
                languageRepository.setLanguageMode(mode)
            } catch (e: Exception) {
                // Error handling
            }
        }
    }
}