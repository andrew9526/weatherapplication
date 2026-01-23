package com.example.weatherapplication.data

import com.example.weatherapplication.domain.model.Location
import com.example.weatherapplication.domain.model.User
import com.example.weatherapplication.domain.model.Weather
import com.example.weatherapplication.domain.model.WeatherType

/**
 * Fake data objects for testing
 */
object FakeData {
    
    val fakeLocation = Location(
        latitude = 14.5995,
        longitude = 120.9842,
        cityName = "Manila",
        countryCode = "PH"
    )
    
    val fakeWeather = Weather(
        id = 1,
        cityName = "Manila",
        countryCode = "PH",
        temperature = 28.5,
        description = "Clear sky",
        weatherType = WeatherType.CLEAR,
        sunriseTime = 1640000000,
        sunsetTime = 1640040000,
        timestamp = System.currentTimeMillis(),
        humidity = 70,
        windSpeed = 5.5
    )
    
    val fakeWeatherList = listOf(
        fakeWeather,
        fakeWeather.copy(
            id = 2,
            temperature = 27.0,
            timestamp = System.currentTimeMillis() - 3600000
        ),
        fakeWeather.copy(
            id = 3,
            temperature = 29.0,
            timestamp = System.currentTimeMillis() - 7200000
        )
    )
    
    val fakeUser = User(
        id = "user123",
        email = "test@example.com",
        name = "Test User"
    )
}
