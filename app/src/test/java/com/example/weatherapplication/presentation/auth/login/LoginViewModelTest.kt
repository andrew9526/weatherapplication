package com.example.weatherapplication.presentation.auth.login

import app.cash.turbine.test
import com.example.weatherapplication.data.FakeData
import com.example.weatherapplication.domain.model.AuthResult
import com.example.weatherapplication.domain.usecase.SignInUseCase
import com.example.weatherapplication.util.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for LoginViewModel
 */
@ExperimentalCoroutinesApi
class LoginViewModelTest {
    
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    
    private val testScheduler get() = mainCoroutineRule.testDispatcher.scheduler
    
    private lateinit var signInUseCase: SignInUseCase
    private lateinit var viewModel: LoginViewModel
    
    @Before
    fun setup() {
        signInUseCase = mockk()
        viewModel = LoginViewModel(signInUseCase)
    }
    
    @Test
    fun `initial state is empty`() {
        // Then
        val state = viewModel.state.value
        assertThat(state.email).isEmpty()
        assertThat(state.password).isEmpty()
        assertThat(state.isLoading).isFalse()
        assertThat(state.errorMessage).isNull()
        assertThat(state.isSignInSuccessful).isFalse()
    }
    
    @Test
    fun `onEmailChange updates email in state`() {
        // When
        viewModel.onEmailChange("test@example.com")
        
        // Then
        assertThat(viewModel.state.value.email).isEqualTo("test@example.com")
    }
    
    @Test
    fun `onPasswordChange updates password in state`() {
        // When
        viewModel.onPasswordChange("password123")
        
        // Then
        assertThat(viewModel.state.value.password).isEqualTo("password123")
    }
    
    @Test
    fun `signIn with valid credentials updates state correctly`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        
        coEvery { 
            signInUseCase(email, password) 
        } returns AuthResult(success = true, user = FakeData.fakeUser)
        
        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        
        // When & Then
        viewModel.signIn()
        
        // Wait for state to update
        testScheduler.advanceUntilIdle()
        
        val finalState = viewModel.state.value
        assertThat(finalState.isLoading).isFalse()
        assertThat(finalState.isSignInSuccessful).isTrue()
        assertThat(finalState.errorMessage).isNull()
    }
    
    @Test
    fun `signIn with invalid credentials shows error`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "wrongpassword"
        val errorMessage = "Invalid email or password"
        
        coEvery { 
            signInUseCase(email, password) 
        } returns AuthResult(success = false, errorMessage = errorMessage)
        
        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        
        // When
        viewModel.signIn()
        
        // Wait for state to update
        testScheduler.advanceUntilIdle()
        
        // Then
        val finalState = viewModel.state.value
        assertThat(finalState.isLoading).isFalse()
        assertThat(finalState.isSignInSuccessful).isFalse()
        assertThat(finalState.errorMessage).isEqualTo(errorMessage)
    }
    
    @Test
    fun `changing email clears error message`() {
        // Given
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("password")
        
        // Simulate error
        coEvery { 
            signInUseCase(any(), any()) 
        } returns AuthResult(success = false, errorMessage = "Error")
        
        // When
        viewModel.onEmailChange("newemail@example.com")
        
        // Then
        assertThat(viewModel.state.value.errorMessage).isNull()
    }
}
