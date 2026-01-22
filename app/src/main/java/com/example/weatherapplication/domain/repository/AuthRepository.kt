package com.example.weatherapplication.domain.repository

import com.example.weatherapplication.domain.model.AuthResult
import com.example.weatherapplication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    /**
     * Register a new user
     */
    suspend fun register(email: String, password: String, name: String): AuthResult
    
    /**
     * Sign in with email and password
     */
    suspend fun signIn(email: String, password: String): AuthResult
    
    /**
     * Sign out current user
     */
    suspend fun signOut()
    
    /**
     * Get current logged in user
     * Returns Flow to observe authentication state changes
     */
    fun getCurrentUser(): Flow<User?>
    
    /**
     * Check if user is signed in
     */
    suspend fun isSignedIn(): Boolean
}
