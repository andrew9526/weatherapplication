package com.example.weatherapplication.domain.model

data class Location(
    val latitude: Double,
    val longitude: Double,
    val cityName: String = "",
    val countryCode: String = ""
)
