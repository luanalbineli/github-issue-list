package com.githubussuelist.ui.repository

import androidx.lifecycle.*
import com.githubussuelist.model.common.Result
import com.githubussuelist.model.common.Status
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.repository.github.GitHubRepository
import javax.inject.Inject

class RepositoryDetailViewModel @Inject constructor(private val githubRepository: GitHubRepository) : ViewModel() {
    val repositoryName = MutableLiveData("")
    private var mInitialRepositoryName = ""

    private val mRepositoryDetail = MediatorLiveData<Result<RepositoryEntityModel>>()
    val repositoryDetail: LiveData<Result<RepositoryEntityModel>>
        get() = mRepositoryDetail

    private val mRepositoryNameValidation = MutableLiveData<Unit>()
    val repositoryNameValidation: LiveData<Unit>
        get() = mRepositoryNameValidation

    private val mDismiss = MutableLiveData<RepositoryDetailResult>()
    val dismiss: LiveData<RepositoryDetailResult>
        get() = mDismiss

    fun init(repositoryEntityModel: RepositoryEntityModel?) {
        if (repositoryEntityModel != null) {
            repositoryName.value = repositoryEntityModel.fullName
            mInitialRepositoryName = repositoryEntityModel.fullName
        }
    }

    fun saveRepositoryDetail() {
        repositoryName.value?.let { repositoryName ->
            if (repositoryName == mInitialRepositoryName) {
                mDismiss.value = RepositoryDetailResult.Dismiss
                return@let
            }

            val repositoryNameParts = repositoryName.split('/')
            if (repositoryNameParts.size != 2) {
                mRepositoryNameValidation.value = Unit
                return@let
            }

            mRepositoryDetail.addSource(githubRepository.fetchAndSaveRepository(viewModelScope, repositoryNameParts[0], repositoryNameParts[1])) {
                mRepositoryDetail.value = it

                if (it.status == Status.SUCCESS) {
                    it.data?.let { result ->
                        mDismiss.value = RepositoryDetailResult.Updated(result)
                    }
                }
            }
        }
    }

    fun closeDialog() {
        mDismiss.value = RepositoryDetailResult.Dismiss
    }
}