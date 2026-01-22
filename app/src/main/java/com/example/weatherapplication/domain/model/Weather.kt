package com.example.weatherapplication.domain.model

data class Weather(
    val id: Long = 0,
    val cityName: String,
    val countryCode: String,
    val temperature: Double, // in Celsius
    val description: String,
    val weatherType: WeatherType,
    val sunriseTime: Long, // Unix timestamp
    val sunsetTime: Long,  // Unix timestamp
    val timestamp: Long = System.currentTimeMillis(), // When this weather was fetched
    val humidity: Int,
    val windSpeed: Double
)

enum class WeatherType {
    CLEAR,
    CLOUDS,
    RAIN,
    DRIZZLE,
    THUNDERSTORM,
    SNOW,
    MIST,
    UNKNOWN
}
