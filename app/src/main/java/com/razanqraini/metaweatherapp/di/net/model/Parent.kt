package com.razanqraini.metaweatherapp.di.net.weather.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Parent(
    @SerializedName("title")
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("woeid")
    val woeid: Int,
    @SerializedName("latt_long")
    val lattLong: String
) : Parcelable