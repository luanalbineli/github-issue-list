package com.githubussuelist.repository.base

import kotlinx.coroutines.CoroutineScope

abstract class BaseRepository<T>(private val repositoryExecutor: RepositoryExecutor) {
    abstract val serviceInstanceType: Class<T>

    protected fun <R> makeRequest(viewModelScope: CoroutineScope, call: suspend CoroutineScope.(serviceInstance: T) -> R)
            = repositoryExecutor.makeRequest(serviceInstanceType, viewModelScope, call)
}