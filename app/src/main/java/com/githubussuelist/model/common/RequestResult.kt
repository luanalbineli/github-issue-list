package com.githubussuelist.model.common

sealed class RequestResult<T> {
    class Loading<T>: RequestResult<T>()
    // object Loading: RequestResult<T>()
    data class Success<T> constructor(
        val data: T
    ): RequestResult<T>()

    data class Error<T> constructor(
        val exception: Exception
    ): RequestResult<T>()
}