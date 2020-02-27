package com.razanqraini.metaweatherapp.di

import com.razanqraini.metaweatherapp.binding.BindingAdapters
import com.razanqraini.metaweatherapp.context.MetaWeatherApplication
import com.razanqraini.metaweatherapp.di.lifecycle.ApplicationLifeCycleListenerImpl
import com.razanqraini.metaweatherapp.di.net.ApiResponseHandler
import com.razanqraini.metaweatherapp.di.net.ApiResponseHandlerProvider
import com.razanqraini.metaweatherapp.di.viewmodel.ViewModelFactoryProvider
import com.razanqraini.metaweatherapp.di.viewmodel.ViewModelsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * This interface is marked with the [Component] annotation.
 * We should define all the modules that can be injected.
 * Also we provide AndroidSupportInjectionModule.class
 * here. This class was not created by me.
 * It is an internal class in Dagger 2.10.
 * Provides our activities and fragments with given module.
 */
@Singleton
@Component(
    modules = [
        ApplicationModuleProvider::class,
        ViewModelsModule::class,
        AndroidSupportInjectionModule::class,
        ActivitiesBuilderModule::class,
        FragmentsBuilderModule::class
    ]
)
interface ApplicationComponent {
    /**
     * We will call this builder interface from our custom Application class.
     * This will set our application object to the ApplicationComponent.
     * So inside the [ApplicationComponent] the application instance is available.
     * So this application instance can be accessed by our modules
     * such as [ApiModule] when needed
     */
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

    fun inject(instance: BindingAdapters)
}