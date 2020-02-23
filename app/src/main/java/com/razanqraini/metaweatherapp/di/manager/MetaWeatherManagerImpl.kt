package com.razanqraini.metaweatherapp.di.manager

import com.razanqraini.metaweatherapp.di.net.model.ApiError
import com.razanqraini.metaweatherapp.di.net.model.Response
import com.razanqraini.metaweatherapp.di.net.weather.MetaWeatherApisExecutor
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationDay
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchLattLong
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchName
import io.reactivex.Single
import javax.inject.Inject

class MetaWeatherManagerImpl @Inject constructor(
    private val metaWeatherApisExecutor: MetaWeatherApisExecutor
) : MetaWeatherManager {
    override fun searchLocationByName(locationName: String): Single<Response<List<LocationSearchName>, ApiError>> {
        return metaWeatherApisExecutor.searchLocationByName(locationName)
    }

    override fun searchLocationByLattLong(lattLong: String): Single<Response<List<LocationSearchLattLong>, ApiError>> {
        return metaWeatherApisExecutor.searchLocationByLattLong(lattLong)
    }

    override fun getLocationInfo(locationName: String): Single<Response<LocationInfo, ApiError>> {
        // Get woeid then pass it to get the location info
        return searchLocationByName(locationName).flatMap {
            it.data?.first()?.woeid?.let { woeid ->
                metaWeatherApisExecutor.getLocationInfo(woeid)
            }
        }
    }

    override fun getLocationInfoForDay(
        locationName: String,
        date: String
    ): Single<Response<List<LocationDay>, ApiError>> {
        // Get woeid then pass it to get the location info
        return searchLocationByName(locationName).flatMap {
            it.data?.first()?.woeid?.let { woeid ->
                metaWeatherApisExecutor.getLocationInfoForDay(woeid, date)
            }
        }
    }
}