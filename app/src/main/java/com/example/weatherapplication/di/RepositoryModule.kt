package com.example.weatherapplication.di

import com.example.weatherapplication.data.repository.AuthRepositoryImpl
import com.example.weatherapplication.data.repository.LocationRepositoryImpl
import com.example.weatherapplication.data.repository.WeatherRepositoryImpl
import com.example.weatherapplication.domain.repository.AuthRepository
import com.example.weatherapplication.domain.repository.LocationRepository
import com.example.weatherapplication.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for repository bindings
 * Binds repository implementations to interfaces
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    /**
     * Bind WeatherRepositoryImpl to WeatherRepository interface
     */
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
    
    /**
     * Bind AuthRepositoryImpl to AuthRepository interface
     */
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
    
    /**
     * Bind LocationRepositoryImpl to LocationRepository interface
     */
    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository
}
