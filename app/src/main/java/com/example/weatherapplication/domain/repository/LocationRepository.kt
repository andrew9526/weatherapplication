package com.example.weatherapplication.domain.repository

import com.example.weatherapplication.domain.model.Location

interface LocationRepository {
    /**
     * Get the device's current location
     * Requires location permissions to be granted
     */
    suspend fun getCurrentLocation(): Result<Location>
    
    /**
     * Check if location permissions are granted
     */
    suspend fun hasLocationPermission(): Boolean
}
