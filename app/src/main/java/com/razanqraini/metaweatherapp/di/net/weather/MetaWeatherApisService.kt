package com.razanqraini.metaweatherapp.di.net.weather

import com.razanqraini.metaweatherapp.di.net.weather.model.LocationDay
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchLattLong
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchName
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MetaWeatherApisService {

    @GET("api/location/search/")
    fun searchLocationByName(
        @Query("query") name: String
    ): Single<List<LocationSearchName>>

    @GET("api/location/search/")
    fun searchLocationByLattLong(
        @Query("lattlong") lattLong: String
    ): Single<List<LocationSearchLattLong>>

    @GET("api/location/{woeid}/")
    fun getLocationInfo(
        @Path("woeid") woeid: Long
    ): Single<LocationInfo>

    @GET("api/location/{woeid}/{date}/")
    fun getLocationInfoForDay(
        @Path("woeid") woeid: Long,
        @Path("date") date: String
    ): Single<List<LocationDay>>
}