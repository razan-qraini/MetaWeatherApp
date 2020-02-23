package com.razanqraini.metaweatherapp.utils

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.razanqraini.metaweatherapp.di.net.model.ApiError
import com.razanqraini.metaweatherapp.di.net.model.Response
import com.razanqraini.metaweatherapp.utils.extensions.subscribeOnMain
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Base class for the ViewModels that want to use RxJava [Disposable]s, to eliminate the boilerplate code.
 */
abstract class DisposableViewModel : ViewModel() {

    /**
     *  used to store all [Disposable] objects inside the viewModel
     */
    private val compositeDisposable = CompositeDisposable()

    private val disposableMap = mutableMapOf<String, Disposable>()

    /**
     *  clear all [Disposable]s when the [ViewModel] get destroyed, to prevent a memory leaks
     */
    @CallSuper
    override fun onCleared() {
        clearDisposables()
        super.onCleared()
    }

    /**
     *  add [Disposable] to compositeDisposable
     */
    protected fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)


    /**
     *  clears all [Disposable]s inside the compositeDisposable
     */
    private fun clearDisposables() {
        compositeDisposable.dispose()
        disposableMap.values.forEach { it.dispose() }
        disposableMap.clear()
        compositeDisposable.clear()
    }

    /**
     *  Utility function that passes a single instance and a block to get executed by subscribe the single successfully,
     *  if the subscription failed, a custom block will be executed which create an error [Response]
     */
    fun <R : Response<DATA, ERROR>, DATA, ERROR> subscribeResponse(
        single: Single<R>,
        res: (Response<DATA, ERROR>) -> Unit
    ) {
        addDisposable(
            single.subscribeOnMain(
                callback = { res(it) },
                onError = {
                    res(Response.error(null))
                    Timber.e(it)
                }
            )
        )
    }

    /**
     * Subscribes this [Single] on the I/O thread and observes it on the main thread.
     * And dispose the previous [Single] with same [requestId]
     */
    protected fun <R : Response<DATA, ApiError>, DATA> Single<R>.subscribeResponseOnce(
        requestId: String,
        responseCallback: (Response<DATA, ApiError>) -> Unit
    ) {
        if (disposableMap.containsKey(requestId)) {
            disposableMap[requestId]!!.dispose()
        }

        disposableMap[requestId] = subscribeOnMain(
            callback = { responseCallback(it) },
            onError = {
                responseCallback(Response.error(null))
                Timber.e(it.toString())
            }
        )
    }

    /**
     * converts Single<Response> to LiveData<Response>, and invoke an optional callback when data is received.
     */
    protected fun <T> responseLiveData(
        single: Single<Response<T, ApiError>>,
        callback: (Response<T, ApiError>) -> Unit = {}
    ): LiveData<Response<T, ApiError>> {
        val liveData = MutableLiveData<Response<T, ApiError>>()
        subscribeResponse(single) { response ->
            liveData.value = response
            callback(response)
        }
        return liveData
    }

    /**
     * converts Single<T> to LiveData<T>
     */
    protected fun <T> toLiveData(single: Single<T>, callback: (T) -> Unit = {}): LiveData<T> {
        val liveData = MutableLiveData<T>()
        addDisposable(
            single.subscribeOnMain { data ->
                liveData.value = data
                callback(data)
            }
        )
        return liveData
    }
}