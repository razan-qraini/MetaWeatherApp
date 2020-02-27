package com.razanqraini.metaweatherapp.di.photos

import android.widget.ImageView
import androidx.annotation.DrawableRes

interface PhotosManager {
    fun loadWeatherIcon(imageView: ImageView, url: String?, @DrawableRes defaultImage: Int)
}
