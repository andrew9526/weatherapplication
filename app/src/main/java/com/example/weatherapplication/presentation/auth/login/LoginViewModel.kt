package com.example.weatherapplication.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI State for Login Screen
 */
data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignInSuccessful: Boolean = false
)

/**
 * ViewModel for Login Screen
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()
    
    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email, errorMessage = null) }
    }
    
    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password, errorMessage = null) }
    }
    
    fun signIn() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            
            val result = signInUseCase(
                email = _state.value.email,
                password = _state.value.password
            )
            
            _state.update {
                it.copy(
                    isLoading = false,
                    isSignInSuccessful = result.success,
                    errorMessage = result.errorMessage
                )
            }
        }
    }
}
