package com.omersungur.domain.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(val errorMessage: String?) : Resource<T>()
}
