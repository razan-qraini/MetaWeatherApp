package com.razanqraini.metaweatherapp.di.net.weather

import com.razanqraini.metaweatherapp.di.net.model.StandardApiResponse
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationDay
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchLattLong
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationSearchName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MetaWeatherApis {

    @GET("/search/")
    fun searchLocationByName(
        @Query("query") name: String
    ): Call<StandardApiResponse<List<LocationSearchName>>>

    @GET("/search/")
    fun searchLocationByLattLong(
        @Query("lattlong") lattLong: String
    ): Call<StandardApiResponse<List<LocationSearchLattLong>>>

    @GET("/{woeid}")
    fun getLocationInfo(
        @Path("woeid") woeid: Long
    ): Call<StandardApiResponse<LocationInfo>>

    @GET("/{woeid}/{date}")
    fun getLocationInfoForDay(
        @Path("woeid") woeid: Long,
        @Path("date") date: String
    ): Call<StandardApiResponse<List<LocationDay>>>
}