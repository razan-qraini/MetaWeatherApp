package com.razanqraini.metaweatherapp.di.net

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.razanqraini.metaweatherapp.di.net.model.Response
import com.razanqraini.metaweatherapp.di.net.model.Status

/**
 * A custom Api callback that can receive from [LiveData]
 *
 * @param DATA the response data
 * @param ERROR the response error
 */
abstract class ApisObserver<DATA, ERROR> : Observer<Response<DATA, ERROR>> {

    override fun onChanged(response: Response<DATA, ERROR>?) {
        response?.let {
            when (response.status) {
                Status.SUCCESS -> onSuccess(response.data)
                Status.ERROR -> onError(response.error)
            }
        }
    }

    /**
     * Called when the response data is changed and the response is successful
     *
     * @param data the successful response data
     */
    abstract fun onSuccess(data: DATA?)

    /**
     * Called when the response data is changed and the response is not successful
     *
     * @param error the response error
     */
    abstract fun onError(error: ERROR?)
}