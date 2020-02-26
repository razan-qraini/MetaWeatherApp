package com.razanqraini.metaweatherapp.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.razanqraini.metaweatherapp.R
import com.razanqraini.metaweatherapp.di.photos.PhotosManager

/**
 * Binding adapters that work in activities or fragments using a [PhotosManager] instance.
 */
class PhotoManagerBindingAdapters(private val photosManager: PhotosManager) {

    @BindingAdapter("weather_icon")
    fun loadWeatherIcon(
        imageView: ImageView,
        url: String?
    ) {
        photosManager.loadWeatherIcon(imageView, url, R.drawable.ic_error_weather)
    }
}