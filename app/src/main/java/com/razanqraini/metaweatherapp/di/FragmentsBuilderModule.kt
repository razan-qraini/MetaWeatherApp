package com.razanqraini.metaweatherapp.di

import com.razanqraini.metaweatherapp.ui.home.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindWeatherFragment(): WeatherFragment
}