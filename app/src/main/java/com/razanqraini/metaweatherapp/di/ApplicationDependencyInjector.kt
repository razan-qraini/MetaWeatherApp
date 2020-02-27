package com.razanqraini.metaweatherapp.di

import android.content.Context
import com.razanqraini.metaweatherapp.binding.BindingAdapters
import com.razanqraini.metaweatherapp.context.MetaWeatherApplication
import com.razanqraini.metaweatherapp.di.lifecycle.ApplicationLifeCycleListenerImpl
import com.razanqraini.metaweatherapp.di.net.ApiResponseHandlerProvider
import com.razanqraini.metaweatherapp.di.viewmodel.ViewModelFactoryProvider

object ApplicationDependencyInjector {

    private fun getApplicationComponent(context: Context): ApplicationComponent =
        (context.applicationContext as MetaWeatherApplication).applicationComponent

    fun inject(context: Context, instance: ApplicationLifeCycleListenerImpl) =
        getApplicationComponent(context).inject(instance)

    fun inject(context: Context, instance: ViewModelFactoryProvider) =
        getApplicationComponent(context).inject(instance)

    fun inject(context: Context, instance: ApiResponseHandlerProvider) =
        getApplicationComponent(context).inject(instance)

    fun inject(context: Context, instance: BindingAdapters) =
        getApplicationComponent(context).inject(instance)
}