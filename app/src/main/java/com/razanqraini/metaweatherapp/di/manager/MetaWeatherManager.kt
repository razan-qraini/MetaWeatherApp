package com.razanqraini.metaweatherapp.di.manager

import com.razanqraini.metaweatherapp.di.net.model.ApiError
import com.razanqraini.metaweatherapp.di.net.model.Response
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationDay
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchLattLong
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchName
import io.reactivex.Single

interface MetaWeatherManager {
    fun searchLocationByName(
        locationName: String
    ): Single<Response<List<LocationSearchName>, ApiError>>

    fun searchLocationByLattLong(
        lattLong: String
    ): Single<Response<List<LocationSearchLattLong>, ApiError>>

    fun getLocationInfo(
        locationName: String
    ): Single<Response<LocationInfo, ApiError>>

    fun getLocationInfoForDay(
        locationName: String,
        date: String
    ): Single<Response<List<LocationDay>, ApiError>>
}
