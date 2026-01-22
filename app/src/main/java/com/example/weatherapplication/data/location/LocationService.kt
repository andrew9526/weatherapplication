package com.example.weatherapplication.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.example.weatherapplication.domain.model.Location
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.Locale
import kotlin.coroutines.resume

/**
 * Location service for getting device GPS location
 */
class LocationService(private val context: Context) {
    
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    
    private val geocoder: Geocoder = Geocoder(context, Locale.getDefault())
    
    /**
     * Check if location permissions are granted
     */
    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }
    
    /**
     * Get current device location
     */
    suspend fun getCurrentLocation(): Result<Location> {
        if (!hasLocationPermission()) {
            return Result.failure(SecurityException("Location permission not granted"))
        }
        
        return try {
            val cancellationTokenSource = CancellationTokenSource()
            
            val location = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).await()
            
            if (location == null) {
                Result.failure(Exception("Unable to get location"))
            } else {
                val locationInfo = getLocationInfo(location.latitude, location.longitude)
                
                Result.success(
                    Location(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        cityName = locationInfo.first,
                        countryCode = locationInfo.second
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get city name and country code from coordinates
     */
    private suspend fun getLocationInfo(latitude: Double, longitude: Double): Pair<String, String> {
        return suspendCancellableCoroutine { continuation ->
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                        val address = addresses.firstOrNull()
                        val cityName = address?.locality ?: address?.subAdminArea ?: "Unknown"
                        val countryCode = address?.countryCode ?: "Unknown"
                        continuation.resume(Pair(cityName, countryCode))
                    }
                } else {
                    @Suppress("DEPRECATION")
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    val address = addresses?.firstOrNull()
                    val cityName = address?.locality ?: address?.subAdminArea ?: "Unknown"
                    val countryCode = address?.countryCode ?: "Unknown"
                    continuation.resume(Pair(cityName, countryCode))
                }
            } catch (e: Exception) {
                continuation.resume(Pair("Unknown", "Unknown"))
            }
        }
    }
}
