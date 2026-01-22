package com.example.weatherapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room database entity for storing weather history
 */
@Entity(tableName = "weather_history")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val cityName: String,
    val countryCode: String,
    val temperature: Double,
    val description: String,
    val weatherType: String, // Store enum as String
    val sunriseTime: Long,
    val sunsetTime: Long,
    val timestamp: Long,
    val humidity: Int,
    val windSpeed: Double
)
