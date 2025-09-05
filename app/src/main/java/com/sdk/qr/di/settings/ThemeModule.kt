package com.sdk.qr.di.settings

import com.sdk.qr.data.preferences.ThemePreferencesImpl
import com.sdk.qr.data.repositoryimpl.settigns.ThemeRepositoryImpl
import com.sdk.qr.domain.model.ThemePreferences
import com.sdk.qr.domain.repository.settings.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ThemeModule {

    @Binds
    abstract fun bindThemeRepository(impl: ThemeRepositoryImpl): ThemeRepository

    @Binds
    abstract fun bindThemePreferences(impl: ThemePreferencesImpl): ThemePreferences
}