package com.razanqraini.metaweatherapp.di.net.weather.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationInfo(
    @SerializedName("consolidated_weather")
    val consolidatedWeather: List<ConsolidatedWeather>,
    @SerializedName("time")
    val time: String,
    @SerializedName("sun_rise")
    val sunRise: String,
    @SerializedName("sun_set")
    val sunSet: String,
    @SerializedName("timezone_name")
    val timezoneName: String,
    @SerializedName("parent")
    val parent: Parent,
    @SerializedName("sources")
    val sources: List<Source>,
    @SerializedName("title")
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("woeid")
    val woeid: Long,
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("timezone")
    val timezone: String
) : Parcelable