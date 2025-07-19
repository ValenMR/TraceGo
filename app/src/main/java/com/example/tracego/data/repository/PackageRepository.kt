package com.example.tracego.data.repository

import com.example.tracego.data.api.RetrofitConfig
import com.example.tracego.data.model.Package
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PackageRepository {
    private val packageApi = RetrofitConfig.packageApi

    suspend fun getPackages(): Result<List<Package>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = packageApi.getPackages()
                if (response.isSuccessful) {
                    val packageResponse = response.body()
                    if (packageResponse != null && packageResponse.success) {
                        Result.success(packageResponse.packages ?: emptyList())
                    } else {
                        Result.failure(Exception(packageResponse?.message ?: "Error al obtener paquetes"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Result.failure(Exception("Error al obtener paquetes: ${response.code()} - $errorBody"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun createPackage(newPackage: Package): Result<Package> {
        return withContext(Dispatchers.IO) {
            try {
                val response = packageApi.createPackage(newPackage)
                if (response.isSuccessful) {
                    response.body()?.let { Result.success(it) }
                        ?: Result.failure(Exception("Respuesta vacía del servidor"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    Result.failure(Exception("Error al crear paquete: ${response.code()} - $errorBody"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
} 