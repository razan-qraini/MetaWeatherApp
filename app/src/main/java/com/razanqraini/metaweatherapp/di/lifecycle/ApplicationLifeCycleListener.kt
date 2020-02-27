package com.razanqraini.metaweatherapp.di.lifecycle

import android.app.Activity
import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver

interface ApplicationLifeCycleListener : Application.ActivityLifecycleCallbacks,
    DefaultLifecycleObserver {

    fun setCurrentActivity(activity: Activity)

    fun getCurrentActivity(): Activity?

    fun isAppInForeground(): Boolean

    fun registerForLifeCycleChange(listener: LifeCycleChangeListener)

    fun unRegisterForLifeCycleChange(listener: LifeCycleChangeListener)

    interface LifeCycleChangeListener {
        fun onLifecycleChanged(status: LifeCycleStatus)

        fun onActivityResumed(activity: Activity) {}
    }

    enum class LifeCycleStatus {

        /**
         * Indicates that the application is currently in foreground
         */
        IN_FOREGROUND,

        /**
         * Indicates that the application is currently in background
         */
        IN_BACKGROUND,

        /**
         * Indicates that the application is currently in the foreground but has been killed recently
         */
        KILLED
    }
}