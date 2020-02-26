package com.razanqraini.metaweatherapp.di.net.response

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

object ApiErrorParser {
    private const val KEY_ERRORS = "errors"
    private const val KEY_ERROR = "error"
    private const val KEY_DETAIL = "detail"
    private const val KEY_STATUS = "status"
    private const val KEY_STATUS_CODE = "status_code"
    private const val KEY_DATA = "data"
    private const val KEY_MESSAGE = "message"
    private const val KEY_FIELD = "field"
    private const val PARSE_ERROR_EXCEPTION_MSG = "Cannot parse API error"

    @Throws(JSONException::class)
    fun parseError(errorBody: String?): ApiError {

        if (errorBody.isNullOrEmpty()) {
            throw JSONException(PARSE_ERROR_EXCEPTION_MSG)
        }

        var apiError = ApiError(0, null, null)
        val jsonErrorBody = JSONObject(errorBody)

        if (jsonErrorBody.has(KEY_ERRORS)) {
            val errors = jsonErrorBody.getJSONArray(KEY_ERRORS)

            if (errors != null && errors.length() != 0) {
                val error = errors.getJSONObject(0)

                if (error.has(KEY_DETAIL) && error.has(KEY_STATUS)) {
                    apiError = ApiError(
                        error.getInt(KEY_STATUS),
                        error.getString(KEY_DETAIL),
                        null
                    )
                }
            }
        } else if (jsonErrorBody.has(KEY_DATA)) {
            val data = jsonErrorBody.get(KEY_DATA)
            val statusCode = jsonErrorBody.getInt(KEY_STATUS_CODE)
            when (data) {
                is String -> apiError = ApiError(statusCode, data, null)

                is JSONObject -> {
                    apiError = ApiError(
                        statusCode,
                        if (data.has(KEY_MESSAGE)) data.getString(KEY_MESSAGE) else null,
                        null
                    )
                }
                is JSONArray -> {
                    val fieldErrorList = ArrayList<FieldError>()
                    var fieldError: FieldError
                    for (i in 0 until data.length()) {
                        val error = data.getJSONObject(i)
                        val errorMsg = error.getString(KEY_ERROR)
                        val field = error.getString(KEY_FIELD)
                        fieldError = FieldError(field, errorMsg)
                        fieldErrorList.add(fieldError)
                    }
                    apiError = ApiError(statusCode, null, fieldErrorList)
                }
            }
        }
        return apiError
    }
}

data class ApiError(val statusCode: Int, val message: String?, val fieldErrors: List<FieldError>?)

data class FieldError(val field: String, val message: String)