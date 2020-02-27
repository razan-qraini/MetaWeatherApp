package com.razanqraini.metaweatherapp.di.net.response

import com.google.gson.annotations.SerializedName

class ApiResponse<DATA> {
    @SerializedName("data")
    val data: DATA? = null
}