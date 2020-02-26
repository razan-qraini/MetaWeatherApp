package com.razanqraini.metaweatherapp.di.net.weather

import com.razanqraini.metaweatherapp.di.net.weather.model.LocationDay
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchLattLong
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchName
import io.reactivex.Single

interface MetaWeatherApisExecutor {

    fun searchLocationByName(
        name: String
    ): Single<List<LocationSearchName>>

    fun searchLocationByLattLong(
        lattLong: String
    ): Single<List<LocationSearchLattLong>>

    fun getLocationInfo(
        woeid: Long
    ): Single<LocationInfo>

    fun getLocationInfoForDay(
        woeid: Long,
        date: String
    ): Single<List<LocationDay>>
}