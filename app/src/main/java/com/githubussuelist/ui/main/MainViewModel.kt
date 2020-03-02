package com.githubussuelist.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubussuelist.model.common.Result
import com.githubussuelist.model.common.Status
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.repository.github.GitHubRepository
import com.githubussuelist.repository.room.RoomRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(roomRepository: RoomRepository, gitHubRepository: GitHubRepository) : ViewModel() {
    private val mRepositoryEntityModel = MediatorLiveData<Result<RepositoryEntityModel>>()
    val repositoryEntityModel :LiveData<Result<RepositoryEntityModel>>
        get() = mRepositoryEntityModel

    private val mOpenRepositoryDialog = MutableLiveData<RepositoryEntityModel>()
    val openRepositoryDialog: LiveData<RepositoryEntityModel?>
        get() = mOpenRepositoryDialog

    init {
        mRepositoryEntityModel.addSource(roomRepository.getRepository()) {
            if (it.status == Status.SUCCESS && it.data == null) {
                mOpenRepositoryDialog.value = null
            }

            mRepositoryEntityModel.value = it
        }
    }

    fun openRepositoryDialog() {
        mOpenRepositoryDialog.value = repositoryEntityModel.value?.data
    }
}