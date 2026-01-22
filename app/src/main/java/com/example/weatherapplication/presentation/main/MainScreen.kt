package com.example.weatherapplication.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapplication.presentation.history.HistoryScreen
import com.example.weatherapplication.presentation.history.HistoryViewModel
import com.example.weatherapplication.presentation.weather.WeatherScreen
import com.example.weatherapplication.presentation.weather.WeatherViewModel

/**
 * Main Screen with bottom navigation
 * Contains Weather and History tabs
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedTab == 0) "Current Weather" else "History"
                    )
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.WbSunny, contentDescription = "Weather") },
                    label = { Text("Weather") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.History, contentDescription = "History") },
                    label = { Text("History") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> {
                val viewModel: WeatherViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()
                
                WeatherScreen(
                    state = state,
                    onRefresh = { viewModel.loadWeather() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            1 -> {
                val viewModel: HistoryViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()
                
                HistoryScreen(
                    state = state,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

/**
 * Tab item data class
 */
private data class TabItem(
    val title: String,
    val icon: ImageVector
)
