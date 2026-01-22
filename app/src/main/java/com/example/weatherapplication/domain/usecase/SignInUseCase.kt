package com.example.weatherapplication.domain.usecase

import android.util.Patterns
import com.example.weatherapplication.domain.model.AuthResult
import com.example.weatherapplication.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Use Case: Sign in user
 * 
 * Validates input and calls repository to authenticate
 */
class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AuthResult {
        // Validate input
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
        
        return authRepository.signIn(email, password)
    }
}
