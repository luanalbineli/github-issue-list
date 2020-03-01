package com.githubussuelist.ui.repository

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubussuelist.model.common.RequestResult
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.repository.github.GitHubRepository
import com.githubussuelist.repository.room.RoomRepository
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(roomRepository: RoomRepository, gitHubRepository: GitHubRepository) : ViewModel() {
    val repositoryName = MutableLiveData("")
    private val mRepositoryEntityModel = MediatorLiveData<RequestResult<RepositoryEntityModel?>>()

    init {
        mRepositoryEntityModel.addSource(roomRepository.getProfile()) {
            if (it is RequestResult.Success) {

            }
        }
    }
}