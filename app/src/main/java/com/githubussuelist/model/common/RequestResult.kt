package com.githubussuelist.model.common

sealed class RequestResult<T> constructor(val data: T? = null) {

    class Loading<T>: RequestResult<T>()
    // object Loading: RequestResult<T>()
    class Success<T> constructor(
        data: T
    ): RequestResult<T>(data)

    data class Error<T> constructor(
        val exception: Exception
    ): RequestResult<T>()
}