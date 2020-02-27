package com.razanqraini.metaweatherapp.di.net.weather.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Source(
    @SerializedName("title")
    val title: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("crawl_rate")
    val crawlRate: Int
)