package com.razanqraini.metaweatherapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.razanqraini.metaweatherapp.di.manager.MetaWeatherManager
import com.razanqraini.metaweatherapp.di.net.response.ApiError
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo
import com.razanqraini.metaweatherapp.utils.DisposableViewModel
import com.razanqraini.metaweatherapp.utils.Event
import com.razanqraini.metaweatherapp.utils.extensions.subscribeOnMain
import javax.inject.Inject

class WeatherFragmentViewModel @Inject constructor(
    private val metaWeatherManager: MetaWeatherManager
) :
    DisposableViewModel() {

    private var locationName: String? = null

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _locationInfoLiveData = MutableLiveData<LocationInfo>()
    val locationInfoLiveData: LiveData<LocationInfo> = _locationInfoLiveData

    private val _errorEventLiveData = MutableLiveData<Event<ApiError?>>()
    val errorEventLiveData: LiveData<Event<ApiError?>> = _errorEventLiveData

    fun refreshLocationInfo() {
        locationName?.let {
            loadLocationInfo(it, true)
        }
    }

    var locationInfo: LocationInfo? = null
        set(value) {
            field = value
            _locationInfoLiveData.value = value
        }

    fun loadLocationInfo(locationName: String, forceRefresh: Boolean) {
        showLoading(forceRefresh)
        this.locationName = locationName

        addDisposable(metaWeatherManager.getLocationInfo(locationName).subscribeOnMain {
            locationInfo = it
            hideLoading()
        })
    }

    private fun showLoading(forceRefresh: Boolean) {
        if (_locationInfoLiveData.value == null) {
            _isLoading.value = true
            _isRefreshing.value = false
        } else {
            _isLoading.value = !forceRefresh
            _isRefreshing.value = forceRefresh
        }
    }

    private fun hideLoading() {
        _isLoading.value = false
        _isRefreshing.value = false
    }

    companion object {
        private val KEY_REQUEST_LOAD_LOCATION_INFO =
            WeatherFragmentViewModel::class.java.name + "_LOAD_LOCATION_INFO_REQUEST_KEY"
    }
}