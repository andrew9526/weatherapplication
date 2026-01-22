package com.example.weatherapplication

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.weatherapplication.domain.repository.AuthRepository
import com.example.weatherapplication.presentation.navigation.NavGraph
import com.example.weatherapplication.presentation.navigation.Screen
import com.example.weatherapplication.presentation.theme.WeatherApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var authRepository: AuthRepository
    
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Handle permission result if needed
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Request location permissions
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        
        setContent {
            WeatherApplicationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    
                    // Determine start destination based on auth state
                    val startDestination by produceState<String?>(
                        initialValue = null
                    ) {
                        val isSignedIn = authRepository.isSignedIn()
                        value = if (isSignedIn) Screen.Weather.route else Screen.Login.route
                    }
                    
                    // Wait for start destination to be determined
                    startDestination?.let { destination ->
                        NavGraph(
                            navController = navController,
                            startDestination = destination
                        )
                    }
                }
            }
        }
    }
}
