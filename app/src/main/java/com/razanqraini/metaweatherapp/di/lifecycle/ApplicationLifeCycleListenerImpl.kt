package com.razanqraini.metaweatherapp.di.lifecycle

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.razanqraini.metaweatherapp.di.Injectable
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

class ApplicationLifeCycleListenerImpl :
    ApplicationLifeCycleListener {

    private val lifeCycleListeners =
        hashSetOf<ApplicationLifeCycleListener.LifeCycleChangeListener>()

    private var currentActivity: Activity? = null

    private var lifeCycleStatus: ApplicationLifeCycleListener.LifeCycleStatus? = null

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun setCurrentActivity(activity: Activity) {
    }

    override fun getCurrentActivity(): Activity? = currentActivity

    override fun isAppInForeground(): Boolean =
        lifeCycleStatus == ApplicationLifeCycleListener.LifeCycleStatus.IN_FOREGROUND

    override fun registerForLifeCycleChange(listener: ApplicationLifeCycleListener.LifeCycleChangeListener) {
        lifeCycleListeners.add(listener)
    }

    override fun unRegisterForLifeCycleChange(listener: ApplicationLifeCycleListener.LifeCycleChangeListener) {
        lifeCycleListeners.remove(listener)
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
        currentActivity = activity
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        injectActivityAndItsFragments(activity)
    }

    override fun onStart(owner: LifecycleOwner) {
        lifeCycleStatus =
            ApplicationLifeCycleListener.LifeCycleStatus.IN_FOREGROUND

        // If an activity is about to be resumed, and the last activity that was visible
        // before the app going to background is now null,
        // this means the app was destroyed while in the background, or killed by the user.
        if (currentActivity == null) {
            notifyLifeCycleListeners(ApplicationLifeCycleListener.LifeCycleStatus.KILLED)
            return
        }
        notifyLifeCycleListeners(ApplicationLifeCycleListener.LifeCycleStatus.IN_FOREGROUND)
    }

    override fun onStop(owner: LifecycleOwner) {
        lifeCycleStatus =
            ApplicationLifeCycleListener.LifeCycleStatus.IN_BACKGROUND
        notifyLifeCycleListeners(ApplicationLifeCycleListener.LifeCycleStatus.IN_BACKGROUND)
    }

    private fun notifyLifeCycleListeners(status: ApplicationLifeCycleListener.LifeCycleStatus) {
        lifeCycleListeners.forEach { it.onLifecycleChanged(status) }
    }

    private fun injectActivityAndItsFragments(activity: Activity) {
        if (activity is Injectable) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fragmentManager: FragmentManager,
                            fragment: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (fragment is Injectable) {
                                AndroidSupportInjection.inject(fragment)
                            }
                        }
                    }, true
                )
        }
    }
}