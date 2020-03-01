package com.githubussuelist.repository.room

import androidx.lifecycle.MutableLiveData
import com.githubussuelist.model.common.RequestResult
import com.githubussuelist.model.room.RepositoryEntityModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RoomRepository @Inject constructor(private val gitHubIssueDB: GitHubIssueDB) {
    fun getProfile(): MutableLiveData<RequestResult<RepositoryEntityModel?>> {
        val result = MutableLiveData<RequestResult<RepositoryEntityModel?>>(RequestResult.Loading())
        CoroutineScope(Dispatchers.Main).launch {
            try {
                withContext(Dispatchers.Default) {
                    val repositoryEntityModel = gitHubIssueDB.repositoryDAO().getOrNull()
                    result.postValue(RequestResult.Success(repositoryEntityModel))
                }
            } catch (exception: Exception) {
                Timber.e(exception, "An error occurred while tried to get the repository")
                result.value = RequestResult.Error(exception)
            }
        }
        return result
    }
}