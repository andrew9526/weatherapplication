package com.example.weatherapplication.data.mapper

import com.example.weatherapplication.data.local.entity.UserEntity
import com.example.weatherapplication.data.local.entity.WeatherEntity
import com.example.weatherapplication.domain.model.User
import com.example.weatherapplication.domain.model.Weather
import com.example.weatherapplication.domain.model.WeatherType

/**
 * Convert Weather entity to domain model
 */
fun WeatherEntity.toDomain(): Weather {
    return Weather(
        id = id,
        cityName = cityName,
        countryCode = countryCode,
        temperature = temperature,
        description = description,
        weatherType = WeatherType.valueOf(weatherType),
        sunriseTime = sunriseTime,
        sunsetTime = sunsetTime,
        timestamp = timestamp,
        humidity = humidity,
        windSpeed = windSpeed
    )
}

/**
 * Convert domain Weather to entity
 */
fun Weather.toEntity(): WeatherEntity {
    return WeatherEntity(
        id = id,
        cityName = cityName,
        countryCode = countryCode,
        temperature = temperature,
        description = description,
        weatherType = weatherType.name,
        sunriseTime = sunriseTime,
        sunsetTime = sunsetTime,
        timestamp = timestamp,
        humidity = humidity,
        windSpeed = windSpeed
    )
}

/**
 * Convert User entity to domain model
 */
fun UserEntity.toDomain(): User {
    return User(
        id = id,
        email = email,
        name = name
    )
}

/**
 * Convert domain User to entity
 */
fun User.toEntity(passwordHash: String): UserEntity {
    return UserEntity(
        id = id,
        email = email,
        name = name,
        passwordHash = passwordHash
    )
}
