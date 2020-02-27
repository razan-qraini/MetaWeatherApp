package com.razanqraini.metaweatherapp.di.photos

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

class PhotosManagerImpl @Inject constructor(
    private val context: Context
) : PhotosManager {
    override fun loadWeatherIcon(
        imageView: ImageView,
        url: String?, @DrawableRes defaultImage: Int
    ) {
        Glide.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions().error(defaultImage))
            .into(imageView)
    }
}
