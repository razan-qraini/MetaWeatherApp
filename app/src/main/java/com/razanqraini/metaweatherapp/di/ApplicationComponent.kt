package com.razanqraini.metaweatherapp.di

import com.razanqraini.metaweatherapp.context.MetaWeatherApplication
import com.razanqraini.metaweatherapp.di.lifecycle.ApplicationLifeCycleListenerImpl
import com.razanqraini.metaweatherapp.di.net.ApiResponseHandler
import com.razanqraini.metaweatherapp.di.net.ApiResponseHandlerProvider
import com.razanqraini.metaweatherapp.di.viewmodel.ViewModelFactoryProvider
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModuleProvider::class,
        AndroidSupportInjectionModule::class,
        ActivitiesBuilderModule::class]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(metaWeatherApplication: MetaWeatherApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(metaWeatherApplication: MetaWeatherApplication)

    fun inject(instance: ApplicationLifeCycleListenerImpl)

    fun inject(instance: ViewModelFactoryProvider)

    fun inject(instance: ApiResponseHandler)

    fun inject(instance: ApiResponseHandlerProvider)
}