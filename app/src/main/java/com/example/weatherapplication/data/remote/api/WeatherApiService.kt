package com.example.weatherapplication.data.remote.api

import com.example.weatherapplication.data.remote.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * OpenWeather API Service
 * 
 * Base URL: https://api.openweathermap.org/data/2.5/
 * Documentation: https://openweathermap.org/current
 */
interface WeatherApiService {
    
    /**
     * Get current weather by coordinates
     * 
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     * @param apiKey API key from OpenWeather
     * @param units Measurement units (metric for Celsius)
     * 
     * Example: weather?lat=14.5995&lon=120.9842&appid=YOUR_KEY&units=metric
     */
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}
