package com.githubussuelist.repository.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.room.withTransaction
import com.githubussuelist.model.common.Result
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.model.room.RepositoryIssueEntityModel
import com.githubussuelist.repository.base.IdlingResourceCounter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RoomRepository @Inject constructor(private val gitHubIssueDB: GitHubIssueDB, private val idlingResourceCounter: IdlingResourceCounter) {
    fun getRepository(): LiveData<Result<RepositoryEntityModel>> {
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
        Timber.d("saveRepository - 1: $idlingResourceCounter")
        idlingResourceCounter.increment()
        gitHubIssueDB.withTransaction {
            Timber.d("saveRepository - 2")
            // Remove the previous repository
            gitHubIssueDB.repositoryDAO().deleteAll()
            Timber.d("saveRepository - 3")
            // Insert a new one
            gitHubIssueDB.repositoryDAO().insert(repositoryEntityModel)
            Timber.d("saveRepository - 4")
            idlingResourceCounter.decrement()
        }
    }

    suspend fun saveIssueList(issueList: List<RepositoryIssueEntityModel>) {
        gitHubIssueDB.withTransaction {
            gitHubIssueDB.repositoryIssueDAO().insertAll(*issueList.toTypedArray())
        }
    }

    fun getRepositoryIssueList(): DataSource.Factory<Int, RepositoryIssueEntityModel> =
        gitHubIssueDB.repositoryIssueDAO().getAll()
}