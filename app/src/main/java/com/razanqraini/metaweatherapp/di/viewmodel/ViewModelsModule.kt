package com.razanqraini.metaweatherapp.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.razanqraini.metaweatherapp.ui.home.HomeViewModel
import com.razanqraini.metaweatherapp.ui.home.WeatherFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelsModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    /**
     * This method basically says
     * Inject this object into a map using the [IntoMap] annotation,
     * with the [HomeViewModel] as key, and a Provider that
     * will build a [HomeViewModel] object
     */
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherFragmentViewModel::class)
    abstract fun bindWeatherFragmentViewModel(weatherFragmentViewModel: WeatherFragmentViewModel): ViewModel
}