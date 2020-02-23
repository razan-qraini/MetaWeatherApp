package com.razanqraini.metaweatherapp.di

import android.content.Context
import com.razanqraini.metaweatherapp.context.MetaWeatherApplication
import com.razanqraini.metaweatherapp.di.lifecycle.ApplicationLifeCycleListener
import com.razanqraini.metaweatherapp.di.lifecycle.ApplicationLifeCycleListenerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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

}
