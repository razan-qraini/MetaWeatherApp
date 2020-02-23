package com.razanqraini.metaweatherapp.di.net.weather.model


import com.google.gson.annotations.SerializedName

data class Parent(
    @SerializedName("title")
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("woeid")
    val woeid: Int,
    @SerializedName("latt_long")
    val lattLong: String
)