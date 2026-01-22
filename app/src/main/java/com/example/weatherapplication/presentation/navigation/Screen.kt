package com.example.weatherapplication.presentation.navigation

/**
 * Navigation routes for the app
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Weather : Screen("weather")
    object History : Screen("history")
}
