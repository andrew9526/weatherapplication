package com.example.weatherapplication.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapplication.data.local.dao.UserDao
import com.example.weatherapplication.data.local.dao.WeatherDao
import com.example.weatherapplication.data.local.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for database dependencies
 * Provides Room database and DAOs
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    /**
     * Provides Room Database instance
     */
    @Provides
    @Singleton
    fun provideWeatherDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            WeatherDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // For development
            .build()
    }
    
    /**
     * Provides WeatherDao
     */
    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }
    
    /**
     * Provides UserDao
     */
    @Provides
    @Singleton
    fun provideUserDao(database: WeatherDatabase): UserDao {
        return database.userDao()
    }
}
