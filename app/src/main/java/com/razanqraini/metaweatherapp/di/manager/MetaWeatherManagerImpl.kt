package com.razanqraini.metaweatherapp.di.manager

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

    override fun searchLocationByName(locationName: String): Single<List<LocationSearchName>> {
        return metaWeatherApisExecutor.searchLocationByName(locationName)
    }

    override fun searchLocationByLattLong(lattLong: String): Single<List<LocationSearchLattLong>> {
        return metaWeatherApisExecutor.searchLocationByLattLong(lattLong)
    }

    /**
     * Get Location information, and a 5 day forecast
     */
    override fun getLocationInfo(locationName: String): Single<LocationInfo> {
        // Get woeid then pass it to get the location info
        return searchLocationByName(locationName).flatMap {
            it.first().woeid.let { woeid ->
                metaWeatherApisExecutor.getLocationInfo(woeid)
            }
        }
    }

    /**
     * Get source information and forecast history for a particular day & location
     */
    override fun getWeatherForDate(
        locationName: String,
        date: String
    ): Single<List<LocationDay>> {
        // Get woeid then pass it to get the location info
        return searchLocationByName(locationName).flatMap {
            it.first().woeid.let { woeid ->
                metaWeatherApisExecutor.getLocationInfoForDay(woeid, date)
            }
        }
    }
}