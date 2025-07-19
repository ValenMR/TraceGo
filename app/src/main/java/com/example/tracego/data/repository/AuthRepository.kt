package com.example.tracego.data.repository

import com.example.tracego.data.api.RetrofitConfig
import com.example.tracego.data.model.LoginRequest
import com.example.tracego.data.model.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {
    
    private val authApi = RetrofitConfig.authApi
    
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = authApi.login(loginRequest)
                
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        Result.success(loginResponse)
                    } else {
                        Result.failure(Exception("Respuesta vacía del servidor"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Result.failure(Exception("Error de autenticación: ${response.code()} - $errorBody"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}