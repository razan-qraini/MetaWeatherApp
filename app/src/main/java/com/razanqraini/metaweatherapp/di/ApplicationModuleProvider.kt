package com.razanqraini.metaweatherapp.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.razanqraini.metaweatherapp.BuildConfig
import com.razanqraini.metaweatherapp.context.MetaWeatherApplication
import com.razanqraini.metaweatherapp.di.lifecycle.ApplicationLifeCycleListener
import com.razanqraini.metaweatherapp.di.lifecycle.ApplicationLifeCycleListenerImpl
import com.razanqraini.metaweatherapp.di.manager.MetaWeatherManager
import com.razanqraini.metaweatherapp.di.manager.MetaWeatherManagerImpl
import com.razanqraini.metaweatherapp.di.net.ApiResponseHandler
import com.razanqraini.metaweatherapp.di.net.weather.MetaWeatherApisExecutor
import com.razanqraini.metaweatherapp.di.net.weather.MetaWeatherApisExecutorImpl
import com.razanqraini.metaweatherapp.di.net.weather.MetaWeatherApisService
import com.razanqraini.metaweatherapp.di.photos.PhotosManager
import com.razanqraini.metaweatherapp.di.photos.PhotosManagerImpl
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Provides dependency objects like Retrofit, Utils, Context, Gson, etc
 */
@Module
class ApplicationModuleProvider {

    @Provides
    @Singleton
    fun provideApplicationContext(metaWeatherApplication: MetaWeatherApplication): Context =
        metaWeatherApplication

    @Provides
    @Singleton
    fun provideActivityLifeCycleListener(): ApplicationLifeCycleListener =
        ApplicationLifeCycleListenerImpl()

    @Provides
    @Singleton
    fun provideApiResponseHandler(): ApiResponseHandler = ApiResponseHandler()

    @Provides
    @Singleton
    fun provideMetaWeatherApisExecutor(metaWeatherApisExecutorImpl: MetaWeatherApisExecutorImpl): MetaWeatherApisExecutor =
        metaWeatherApisExecutorImpl

    @Provides
    @Singleton
    fun provideMetaWeatherManager(metaWeatherManagerImpl: MetaWeatherManagerImpl): MetaWeatherManager =
        metaWeatherManagerImpl

    @Provides
    @Singleton
    fun providePhotoManager(photosManagerImpl: PhotosManagerImpl): PhotosManager = photosManagerImpl

    /*
     * The method returns the Gson object
     * */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    /*
     * The method returns the Okhttp object
     * */
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.META_WEATHER_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides the MetaWeatherApi service implementation
     * @param gson
     * @param okHttpClient
     * @return the MetaWeatherApi service implementation
     */
    @Provides
    @Singleton
    fun provideMetaWeatherApiService(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): MetaWeatherApisService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.META_WEATHER_DOMAIN)
            .client(okHttpClient)
            .build()
            .create(MetaWeatherApisService::class.java)
    }
}