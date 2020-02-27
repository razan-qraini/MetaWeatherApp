package com.razanqraini.metaweatherapp.di.net

import com.razanqraini.metaweatherapp.di.net.response.ApiError
import com.razanqraini.metaweatherapp.di.net.response.ApiErrorParser
import com.razanqraini.metaweatherapp.di.net.response.ApiResponse
import com.razanqraini.metaweatherapp.di.net.response.Response
import okhttp3.ResponseBody
import retrofit2.Response as RetrofitResponse

class ApiResponseHandler {

    /**
     * Handles the API response success and possible error cases
     *
     * @param response the api response
     */
    fun <T : ApiResponse<R>, R> handleResponse(
        response: RetrofitResponse<T>
    ): Response<R, ApiError> {
        return processApiResponse(response)
    }


    /**
     * Handles possible network failure exceptions such as [java.net.SocketTimeoutException], [IllegalStateException]
     *
     * @param apiResponseBody JSON representation of the API response.
     */

    fun <R> handleFailureResponse(apiResponseBody: String?): Response<R, ApiError> {
        return processErrorResponse(apiResponseBody)
    }

    private fun <T : ApiResponse<R>, R> processApiResponse(
        response: RetrofitResponse<T>
    ): Response<R, ApiError> {
        val responseBody = response.body()
        val errorBody = response.errorBody()

        return when {
            response.isSuccessful -> Response.success(responseBody?.data)
            errorBody != null -> processErrorResponse(errorBody)
            else -> Response.error(null)
        }
    }

    private fun <R> processErrorResponse(
        errorBody: ResponseBody
    ): Response<R, ApiError> {
        return try {
            processErrorResponse(errorBody.string())
        } catch (e: Exception) {
            Response.error(null)
        }
    }

    private fun <R> processErrorResponse(errorBody: String?): Response<R, ApiError> {
        return try {
            val apiError = ApiErrorParser.parseError(errorBody)
            Response.error(apiError)
        } catch (e: Exception) {
            Response.error(null)
        }
    }
}
