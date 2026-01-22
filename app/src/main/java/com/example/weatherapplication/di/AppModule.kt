package com.example.weatherapplication.di

import android.content.Context
import com.example.weatherapplication.data.local.preferences.SecurePreferences
import com.example.weatherapplication.data.location.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for app-level dependencies
 * Provides location service and preferences
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    /**
     * Provides LocationService
     */
    @Provides
    @Singleton
    fun provideLocationService(
        @ApplicationContext context: Context
    ): LocationService {
        return LocationService(context)
    }
    
    /**
     * Provides SecurePreferences
     */
    @Provides
    @Singleton
    fun provideSecurePreferences(
        @ApplicationContext context: Context
    ): SecurePreferences {
        return SecurePreferences(context)
    }
}
