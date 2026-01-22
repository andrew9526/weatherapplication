package com.example.weatherapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room database entity for storing user data
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    
    val email: String,
    val name: String,
    val passwordHash: String // Never store plain text passwords!
)
