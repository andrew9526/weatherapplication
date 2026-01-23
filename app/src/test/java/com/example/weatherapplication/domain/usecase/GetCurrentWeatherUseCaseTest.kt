package com.example.weatherapplication.domain.usecase

import com.example.weatherapplication.data.FakeData
import com.example.weatherapplication.domain.repository.LocationRepository
import com.example.weatherapplication.domain.repository.WeatherRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for GetCurrentWeatherUseCase
 */
@ExperimentalCoroutinesApi
class GetCurrentWeatherUseCaseTest {
    
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var locationRepository: LocationRepository
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    
    @Before
    fun setup() {
        weatherRepository = mockk(relaxed = true)
        locationRepository = mockk()
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(
            weatherRepository,
            locationRepository
        )
    }
    
    @Test
    fun `getCurrentWeather with valid location returns weather and saves to history`() = runTest {
        val location = FakeData.fakeLocation
        val weather = FakeData.fakeWeather
        
        coEvery { locationRepository.getCurrentLocation() } returns Result.success(location)
        coEvery { weatherRepository.getCurrentWeather(location) } returns Result.success(weather)

        val result = getCurrentWeatherUseCase()

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(weather)

        coVerify { weatherRepository.saveWeather(weather) }
    }
    
    @Test
    fun `getCurrentWeather with location error returns failure`() = runTest {
        val exception = Exception("Location permission denied")
        coEvery { locationRepository.getCurrentLocation() } returns Result.failure(exception)

        val result = getCurrentWeatherUseCase()

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Location permission denied")

        coVerify(exactly = 0) { weatherRepository.getCurrentWeather(any()) }
        coVerify(exactly = 0) { weatherRepository.saveWeather(any()) }
    }
    
    @Test
    fun `getCurrentWeather with API error returns failure`() = runTest {
        // Given
        val location = FakeData.fakeLocation
        val exception = Exception("API error")
        
        coEvery { locationRepository.getCurrentLocation() } returns Result.success(location)
        coEvery { weatherRepository.getCurrentWeather(location) } returns Result.failure(exception)
        
        // When
        val result = getCurrentWeatherUseCase()
        
        // Then
        assertThat(result.isFailure).isTrue()
        
        // Verify weather was not saved
        coVerify(exactly = 0) { weatherRepository.saveWeather(any()) }
    }
}
