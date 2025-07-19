package com.example.tracego.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracego.data.model.LocationResponse
import com.example.tracego.data.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel(private val repository: LocationRepository) : ViewModel() {
    private val _location = MutableStateFlow<LocationResponse?>(null)
    val location: StateFlow<LocationResponse?> = _location

    fun fetchLocation(packageCode: String) {
        viewModelScope.launch {
            _location.value = repository.getLocation(packageCode)
        }
    }
} 