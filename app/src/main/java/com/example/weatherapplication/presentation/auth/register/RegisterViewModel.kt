package com.example.weatherapplication.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI State for Register Screen
 */
data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegistrationSuccessful: Boolean = false
)

/**
 * ViewModel for Register Screen
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()
    
    fun onNameChange(name: String) {
        _state.update { it.copy(name = name, errorMessage = null) }
    }
    
    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email, errorMessage = null) }
    }
    
    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password, errorMessage = null) }
    }
    
    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.update { it.copy(confirmPassword = confirmPassword, errorMessage = null) }
    }
    
    fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            
            val result = registerUseCase(
                email = _state.value.email,
                password = _state.value.password,
                confirmPassword = _state.value.confirmPassword,
                name = _state.value.name
            )
            
            _state.update {
                it.copy(
                    isLoading = false,
                    isRegistrationSuccessful = result.success,
                    errorMessage = result.errorMessage
                )
            }
        }
    }
}
