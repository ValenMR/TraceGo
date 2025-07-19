package com.example.tracego.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracego.data.model.Package
import com.example.tracego.data.repository.PackageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PackageListViewModel : ViewModel() {
    private val packageRepository = PackageRepository()

    private val _uiState = MutableStateFlow<PackageListUiState>(PackageListUiState.Initial)
    val uiState: StateFlow<PackageListUiState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _packages = MutableStateFlow<List<Package>>(emptyList())
    val packages: StateFlow<List<Package>> = _packages.asStateFlow()

    fun loadPackages() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = packageRepository.getPackages()
                result.fold(
                    onSuccess = { packagesList ->
                        _packages.value = packagesList
                        _uiState.value = PackageListUiState.Success(packagesList)
                        _isLoading.value = false
                    },
                    onFailure = { exception ->
                        _uiState.value = PackageListUiState.Error(exception.message ?: "Error desconocido")
                        _errorMessage.value = exception.message
                        _isLoading.value = false
                    }
                )
            } catch (e: Exception) {
                _uiState.value = PackageListUiState.Error(e.message ?: "Error inesperado")
                _errorMessage.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun createPackage(newPackage: Package, onSuccess: (() -> Unit)? = null, onError: ((String) -> Unit)? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = packageRepository.createPackage(newPackage)
                result.fold(
                    onSuccess = { createdPackage ->
                        _packages.value = listOf(createdPackage) + _packages.value
                        _uiState.value = PackageListUiState.Success(_packages.value)
                        _isLoading.value = false
                        onSuccess?.invoke()
                    },
                    onFailure = { exception ->
                        _errorMessage.value = exception.message
                        _isLoading.value = false
                        onError?.invoke(exception.message ?: "Error desconocido")
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _isLoading.value = false
                onError?.invoke(e.message ?: "Error inesperado")
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun refresh() {
        loadPackages()
    }
}

sealed class PackageListUiState {
    object Initial : PackageListUiState()
    object Loading : PackageListUiState()
    data class Success(val packages: List<Package>) : PackageListUiState()
    data class Error(val message: String) : PackageListUiState()
} 