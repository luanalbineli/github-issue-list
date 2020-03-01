package com.githubussuelist.repository.github

import androidx.lifecycle.MutableLiveData
import com.githubussuelist.model.common.RequestResult
import com.githubussuelist.model.response.RepositoryResponseModel
import com.githubussuelist.model.room.RepositoryEntityModel
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

    fun getRepositoryByName(viewModelScope: CoroutineScope, repositoryName: String): MutableLiveData<RequestResult<RepositoryResponseModel?>> {
        return makeRequest(viewModelScope) { service ->
            service.getRepositoryByName(repositoryName).await()
        }
    }

    fun fetchAndSaveRepository(
        viewModelScope: CoroutineScope,
        repositoryName: String
    ): MutableLiveData<RequestResult<RepositoryEntityModel?>> {
        return makeRequest(viewModelScope) { service ->
            val repositoryResponseModel = service.getRepositoryByName(repositoryName).await()

            val repositoryEntityModel = repositoryResponseModel.toEntity()


            roomRepository.saveRepository(repositoryEntityModel)

            return@makeRequest repositoryEntityModel
        }
    }


}