package com.razanqraini.metaweatherapp.di.net.weather

import com.razanqraini.metaweatherapp.di.net.model.ApiError
import com.razanqraini.metaweatherapp.di.net.model.Response
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationDay
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchLattLong
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchName
import io.reactivex.Single

interface MetaWeatherApisExecutor {

    fun searchLocationByName(
        name: String
    ): Single<Response<List<LocationSearchName>, ApiError>>

    fun searchLocationByLattLong(
        lattLong: String
    ): Single<Response<List<LocationSearchLattLong>, ApiError>>

    fun getLocationInfo(
        woeid: Long
    ): Single<Response<LocationInfo, ApiError>>

    fun getLocationInfoForDay(
        woeid: Long,
        date: String
    ): Single<Response<List<LocationDay>, ApiError>>
}