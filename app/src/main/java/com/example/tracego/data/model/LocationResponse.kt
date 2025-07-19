package com.example.tracego.data.model

// Modelo para la respuesta de la API de localización

data class LocationResponse(
    val packageCode: String,
    val latitude: String,
    val longitude: String
) 