package com.razanqraini.metaweatherapp.di.net.weather

import android.content.Context
import com.razanqraini.metaweatherapp.BuildConfig
import com.razanqraini.metaweatherapp.di.net.ApisExecutor
import com.razanqraini.metaweatherapp.di.net.model.ApiError
import com.razanqraini.metaweatherapp.di.net.model.Response
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationDay
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchLattLong
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchName
import com.razanqraini.metaweatherapp.utils.extensions.mapResponse
import io.reactivex.Single
import javax.inject.Inject

class MetaWeatherApisExecutorImpl @Inject constructor(context: Context) :
    ApisExecutor<MetaWeatherApis>(
        context,
        BuildConfig.META_WEATHER_DOMAIN,
        MetaWeatherApis::class.java
    ), MetaWeatherApisExecutor {

    override fun searchLocationByName(
        name: String
    ): Single<Response<List<LocationSearchName>, ApiError>> {
        return enqueueCall(
            apiService.searchLocationByName(
                name
            )
        ).mapResponse {
            it
        }
    }

    override fun searchLocationByLattLong(
        lattLong: String
    ): Single<Response<List<LocationSearchLattLong>, ApiError>> {
        return enqueueCall(
            apiService.searchLocationByLattLong(
                lattLong
            )
        ).mapResponse {
            it
        }
    }

    override fun getLocationInfo(
        woeid: Long
    ): Single<Response<LocationInfo, ApiError>> {
        return enqueueCall(
            apiService.getLocationInfo(
                woeid
            )
        ).mapResponse {
            it
        }
    }

    override fun getLocationInfoForDay(
        woeid: Long,
        date: String
    ): Single<Response<List<LocationDay>, ApiError>> {
        return enqueueCall(
            apiService.getLocationInfoForDay(
                woeid, date
            )
        ).mapResponse {
            it
        }
    }
}