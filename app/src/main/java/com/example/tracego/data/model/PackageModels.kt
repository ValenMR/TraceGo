package com.example.tracego.data.model

import com.google.gson.annotations.SerializedName

data class Package(
    @SerializedName("id")
    val id: Int,
    @SerializedName("packageName")
    val packageName: String,
    @SerializedName("packageCode")
    val packageCode: String,
    @SerializedName("supplierCode")
    val supplierCode: String,
    @SerializedName("creationDate")
    val creationDate: String,
    @SerializedName("estimatedDeliveryDate")
    val estimatedDeliveryDate: String,
    @SerializedName("stage")
    val stage: String,
    @SerializedName("status")
    val status: String
)

data class PackageListResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("packages")
    val packages: List<Package>?,
    @SerializedName("total")
    val total: Int?
)

