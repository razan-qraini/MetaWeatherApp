package com.razanqraini.metaweatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.razanqraini.metaweatherapp.di.manager.MetaWeatherManager
import com.razanqraini.metaweatherapp.di.net.model.ApiError
import com.razanqraini.metaweatherapp.di.net.weather.model.LocationInfo
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

    private val _locationsListLiveData = MutableLiveData<List<LocationInfo>>()
    val locationsListLiveData: LiveData<List<LocationInfo>> = _locationsListLiveData

    private val _errorEventLiveData = MutableLiveData<Event<ApiError?>>()
    val errorEventLiveData: LiveData<Event<ApiError?>> = _errorEventLiveData

    private val citiesNames = listOf(
        "Gothenburg",
        "Stockholm",
        "Mountain View",
        "London",
        "New York",
        "Berlin"
    )

    var locationInfoList = emptyList<LocationInfo>()

    init {
        loadLocationsInfo(false)
    }

    private fun loadLocationsInfo(forceRefresh: Boolean) {
        showLoading(forceRefresh)
//        citiesNames.forEach { cityName ->
            metaWeatherManager.getLocationInfo("Stockholm")
                .subscribeResponseOnce(KEY_REQUEST_LOAD_LOCATIONS_INFO) {
                    if (!it.isSuccessful) {
                        _errorEventLiveData.value = Event(it.error)
                    } else {
                        it.data?.let { locationInfo ->
                            locationInfoList.toMutableList().add(locationInfo)
                        }
                    }
                }
//        }
        _locationsListLiveData.value = locationInfoList
        hideLoading()
    }

    fun refreshLocation() {
        loadLocationsInfo(true)
    }

    private fun showLoading(forceRefresh: Boolean) {
        if (_locationsListLiveData.value == null) {
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
        private val KEY_REQUEST_LOAD_LOCATIONS_INFO =
            HomeViewModel::class.java.name + "_LOAD_LOCATIONS_INFO_REQUEST_KEY"
    }
}
