package com.razanqraini.metaweatherapp.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.razanqraini.metaweatherapp.di.net.ApisObserver
import com.razanqraini.metaweatherapp.di.net.response.Response
import com.razanqraini.metaweatherapp.utils.Event
import com.razanqraini.metaweatherapp.utils.EventObserver

/**
 * take advantage of [ApisObserver] and named arguments for cleaner code.
 */
inline fun <T, E> LiveData<Response<T, E>>.observeResponse(
    owner: LifecycleOwner,
    crossinline onSuccess: (T?) -> Unit,
    crossinline onError: (E?) -> Unit
) {
    observe(owner, object : ApisObserver<T, E>() {
        override fun onSuccess(data: T?) {
            onSuccess(data)
        }

        override fun onError(error: E?) {
            onError(error)
        }
    })
}

/**
 * Handle Event Response observation using [onSuccess] and [onError] callbacks.
 *
 * This Response will be handled *once*.
 */
inline fun <T, E> LiveData<Event<Response<T, E>>>.observeEventResponse(
    owner: LifecycleOwner,
    crossinline onSuccess: (T?) -> Unit,
    crossinline onError: (E?) -> Unit
) {
    observe(owner, EventObserver {
        if (it?.isSuccessful == true) {
            onSuccess.invoke(it.data)
        } else {
            onError.invoke(it?.error)
        }
    })
}

/**
 * Observe the [LiveData] and provide [observer] callback instead of [Observer] object.
 */
inline fun <T> LiveData<T>.observe(
    lifecycleOwner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(lifecycleOwner, Observer {
        it?.let(observer)
    })
}

/**
 * Combines this [LiveData] with another [LiveData] using the specified [combiner] and returns the
 * result as a [LiveData].
 */
fun <A, B, Result> LiveData<A>.combine(
    other: LiveData<B>,
    combiner: (A, B) -> Result
): LiveData<Result> {
    val result = MediatorLiveData<Result>()
    result.addSource(this) { a ->
        val b = other.value
        if (b != null) {
            result.postValue(combiner(a, b))
        }
    }
    result.addSource(other) { b ->
        val a = this@combine.value
        if (a != null) {
            result.postValue(combiner(a, b))
        }
    }
    return result
}