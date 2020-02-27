package com.razanqraini.metaweatherapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.razanqraini.metaweatherapp.di.manager.MetaWeatherManager
import com.razanqraini.metaweatherapp.di.net.response.ApiError
import com.razanqraini.metaweatherapp.utils.DisposableViewModel
import com.razanqraini.metaweatherapp.utils.Event
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val metaWeatherManager: MetaWeatherManager
) : DisposableViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _locationsListLiveData = MutableLiveData<List<String>>()
    val locationsListLiveData: LiveData<List<String>> = _locationsListLiveData

    private val _errorEventLiveData = MutableLiveData<Event<ApiError?>>()
    val errorEventLiveData: LiveData<Event<ApiError?>> = _errorEventLiveData

    private val locationsNames = listOf(
        "Gothenburg",
        "Stockholm",
        "Mountain View",
        "London",
        "New York",
        "Berlin"
    )

    init {
        _locationsListLiveData.value = locationsNames
    }
}
