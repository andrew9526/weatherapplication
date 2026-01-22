package com.example.weatherapplication.domain.repository

import com.example.weatherapplication.domain.model.Location
import com.example.weatherapplication.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    /**
     * Get current weather for a specific location from API
     */
    suspend fun getCurrentWeather(location: Location): Result<Weather>
    
    /**
     * Get all weather history from local database
     * Returns a Flow so UI can react to changes automatically
     */
    fun getWeatherHistory(): Flow<List<Weather>>
    
    /**
     * Save weather data to local database
     */
    suspend fun saveWeather(weather: Weather)
    
    /**
     * Clear all weather history
     */
    suspend fun clearHistory()
}
