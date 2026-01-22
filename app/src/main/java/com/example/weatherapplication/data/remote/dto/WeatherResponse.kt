package com.example.weatherapplication.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Main response from OpenWeather API
 * Matches: api.openweathermap.org/data/2.5/weather
 */
data class WeatherResponse(
    @SerializedName("coord")
    val coordinates: Coordinates,
    
    @SerializedName("weather")
    val weather: List<WeatherInfo>,
    
    @SerializedName("main")
    val main: MainWeatherData,
    
    @SerializedName("wind")
    val wind: Wind,
    
    @SerializedName("sys")
    val sys: System,
    
    @SerializedName("name")
    val cityName: String,
    
    @SerializedName("dt")
    val timestamp: Long
)

data class Coordinates(
    @SerializedName("lon")
    val longitude: Double,
    
    @SerializedName("lat")
    val latitude: Double
)

data class WeatherInfo(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("main")
    val main: String, // "Rain", "Clear", "Clouds"
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("icon")
    val icon: String
)

data class MainWeatherData(
    @SerializedName("temp")
    val temperature: Double, // in Celsius (we use units=metric)
    
    @SerializedName("feels_like")
    val feelsLike: Double,
    
    @SerializedName("temp_min")
    val tempMin: Double,
    
    @SerializedName("temp_max")
    val tempMax: Double,
    
    @SerializedName("pressure")
    val pressure: Int,
    
    @SerializedName("humidity")
    val humidity: Int
)

data class Wind(
    @SerializedName("speed")
    val speed: Double,
    
    @SerializedName("deg")
    val degree: Int
)

data class System(
    @SerializedName("country")
    val country: String,
    
    @SerializedName("sunrise")
    val sunrise: Long, // Unix timestamp
    
    @SerializedName("sunset")
    val sunset: Long // Unix timestamp
)
