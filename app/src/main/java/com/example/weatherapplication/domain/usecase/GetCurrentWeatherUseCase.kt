package com.example.weatherapplication.domain.usecase

import com.example.weatherapplication.domain.model.Weather
import com.example.weatherapplication.domain.repository.LocationRepository
import com.example.weatherapplication.domain.repository.WeatherRepository
import javax.inject.Inject

/**
 * Use Case: Get current weather based on device location
 * 
 * This orchestrates:
 * 1. Getting current location
 * 2. Fetching weather for that location
 * 3. Saving the weather to history
 */
class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Result<Weather> {
        // First, get the current location
        val locationResult = locationRepository.getCurrentLocation()
        
        if (locationResult.isFailure) {
            return Result.failure(
                locationResult.exceptionOrNull() 
                    ?: Exception("Failed to get location")
            )
        }
        
        val location = locationResult.getOrNull()!!
        
        // Then, get weather for that location
        val weatherResult = weatherRepository.getCurrentWeather(location)
        
        if (weatherResult.isSuccess) {
            // Save to history if successful
            weatherResult.getOrNull()?.let { weather ->
                weatherRepository.saveWeather(weather)
            }
        }
        
        return weatherResult
    }
}
