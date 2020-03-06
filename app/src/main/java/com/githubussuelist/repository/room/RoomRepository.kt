package com.githubussuelist.repository.room

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.room.withTransaction
import com.githubussuelist.model.common.Result
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.model.room.RepositoryIssueEntityModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RoomRepository @Inject constructor(private val gitHubIssueDB: GitHubIssueDB) {
    fun getRepository(): LiveData<Result<RepositoryEntityModel>> {
        val result = MutableLiveData<Result<RepositoryEntityModel>>()
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

    suspend fun saveIssueList(issueList: List<RepositoryIssueEntityModel>) {
        gitHubIssueDB.withTransaction {
            gitHubIssueDB.repositoryIssueDAO().insertAll(*issueList.toTypedArray())
        }
    }

    fun getIssueDataSourceFactory(): DataSource.Factory<Int, RepositoryIssueEntityModel> =
        gitHubIssueDB.repositoryIssueDAO().getDataSourceFactory()

    suspend fun removeAllIssues() {
        gitHubIssueDB.withTransaction {
            gitHubIssueDB.repositoryIssueDAO().removeAll()
        }

    }

    @VisibleForTesting
    suspend fun getRepositoryAsync() = gitHubIssueDB.repositoryDAO().getOrNull()

    @VisibleForTesting
    suspend fun getAllIssueAsync() = gitHubIssueDB.repositoryIssueDAO().getAll()
}