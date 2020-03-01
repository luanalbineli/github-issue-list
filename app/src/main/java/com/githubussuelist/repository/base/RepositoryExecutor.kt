package com.githubussuelist.repository.base

import androidx.lifecycle.MutableLiveData
import com.githubussuelist.model.common.RequestResult
import kotlinx.coroutines.*
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryExecutor @Inject constructor(
    private val idlingResourceCounter: IdlingResourceCounter,
    private val retrofit: Retrofit
) {
    fun <R, T> makeRequest(clazz: Class<T>,
                           viewModelScope: CoroutineScope,
                           asyncRequest: suspend CoroutineScope.(serviceInstance: T) -> R): MutableLiveData<RequestResult<R?>> {
        val result = MutableLiveData<RequestResult<R?>>()
        result.value = RequestResult.Loading()

        // TODO: Handle internet connection
        /*val isConnected = networkStatus.haveActiveConnection()
        Timber.d("Is connected: $isConnected")
        if (!isConnected) {
            result.value = Result.error(RequestErrorModel(NoInternetConnectionException()))
            return result
        }*/

        idlingResourceCounter.increment()
        viewModelScope.launch {
            try {
                var asyncRequestResult: R? = null
                withContext(Dispatchers.Default) {
                    val apiServiceInstance = retrofit.create(clazz)
                    asyncRequestResult = asyncRequest(apiServiceInstance)
                }
                result.value = RequestResult.Success(asyncRequestResult)
            } catch (cancellationException: CancellationException) {
            } catch (exception: Exception) {
                result.value = RequestResult.Error(exception)
            } finally {
                idlingResourceCounter.decrement()
            }
        }

        return result
    }
}