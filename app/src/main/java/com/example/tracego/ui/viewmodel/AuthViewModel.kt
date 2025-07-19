package com.example.tracego.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracego.data.model.LoginResponse
import com.example.tracego.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    
    private val authRepository = AuthRepository()
    
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val result = authRepository.login(email, password)
                
                result.fold(
                    onSuccess = { loginResponse ->
                        _uiState.value = AuthUiState.Success(loginResponse)
                        _isLoading.value = false
                    },
                    onFailure = { exception ->
                        _uiState.value = AuthUiState.Error(exception.message ?: "Error desconocido")
                        _errorMessage.value = exception.message
                        _isLoading.value = false
                    }
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Error inesperado")
                _errorMessage.value = e.message
                _isLoading.value = false
            }
        }
    }
}

sealed class AuthUiState {
    object Initial : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val loginResponse: LoginResponse) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
} 