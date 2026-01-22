package com.example.weatherapplication.domain.usecase

import com.example.weatherapplication.domain.model.Weather
import com.example.weatherapplication.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use Case: Get weather history
 * 
 * Returns all previously fetched weather data
 * sorted by most recent first
 */
class GetWeatherHistoryUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<List<Weather>> {
        return weatherRepository.getWeatherHistory()
    }
}
