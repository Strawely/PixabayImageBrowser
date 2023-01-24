package com.solomyannyy.pixabayimagebrowser.di

import com.solomyannyy.pixabayimagebrowser.BuildConfig
import com.solomyannyy.pixabayimagebrowser.Constants.API_KEY_DEPENDENCY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BuildInfoModule {
    @Provides
    @Named(API_KEY_DEPENDENCY)
    @Singleton
    fun provideApiKey() = BuildConfig.API_KEY
}