package com.githubussuelist.repository.github

import androidx.lifecycle.MutableLiveData
import com.githubussuelist.model.common.RequestResult
import com.githubussuelist.repository.base.BaseRepository
import com.githubussuelist.repository.base.RepositoryExecutor
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    repositoryExecutor: RepositoryExecutor
): BaseRepository<IGitHubService>(repositoryExecutor) {
    override val serviceInstanceType: Class<IGitHubService>
        get() = IGitHubService::class.java

    fun getRepositoryByName(viewModelScope: CoroutineScope, repositoryName: String): MutableLiveData<RequestResult> {
        return makeRequest(viewModelScope) { service ->
            service.getRepositoryByName(repositoryName)
        }
    }


}