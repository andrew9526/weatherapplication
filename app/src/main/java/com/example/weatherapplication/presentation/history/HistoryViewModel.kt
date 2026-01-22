package com.example.weatherapplication.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.domain.model.Weather
import com.example.weatherapplication.domain.usecase.GetWeatherHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI State for History Screen
 */
data class HistoryState(
    val weatherList: List<Weather> = emptyList(),
    val isLoading: Boolean = true
)

/**
 * ViewModel for History Screen
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getWeatherHistoryUseCase: GetWeatherHistoryUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state.asStateFlow()
    
    init {
        loadHistory()
    }
    
    private fun loadHistory() {
        viewModelScope.launch {
            getWeatherHistoryUseCase().collect { weatherList ->
                _state.value = HistoryState(
                    weatherList = weatherList,
                    isLoading = false
                )
            }
        }
    }
}
