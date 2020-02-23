package com.razanqraini.metaweatherapp.di.net.model

import com.google.gson.annotations.SerializedName

sealed class ApiResponse<DATA> {
    @SerializedName("data")
    val data: DATA? = null
}

class LegacyApiResponse<DATA>(
    @SerializedName("status")
    val status: String,
    @SerializedName("status_code")
    val statusCode: Int
) : ApiResponse<DATA>()

class StandardApiResponse<DATA> : ApiResponse<DATA>()