package com.example.weatherapplication.data.repository

import com.example.weatherapplication.data.local.dao.UserDao
import com.example.weatherapplication.data.local.preferences.SecurePreferences
import com.example.weatherapplication.data.mapper.toDomain
import com.example.weatherapplication.data.mapper.toEntity
import com.example.weatherapplication.data.util.SecurityUtil
import com.example.weatherapplication.domain.model.AuthResult
import com.example.weatherapplication.domain.model.User
import com.example.weatherapplication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

/**
 * Implementation of AuthRepository
 */
class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val securePreferences: SecurePreferences
) : AuthRepository {
    
    override suspend fun register(email: String, password: String, name: String): AuthResult {
        return try {
            // Check if user already exists
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                return AuthResult(
                    success = false,
                    errorMessage = "Email already registered"
                )
            }
            
            // Create new user
            val userId = UUID.randomUUID().toString()
            val passwordHash = SecurityUtil.hashPassword(password)
            
            val user = User(
                id = userId,
                email = email,
                name = name
            )
            
            // Save to database
            userDao.insertUser(user.toEntity(passwordHash))
            
            // Save session
            securePreferences.saveUserId(userId)
            
            AuthResult(
                success = true,
                user = user
            )
        } catch (e: Exception) {
            AuthResult(
                success = false,
                errorMessage = "Registration failed: ${e.message}"
            )
        }
    }
    
    override suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            val userEntity = userDao.getUserByEmail(email)
            
            if (userEntity == null) {
                return AuthResult(
                    success = false,
                    errorMessage = "Invalid email or password"
                )
            }
            
            val isPasswordCorrect = SecurityUtil.verifyPassword(password, userEntity.passwordHash)
            
            if (!isPasswordCorrect) {
                return AuthResult(
                    success = false,
                    errorMessage = "Invalid email or password"
                )
            }
            
            // Save session
            securePreferences.saveUserId(userEntity.id)
            
            AuthResult(
                success = true,
                user = userEntity.toDomain()
            )
        } catch (e: Exception) {
            AuthResult(
                success = false,
                errorMessage = "Sign in failed: ${e.message}"
            )
        }
    }
    
    override suspend fun signOut() {
        securePreferences.clearUserId()
    }
    
    override fun getCurrentUser(): Flow<User?> {
        return securePreferences.getUserId().map { userId ->
            userId?.let { userDao.getUserById(it)?.toDomain() }
        }
    }
    
    override suspend fun isSignedIn(): Boolean {
        return securePreferences.isLoggedIn()
    }
}
