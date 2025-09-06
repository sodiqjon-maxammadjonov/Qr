package com.sdk.qr.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.qr.domain.model.settings.ThemeMode
import com.sdk.qr.domain.repository.settings.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val repository: ThemeRepository
) : ViewModel() {

    val themeMode = repository.themeMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 10_000,
            replayExpirationMillis = 0
        ),
        initialValue = ThemeMode.SYSTEM
    )

    fun changeThemeMode(mode: ThemeMode) {
        if (themeMode.value == mode) return

        viewModelScope.launch {
            try {
                repository.setThemeMode(mode)
            } catch (e: Exception) {
                // Error handling
            }
        }
    }

    fun isCurrentMode(mode: ThemeMode): Boolean = themeMode.value == mode
}