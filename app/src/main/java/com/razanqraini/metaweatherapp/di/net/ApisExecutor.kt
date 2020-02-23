package com.razanqraini.metaweatherapp.di.net

import android.content.Context
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.razanqraini.metaweatherapp.di.net.model.ApiError
import com.razanqraini.metaweatherapp.di.net.model.ApiResponse
import com.razanqraini.metaweatherapp.di.net.model.Response
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

/**
 * Base API executor class for all api-executors which handles initializing retrofit APIs
 * and enqueueCall any API response in the application.
 *
 * @param S type of API service class
 *
 * Since this response has only a status code of 200 and retrofit will always consider the response as successful response
 * And it will always try to parse the response even in case of errors and it fails to parse it.
 *
 * So retrofit onSuccess callback will not be invoked and onFailure will be invoked instead.
 *
 * And since retrofit doesn't provide a way to get error response on Failure callback,
 * We need this to determine if the executor has  [com.harri.manage.di.net.model.LegacyApiResponse],
 * only using a response interceptor we can read legacy apis errors [LegacyApiErrorInterceptor] is used.
 *
 * Key: The path of API.
 * Value: JSON representation of the API response.
 */
abstract class ApisExecutor<S>(
    private val context: Context,
    private val baseUrl: String,
    private val apiServiceClass: Class<S>
) {

    /**
     * The API service interface that is used to initialise the retrofit apis.
     */
    protected val apiService: S by lazy { initApiService() }

    private val apiResponseHandler = ApiResponseHandlerProvider(context).apiResponseHandler

    /**
     * General method for executing any API response, along with API error callback to be used after
     * handling the API response.
     *
     * @param call the response to be executed
     * @param [T] the response type
     * @param [R] API callback response type
     */
    protected fun <T : ApiResponse<R>, R> enqueueCall(
        call: Call<T>
    ): Single<Response<R, ApiError>> = Single.just(1)
        .subscribeOn(Schedulers.io())
        .map { callToSingle(call) }

    private fun <T : ApiResponse<R>, R> callToSingle(call: Call<T>): Response<R, ApiError> {
        val apiPath = call.request().url.encodedPath
        return try {
            val response = call.execute()
            apiResponseHandler.handleResponse(response)
        } catch (e: Exception) {
            return when (// Non-legacy errors are considered as HttpException.
                e) {
                is HttpException -> {
                    val result = runCatching {
                        // We the need the error body of the non-legacy error to parse later in ApiResponseHandler.
                        e.response()?.errorBody()?.string().orEmpty()
                    }
                    return apiResponseHandler.handleFailureResponse(result.getOrNull())
                }

                // Return a generic error response when unknown error is encountered.
                else -> Response.error(ApiError(-1, e.message, null))
            }
        }
    }

    private fun initApiService(): S {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val timeOut = 60L

        val okHttpClientBuilder = OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)

        val gson = GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE)
            .setExclusionStrategies(object : ExclusionStrategy {

                override fun shouldSkipField(f: FieldAttributes): Boolean {
                    return f.declaredType == Void.TYPE
                }

                override fun shouldSkipClass(clazz: Class<*>): Boolean {
                    return clazz.name.equals(Void::class.java.name, ignoreCase = true)
                }
            }).create()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(apiServiceClass)
    }
}