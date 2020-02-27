package com.razanqraini.metaweatherapp.binding

import androidx.databinding.DataBindingComponent
import com.razanqraini.metaweatherapp.di.photos.PhotosManager
import javax.inject.Inject

/**
 * A Data Binding Component implementation for photo managers.
 */
class PhotoManagerDataBindingComponent @Inject constructor(
    photosManager: PhotosManager
) : DataBindingComponent {

    private val adapter = PhotoManagerBindingAdapters(photosManager)

    override fun getPhotoManagerBindingAdapters(): PhotoManagerBindingAdapters {
        return adapter
    }
}
