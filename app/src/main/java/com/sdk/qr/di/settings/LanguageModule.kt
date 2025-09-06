package com.sdk.qr.di.settings

import com.sdk.qr.data.preferences.LanguagePreferencesImpl
import com.sdk.qr.data.repositoryimpl.settigns.LanguageRepositoryImpl
import com.sdk.qr.domain.model.settings.LanguagePreferences
import com.sdk.qr.domain.repository.settings.LanguageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LanguageModule {

    @Binds
    abstract fun bindLanguageRepository(impl: LanguageRepositoryImpl): LanguageRepository

    @Binds
    abstract fun bindLanguagePreferences(impl: LanguagePreferencesImpl): LanguagePreferences
}
