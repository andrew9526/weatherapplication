package com.example.weatherapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapplication.presentation.auth.login.LoginScreen
import com.example.weatherapplication.presentation.auth.login.LoginViewModel
import com.example.weatherapplication.presentation.auth.register.RegisterScreen
import com.example.weatherapplication.presentation.auth.register.RegisterViewModel
import com.example.weatherapplication.presentation.main.MainScreen

/**
 * Navigation graph for the app
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Login Screen
        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            
            LoginScreen(
                state = state,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onSignInClick = viewModel::signIn,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToWeather = {
                    navController.navigate(Screen.Weather.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Register Screen
        composable(Screen.Register.route) {
            val viewModel: RegisterViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            
            RegisterScreen(
                state = state,
                onNameChange = viewModel::onNameChange,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
                onRegisterClick = viewModel::register,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToWeather = {
                    navController.navigate(Screen.Weather.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Main Screen (Weather + History with bottom navigation)
        composable(Screen.Weather.route) {
            MainScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
