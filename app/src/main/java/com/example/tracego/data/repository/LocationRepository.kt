package com.example.tracego.data.repository

import com.example.tracego.data.api.LocationApi
import com.example.tracego.data.model.LocationResponse

class LocationRepository(private val api: LocationApi) {
    suspend fun getLocation(packageCode: String): LocationResponse {
        return api.getLocation(packageCode)
    }
} 