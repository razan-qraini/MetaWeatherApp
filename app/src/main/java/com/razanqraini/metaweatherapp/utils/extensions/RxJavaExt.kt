package com.razanqraini.metaweatherapp.utils.extensions

import com.razanqraini.metaweatherapp.di.net.response.Response
import com.razanqraini.metaweatherapp.di.net.response.Status
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * ensure that the subscription handled in main thread.
 */
@CheckReturnValue
inline fun <T> Single<T>.subscribeOnMain(
    crossinline onError: (Throwable) -> Unit = { Timber.e(it) },
    crossinline callback: (T) -> Unit
): Disposable {
    return subscribeOn(Schedulers.io()) //if the single not subscribed on a thread, then use (io) thread.
        .observeOn(AndroidSchedulers.mainThread()) //get the final result in main thread
        .subscribe(
            { callback(it) },
            { onError(it) })
}

/**
 * ensure that the subscription handled in main thread.
 */
inline fun <T> PublishSubject<T>.subscribeOnMain(
    crossinline onError: (Throwable) -> Unit = { Timber.e(it) },
    crossinline callback: (T) -> Unit
): Disposable {
    return subscribeOn(Schedulers.io()) //if the single not subscribed on a thread, then use (io) thread.
        .observeOn(AndroidSchedulers.mainThread()) //get the final result in main thread
        .subscribe(
            { callback(it) },
            { onError(it) }
        )
}

/**
 * ensure that the subscription handled in main thread.
 */
inline fun <T> BehaviorSubject<T>.subscribeOnMain(
    crossinline onError: (Throwable) -> Unit = { Timber.e(it) },
    crossinline callback: (T) -> Unit
): Disposable {
    return subscribeOn(Schedulers.io()) //if the single not subscribed on a thread, then use (io) thread.
        .observeOn(AndroidSchedulers.mainThread()) //get the final result in main thread
        .subscribe(
            { callback(it) },
            { onError(it) }
        )
}

/**
 * zip Single<Response<DATA, E>> into Single<Response<DATA2, E>> by passing a callback to zip DATA to DATA2
 */
inline fun <DATA, DATA2, ERROR> Single<Response<DATA, ERROR>>.mapResponse(crossinline map: (DATA?) -> DATA2?): Single<Response<DATA2, ERROR>> {
    return map { response ->
        response.map { map(it) }
    }
}

/**
 * execute a [block] if response has succeed, before return the result.
 */
inline fun <DATA, ERROR> Single<Response<DATA, ERROR>>.doOnSuccessResponse(crossinline block: (DATA?) -> Unit): Single<Response<DATA, ERROR>> {
    return map { response ->
        if (response.isSuccessful) {
            block(response.data)
        }
        response
    }
}

/**
 * map Single<Response<DATA, ERROR>> into Single<Response<DATA2, ERROR>> by passing a callback to map DATA to DATA2 if DATA is not null
 */
inline fun <DATA, DATA2, ERROR> Single<Response<DATA, ERROR>>.mapNotNullResponse(crossinline map: (DATA) -> DATA2?): Single<Response<DATA2, ERROR>> {
    return map { response ->
        response.mapNotNull { map(it) }
    }
}

/**
 * flat map Single<Response<DATA, ERROR>> into Single<Response<DATA2, ERROR>> by passing a callback to map DATA to DATA2
 */
inline fun <DATA, DATA2, ERROR> Single<Response<DATA, ERROR>>.flatMapResponse(crossinline map: (DATA?) -> Single<Response<DATA2, ERROR>>): Single<Response<DATA2, ERROR>> =
    flatMap { (status, data, error) ->
        if (status == Status.SUCCESS)
            map(data)
        else
            Single.just(Response.error(error))
    }

/**
 * combine tow responses into one response through a mapper that take the data from the first and second responses and return a new data to wrap in the new response.
 */
inline fun <DATA, DATA2, RESULT, ERROR> Single<Response<DATA, ERROR>>.zipWithResponse(
    secondSingle: Single<Response<DATA2, ERROR>>,
    crossinline map: (DATA?, DATA2?) -> RESULT?
): Single<Response<RESULT, ERROR>> =
    zipWith(secondSingle) { firstResponse, secondResponse ->
        if (firstResponse.status == Status.SUCCESS && secondResponse.status == Status.SUCCESS) {
            Response.success<RESULT, ERROR>(map(firstResponse.data, secondResponse.data))
        } else if (firstResponse.status == Status.ERROR) {
            Response.error(firstResponse.error)
        } else {
            Response.error(secondResponse.error)
        }
    }

fun <DATA, ERROR> Single<Response<DATA, ERROR>>.voidResponse(): Single<Response<Void, ERROR>> =
    mapResponse { null }

inline fun <DATA, ERROR> Single<Response<DATA, ERROR>>.doAfterSuccessResponse(crossinline callback: (DATA?) -> Unit): Single<Response<DATA, ERROR>> =
    doAfterSuccess {
        if (it.status == Status.SUCCESS) {
            callback(it.data)
        }
    }

inline fun <DATA, DATA2, ERROR> Single<Response<DATA, ERROR>>.flatMapNotNullResponse(crossinline map: (DATA) -> Single<Response<DATA2, ERROR>>): Single<Response<DATA2, ERROR>> =
    flatMap { response ->
        if (response.status == Status.SUCCESS)
            response.data?.let(map) ?: Single.just(Response.error<DATA2, ERROR>(null))
        else
            Single.just(Response.error(response.error))
    }