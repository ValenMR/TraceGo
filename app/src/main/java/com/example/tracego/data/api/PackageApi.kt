package com.example.tracego.data.api

import com.example.tracego.data.model.Package
import com.example.tracego.data.model.PackageListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PackageApi {
    @GET("packages")
    suspend fun getPackages(): Response<PackageListResponse>

    @POST("packages")
    suspend fun createPackage(@Body newPackage: Package): Response<Package>
}