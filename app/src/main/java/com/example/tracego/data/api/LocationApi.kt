package com.example.tracego.data.api

import com.example.tracego.data.model.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationApi {
    @GET("location/{packageCode}")
    suspend fun getLocation(@Path("packageCode") packageCode: String): LocationResponse
} 