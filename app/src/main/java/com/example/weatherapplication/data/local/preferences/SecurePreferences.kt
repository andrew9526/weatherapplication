package com.example.weatherapplication.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Secure preferences manager using DataStore
 * Stores user session data
 */
class SecurePreferences(private val context: Context) {
    
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "secure_prefs"
    )
    
    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }
    
    /**
     * Save logged in user ID
     */
    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }
    
    /**
     * Get logged in user ID as Flow
     */
    fun getUserId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }
    }
    
    /**
     * Clear user session (logout)
     */
    suspend fun clearUserId() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
        }
    }
    
    /**
     * Check if user is logged in
     */
    suspend fun isLoggedIn(): Boolean {
        return context.dataStore.data.first()[USER_ID_KEY] != null
    }
}
