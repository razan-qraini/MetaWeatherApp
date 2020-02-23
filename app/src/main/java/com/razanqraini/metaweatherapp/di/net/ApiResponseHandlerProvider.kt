package com.razanqraini.metaweatherapp.di.net

import android.content.Context
import com.razanqraini.metaweatherapp.di.ApplicationDependencyInjector
import javax.inject.Inject

/**
 * This class is only dedicated to provide the ApiResponseHandler to the [ApisExecutor].
 * Since [ApisExecutor] is a typed class, Dagger doesn't have the ability to provide members injector for typed classes,
 * This class is used to fix the issue of providing the [ApiResponseHandler].
 */
class ApiResponseHandlerProvider(context: Context) {

    @Inject
    lateinit var apiResponseHandler: ApiResponseHandler

    init {
        ApplicationDependencyInjector.inject(context, this)
    }
}