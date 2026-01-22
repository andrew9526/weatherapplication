package com.example.weatherapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.data.local.entity.UserEntity

/**
 * Data Access Object for User
 */
@Dao
interface UserDao {
    
    /**
     * Insert user
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    /**
     * Get user by email
     */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?
    
    /**
     * Get user by id
     */
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: String): UserEntity?
    
    /**
     * Delete all users
     */
    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
