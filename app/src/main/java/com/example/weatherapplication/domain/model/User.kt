package com.example.weatherapplication.domain.model

data class User(
    val id: String,
    val email: String,
    val name: String
)

data class AuthResult(
    val success: Boolean,
    val user: User? = null,
    val errorMessage: String? = null
)
