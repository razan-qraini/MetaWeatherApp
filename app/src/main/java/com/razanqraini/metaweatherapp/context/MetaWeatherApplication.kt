package com.razanqraini.metaweatherapp.context

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.razanqraini.metaweatherapp.di.ApplicationComponent
import com.razanqraini.metaweatherapp.di.DaggerApplicationComponent
import com.razanqraini.metaweatherapp.di.lifecycle.ApplicationLifeCycleListener
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject


class MetaWeatherApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    val applicationComponent: ApplicationComponent by lazy { initializeDaggerComponent() }

    @Inject
    lateinit var dispatchingAndroidInjectorActivity: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingAndroidInjectorFragment: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var applicationLifeCycleListener: ApplicationLifeCycleListener

    override fun activityInjector() = dispatchingAndroidInjectorActivity

    override fun onCreate() {
        super.onCreate()
        setupTimber()

        applicationComponent.inject(this)
        registerActivityLifecycleCallbacks(applicationLifeCycleListener)
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

    /**
     * we use our AppComponent (now prefixed with Dagger)
     * to inject our Application class.
     * This way a DispatchingAndroidInjector is injected which is
     * then returned when an injector for an activity is requested.
     * */
    private fun initializeDaggerComponent() =
        DaggerApplicationComponent.builder()
            .application(this)
            .build()


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjectorFragment
    }
}