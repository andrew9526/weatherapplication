package com.example.weatherapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.data.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Weather
 */
@Dao
interface WeatherDao {
    
    /**
     * Insert weather record
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)
    
    /**
     * Get all weather records ordered by timestamp (newest first)
     * Returns Flow for reactive updates
     */
    @Query("SELECT * FROM weather_history ORDER BY timestamp DESC")
    fun getAllWeather(): Flow<List<WeatherEntity>>
    
    /**
     * Delete all weather records
     */
    @Query("DELETE FROM weather_history")
    suspend fun deleteAll()
    
    /**
     * Get weather count
     */
    @Query("SELECT COUNT(*) FROM weather_history")
    suspend fun getWeatherCount(): Int
}
