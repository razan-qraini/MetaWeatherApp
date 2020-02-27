package com.razanqraini.metaweatherapp.di.manager

import com.razanqraini.metaweatherapp.di.net.weather.model.LocationDay
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchLattLong
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchName
import io.reactivex.Single

interface MetaWeatherManager {
    fun searchLocationByName(
        locationName: String
    ): Single<List<LocationSearchName>>

    fun searchLocationByLattLong(
        lattLong: String
    ): Single<List<LocationSearchLattLong>>

    fun getLocationInfo(
        locationName: String
    ): Single<LocationInfo>

    fun getWeatherForDate(
        locationName: String,
        date: String
    ): Single<List<LocationDay>>
}