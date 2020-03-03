package com.githubussuelist.repository.github

import androidx.lifecycle.MutableLiveData
import com.githubussuelist.model.common.Result
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.model.room.RepositoryIssueEntityModel
import com.githubussuelist.repository.base.BaseRepository
import com.githubussuelist.repository.base.RepositoryExecutor
import com.githubussuelist.repository.room.RoomRepository
import kotlinx.coroutines.CoroutineScope
import retrofit2.await
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    repositoryExecutor: RepositoryExecutor,
    private val roomRepository: RoomRepository
): BaseRepository<IGitHubService>(repositoryExecutor) {
    override val serviceInstanceType: Class<IGitHubService>
        get() = IGitHubService::class.java

    fun fetchAndSaveRepository(
        viewModelScope: CoroutineScope,
        repositoryOrgName: String,
        repositoryName: String
    ): MutableLiveData<Result<RepositoryEntityModel>> {
        return makeRequest(viewModelScope) { service ->
            val repositoryResponseModel = service.getRepositoryByName(repositoryOrgName, repositoryName).await()

            val repositoryEntityModel = repositoryResponseModel.toEntity()

            roomRepository.saveRepository(repositoryEntityModel)

            return@makeRequest repositoryEntityModel
        }
    }

    fun fetchAndSaveIssueList(
        viewModelScope: CoroutineScope,
        repositoryId: Int,
        repositoryOrgName: String,
        repositoryName: String,
        pageIndex: Int
    ): MutableLiveData<Result<List<RepositoryIssueEntityModel>>> {
        return makeRequest(viewModelScope) { service ->
            val issueListResponseModel = service.getRepositoryIssuesByRepositoryName(repositoryOrgName, repositoryName, pageIndex).await()
            val issueListEntityModel = issueListResponseModel.map { it.toEntity(repositoryId) }

            roomRepository.saveIssueList(issueListEntityModel)

            return@makeRequest issueListEntityModel
        }
    }
}