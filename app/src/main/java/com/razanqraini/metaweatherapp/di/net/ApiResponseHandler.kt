package com.razanqraini.metaweatherapp.di.net

import com.razanqraini.metaweatherapp.di.net.model.ApiError
import com.razanqraini.metaweatherapp.di.net.model.ApiErrorParser
import com.razanqraini.metaweatherapp.di.net.model.ApiResponse
import com.razanqraini.metaweatherapp.di.net.model.Response
import okhttp3.ResponseBody
import java.net.HttpURLConnection
import javax.inject.Inject
import retrofit2.Response as RetrofitResponse

class ApiResponseHandler
@Inject constructor(private val onUnAuthorizedUser: () -> Unit) {

    /**
     * Handles the API response success and possible error cases
     *
     * @param response the api response
     */
    fun <T : ApiResponse<R>, R> handleResponse(
        response: RetrofitResponse<T>
    ): Response<R, ApiError> {
        return processStandardApiResponse(response)
    }


    /**
     * Handles possible network failure exceptions such as [java.net.SocketTimeoutException], [IllegalStateException]
     *
     * @param apiResponseBody JSON representation of the API response.
     */

    fun <R> handleFailureResponse(apiResponseBody: String?): Response<R, ApiError> {
        return processErrorResponse(apiResponseBody)
    }

    /**
     * All legacy api responses are considered to be successful at this point,
     * if there was a failure in the api response it would have been intercepted by the legacy apis error interceptor.
     *
     *
     * A successful Api response can be NULL if the api definition in the service interface was defined with Void as the response type.
     */
    private fun <T : ApiResponse<R>, R> processLegacyApiResponse(
        response: RetrofitResponse<T>
    ): Response<R, ApiError> {
        val legacyApiResponse = response.body()
        return Response.success(legacyApiResponse?.data)
    }

    private fun <T : ApiResponse<R>, R> processStandardApiResponse(
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
