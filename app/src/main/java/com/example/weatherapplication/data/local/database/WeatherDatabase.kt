package com.example.weatherapplication.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.data.local.dao.UserDao
import com.example.weatherapplication.data.local.dao.WeatherDao
import com.example.weatherapplication.data.local.entity.UserEntity
import com.example.weatherapplication.data.local.entity.WeatherEntity

/**
 * Main Room Database
 */
@Database(
    entities = [
        WeatherEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    
    abstract fun weatherDao(): WeatherDao
    abstract fun userDao(): UserDao
    
    companion object {
        const val DATABASE_NAME = "weather_database"
    }
}
