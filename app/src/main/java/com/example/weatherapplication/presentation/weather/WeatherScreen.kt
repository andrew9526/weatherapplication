package com.example.weatherapplication.presentation.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.weatherapplication.domain.model.Weather
import com.example.weatherapplication.domain.model.WeatherType
import java.text.SimpleDateFormat
import java.util.*

/**
 * Weather Screen UI
 */
@Composable
fun WeatherScreen(
    state: WeatherState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            state.errorMessage != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onRefresh) {
                        Text("Retry")
                    }
                }
            }
            
            state.weather != null -> {
                WeatherContent(
                    weather = state.weather,
                    onRefresh = onRefresh
                )
            }
        }
    }
}

@Composable
private fun WeatherContent(
    weather: Weather,
    onRefresh: () -> Unit
) {
    // Location
    Text(
        text = "${weather.cityName}, ${weather.countryCode}",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary
    )
    
    Spacer(modifier = Modifier.height(32.dp))
    
    // Weather Icon (Sun during day, Moon after 6 PM)
    val weatherIcon = getWeatherIcon(weather)
    Icon(
        imageVector = weatherIcon,
        contentDescription = "Weather Icon",
        modifier = Modifier.size(120.dp),
        tint = MaterialTheme.colorScheme.primary
    )
    
    Spacer(modifier = Modifier.height(24.dp))
    
    // Temperature
    Text(
        text = "${weather.temperature.toInt()}°C",
        style = MaterialTheme.typography.displayLarge
    )
    
    Spacer(modifier = Modifier.height(8.dp))
    
    // Description
    Text(
        text = weather.description.replaceFirstChar { it.uppercase() },
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    
    Spacer(modifier = Modifier.height(48.dp))
    
    // Weather Details Card
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Sunrise
            WeatherDetailRow(
                icon = Icons.Default.WbSunny,
                label = "Sunrise",
                value = formatTime(weather.sunriseTime)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Sunset
            WeatherDetailRow(
                icon = Icons.Default.NightsStay,
                label = "Sunset",
                value = formatTime(weather.sunsetTime)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Humidity
            WeatherDetailRow(
                icon = Icons.Default.WaterDrop,
                label = "Humidity",
                value = "${weather.humidity}%"
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Wind Speed
            WeatherDetailRow(
                icon = Icons.Default.Air,
                label = "Wind Speed",
                value = "${weather.windSpeed} m/s"
            )
        }
    }
    
    Spacer(modifier = Modifier.height(24.dp))
    
    // Refresh Button
    Button(
        onClick = onRefresh,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
        Spacer(modifier = Modifier.width(8.dp))
        Text("Refresh Weather")
    }
}

@Composable
private fun WeatherDetailRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Get weather icon based on weather type and time
 * Shows moon icon if after 6 PM, otherwise shows sun
 */
private fun getWeatherIcon(weather: Weather): ImageVector {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val isNight = currentHour >= 18 // After 6 PM
    
    return when (weather.weatherType) {
        WeatherType.CLEAR -> if (isNight) Icons.Default.NightsStay else Icons.Default.WbSunny
        WeatherType.CLOUDS -> Icons.Default.Cloud
        WeatherType.RAIN -> Icons.Default.WaterDrop
        WeatherType.DRIZZLE -> Icons.Default.WaterDrop
        WeatherType.THUNDERSTORM -> Icons.Default.FlashOn
        WeatherType.SNOW -> Icons.Default.AcUnit
        WeatherType.MIST -> Icons.Default.Cloud
        WeatherType.UNKNOWN -> Icons.Default.Help
    }
}

/**
 * Format Unix timestamp to readable time
 */
private fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp * 1000))
}
