package com.example.weatherapplication.data.repository

import com.example.weatherapplication.data.location.LocationService
import com.example.weatherapplication.domain.model.Location
import com.example.weatherapplication.domain.repository.LocationRepository
import javax.inject.Inject

/**
 * Implementation of LocationRepository
 */
class LocationRepositoryImpl @Inject constructor(
    private val locationService: LocationService
) : LocationRepository {
    
    override suspend fun getCurrentLocation(): Result<Location> {
        return locationService.getCurrentLocation()
    }
    
    override suspend fun hasLocationPermission(): Boolean {
        return locationService.hasLocationPermission()
    }
}
