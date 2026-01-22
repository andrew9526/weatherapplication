package com.example.weatherapplication.data.mapper

import com.example.weatherapplication.data.remote.dto.WeatherResponse
import com.example.weatherapplication.domain.model.Weather
import com.example.weatherapplication.domain.model.WeatherType

/**
 * Convert API WeatherResponse to Domain Weather model
 */
fun WeatherResponse.toDomain(): Weather {
    return Weather(
        cityName = cityName,
        countryCode = sys.country,
        temperature = main.temperature,
        description = weather.firstOrNull()?.description ?: "Unknown",
        weatherType = weather.firstOrNull()?.main?.toWeatherType() ?: WeatherType.UNKNOWN,
        sunriseTime = sys.sunrise,
        sunsetTime = sys.sunset,
        timestamp = timestamp * 1000, // Convert to milliseconds
        humidity = main.humidity,
        windSpeed = wind.speed
    )
}

/**
 * Map API weather type string to domain WeatherType enum
 */
private fun String.toWeatherType(): WeatherType {
    return when (this.lowercase()) {
        "clear" -> WeatherType.CLEAR
        "clouds" -> WeatherType.CLOUDS
        "rain" -> WeatherType.RAIN
        "drizzle" -> WeatherType.DRIZZLE
        "thunderstorm" -> WeatherType.THUNDERSTORM
        "snow" -> WeatherType.SNOW
        "mist", "smoke", "haze", "dust", "fog", "sand", "ash", "squall", "tornado" -> WeatherType.MIST
        else -> WeatherType.UNKNOWN
    }
}
