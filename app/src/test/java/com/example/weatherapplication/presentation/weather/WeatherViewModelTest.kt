package com.example.weatherapplication.presentation.weather

import com.example.weatherapplication.data.FakeData
import com.example.weatherapplication.domain.usecase.GetCurrentWeatherUseCase
import com.example.weatherapplication.util.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for WeatherViewModel
 */
@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    private lateinit var viewModel: WeatherViewModel
    
    @Before
    fun setup() {
        getCurrentWeatherUseCase = mockk()
    }
    
    @Test
    fun `loadWeather with success updates state with weather data`() = runTest {
        // Given
        val weather = FakeData.fakeWeather
        coEvery { getCurrentWeatherUseCase() } returns Result.success(weather)
        
        // When
        viewModel = WeatherViewModel(getCurrentWeatherUseCase)
        advanceUntilIdle()
        
        // Then
        val state = viewModel.state.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.weather).isEqualTo(weather)
        assertThat(state.errorMessage).isNull()
    }
    
    @Test
    fun `loadWeather with error updates state with error message`() = runTest {
        // Given
        val errorMessage = "Failed to fetch weather"
        coEvery { getCurrentWeatherUseCase() } returns Result.failure(Exception(errorMessage))
        
        // When
        viewModel = WeatherViewModel(getCurrentWeatherUseCase)
        advanceUntilIdle()
        
        // Then
        val state = viewModel.state.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.weather).isNull()
        assertThat(state.errorMessage).isEqualTo(errorMessage)
    }
    
    @Test
    fun `loadWeather is called on init`() = runTest {
        // Given
        coEvery { getCurrentWeatherUseCase() } returns Result.success(FakeData.fakeWeather)
        
        // When
        viewModel = WeatherViewModel(getCurrentWeatherUseCase)
        advanceUntilIdle()

        assertThat(viewModel.state.value.weather).isNotNull()
    }
    
    @Test
    fun `calling loadWeather again refreshes data`() = runTest {
        // Given
        val weather1 = FakeData.fakeWeather
        val weather2 = FakeData.fakeWeather.copy(temperature = 30.0)
        
        coEvery { getCurrentWeatherUseCase() } returns Result.success(weather1)
        
        viewModel = WeatherViewModel(getCurrentWeatherUseCase)
        advanceUntilIdle()

        assertThat(viewModel.state.value.weather?.temperature).isEqualTo(28.5)

        coEvery { getCurrentWeatherUseCase() } returns Result.success(weather2)
        viewModel.loadWeather()
        advanceUntilIdle()
        
        // Then
        assertThat(viewModel.state.value.weather?.temperature).isEqualTo(30.0)
    }
}
