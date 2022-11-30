package com.example.starwarsapp.utils

sealed class Response<T>(
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        const val NO_DATA_AVAILABLE = "No data"
        const val LOG_ERROR_TAG = "LOG_error_tag"
    }
    class Success<T>(data: T) : Response<T>(data)
    class Error<T>(message: String, data: T? = null) : Response<T>(data, message)
}
