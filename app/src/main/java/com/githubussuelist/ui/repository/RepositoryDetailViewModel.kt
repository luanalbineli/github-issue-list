package com.githubussuelist.ui.repository

import androidx.lifecycle.*
import com.githubussuelist.model.common.RequestResult
import com.githubussuelist.model.response.RepositoryResponseModel
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.repository.github.GitHubRepository
import com.githubussuelist.repository.room.RoomRepository
import javax.inject.Inject

class RepositoryDetailViewModel @Inject constructor(private val githubRepository: GitHubRepository, private val roomRepository: RoomRepository) : ViewModel() {
    val repositoryName = MutableLiveData("")

    private val mRepositoryDetail = MediatorLiveData<RequestResult<RepositoryEntityModel?>>()
    val repositoryDetail: LiveData<RequestResult<RepositoryEntityModel?>>
        get() = mRepositoryDetail

    fun saveRepositoryDetail() {
        repositoryName.value?.let { repositoryName ->
            mRepositoryDetail.addSource(githubRepository.fetchAndSaveRepository(viewModelScope, repositoryName)) {
                if (it is RequestResult.Success && it.data != null) {
                    saveRepository(it.data)
                }
            }
        }
    }

    private fun saveRepository(repositoryResponseModel: RepositoryResponseModel) {
        roomRepository.getRepository()
    }
}