package com.githubussuelist.repository.room

import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.githubussuelist.model.common.Result
import com.githubussuelist.model.room.RepositoryEntityModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RoomRepository @Inject constructor(private val gitHubIssueDB: GitHubIssueDB) {
    fun getRepository(): MutableLiveData<Result<RepositoryEntityModel>> {
        val result = MutableLiveData<Result<RepositoryEntityModel>>(Result.loading())
        CoroutineScope(Dispatchers.Main).launch {
            try {
                withContext(Dispatchers.Default) {
                    val repositoryEntityModel = gitHubIssueDB.repositoryDAO().getOrNull()
                    result.postValue(Result.success(repositoryEntityModel))
                }
            } catch (exception: Exception) {
                Timber.e(exception, "An error occurred while tried to get the repository")
                result.value = Result.error(exception)
            }
        }
        return result
    }

    suspend fun saveRepository(repositoryEntityModel: RepositoryEntityModel) {
        gitHubIssueDB.withTransaction {
            // Remove the previous repository
            gitHubIssueDB.repositoryDAO().deleteAll()
            // Insert a new one
            gitHubIssueDB.repositoryDAO().insert(repositoryEntityModel)
        }
    }
}