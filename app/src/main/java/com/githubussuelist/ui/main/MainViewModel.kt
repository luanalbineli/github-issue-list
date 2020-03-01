package com.githubussuelist.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubussuelist.model.common.RequestResult
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.repository.github.GitHubRepository
import com.githubussuelist.repository.room.RoomRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(roomRepository: RoomRepository, gitHubRepository: GitHubRepository) : ViewModel() {
    val repositoryEntityModel = MediatorLiveData<RequestResult<RepositoryEntityModel?>>()

    private val mOpenRepositoryDialog = MutableLiveData<RepositoryEntityModel?>()
    val openRepositoryDialog: LiveData<RepositoryEntityModel?>
        get() = mOpenRepositoryDialog

    init {
        repositoryEntityModel.addSource(roomRepository.getRepository()) {
            if (it is RequestResult.Success) {
                if (it.data == null) {
                    mOpenRepositoryDialog.value = null
                }
            }
        }
    }

    fun openRepositoryDialog() {
        val repositoryEntityModelData = repositoryEntityModel.value
        mOpenRepositoryDialog.value = if (repositoryEntityModelData is RequestResult.Success) {
            repositoryEntityModelData.data
        } else
            null
    }
}