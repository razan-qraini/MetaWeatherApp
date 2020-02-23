package com.razanqraini.metaweatherapp.di.net.weather.model


import com.google.gson.annotations.SerializedName

data class LocationSearchLattLong(
    @SerializedName("distance")
    val distance: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("woeid")
    val woeid: Long,
    @SerializedName("latt_long")
    val lattLong: String
)