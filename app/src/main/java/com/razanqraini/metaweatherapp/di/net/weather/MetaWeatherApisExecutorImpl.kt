package com.razanqraini.metaweatherapp.di.net.weather

import com.razanqraini.metaweatherapp.di.net.weather.model.LocationDay
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchLattLong
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchName
import io.reactivex.Single
import javax.inject.Inject

class MetaWeatherApisExecutorImpl @Inject constructor(
    private val metaWeatherApisService: MetaWeatherApisService
) :
    MetaWeatherApisExecutor {

    override fun searchLocationByName(
        name: String
    ): Single<List<LocationSearchName>> {
        return metaWeatherApisService.searchLocationByName(
            name
        )
    }

    override fun searchLocationByLattLong(
        lattLong: String
    ): Single<List<LocationSearchLattLong>> {
        return metaWeatherApisService.searchLocationByLattLong(
            lattLong
        )
    }

    override fun getLocationInfo(
        woeid: Long
    ): Single<LocationInfo> {
        return metaWeatherApisService.getLocationInfo(
            woeid
        )
    }

    override fun getLocationInfoForDay(
        woeid: Long,
        date: String
    ): Single<List<LocationDay>> {
        return metaWeatherApisService.getLocationInfoForDay(
            woeid, date
        )
    }
}