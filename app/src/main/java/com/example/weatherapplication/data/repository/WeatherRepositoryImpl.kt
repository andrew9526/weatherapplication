package com.example.weatherapplication.data.repository

import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.data.local.dao.WeatherDao
import com.example.weatherapplication.data.mapper.toDomain
import com.example.weatherapplication.data.mapper.toEntity
import com.example.weatherapplication.data.remote.api.WeatherApiService
import com.example.weatherapplication.domain.model.Location
import com.example.weatherapplication.domain.model.Weather
import com.example.weatherapplication.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of WeatherRepository
 * Coordinates between API and database
 */
class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService,
    private val weatherDao: WeatherDao
) : WeatherRepository {
    
    override suspend fun getCurrentWeather(location: Location): Result<Weather> {
        return try {
            val response = apiService.getCurrentWeather(
                latitude = location.latitude,
                longitude = location.longitude,
                apiKey = BuildConfig.WEATHER_API_KEY
            )
            
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun getWeatherHistory(): Flow<List<Weather>> {
        return weatherDao.getAllWeather().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun saveWeather(weather: Weather) {
        weatherDao.insertWeather(weather.toEntity())
    }
    
    override suspend fun clearHistory() {
        weatherDao.deleteAll()
    }
}
