package com.example.weatherapplication.domain.usecase

import android.util.Patterns
import com.example.weatherapplication.domain.model.AuthResult
import com.example.weatherapplication.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Use Case: Register new user
 * 
 * Validates input and calls repository to create account
 */
class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String, 
        password: String, 
        confirmPassword: String,
        name: String
    ): AuthResult {
        // Validate name
        if (name.isBlank()) {
            return AuthResult(
                success = false,
                errorMessage = "Name cannot be empty"
            )
        }
        
        // Validate email
        if (email.isBlank()) {
            return AuthResult(
                success = false,
                errorMessage = "Email cannot be empty"
            )
        }
        
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return AuthResult(
                success = false,
                errorMessage = "Invalid email format"
            )
        }
        
        // Validate password
        if (password.isBlank()) {
            return AuthResult(
                success = false,
                errorMessage = "Password cannot be empty"
            )
        }
        
        if (password.length < 6) {
            return AuthResult(
                success = false,
                errorMessage = "Password must be at least 6 characters"
            )
        }
        
        if (password != confirmPassword) {
            return AuthResult(
                success = false,
                errorMessage = "Passwords do not match"
            )
        }
        
        return authRepository.register(email, password, name)
    }
}
