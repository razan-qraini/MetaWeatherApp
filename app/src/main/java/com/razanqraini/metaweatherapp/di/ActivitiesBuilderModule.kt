package com.razanqraini.metaweatherapp.di

import com.razanqraini.metaweatherapp.ui.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivitiesBuilderModule {
    @ContributesAndroidInjector
    abstract fun homeActivity(): HomeActivity
}