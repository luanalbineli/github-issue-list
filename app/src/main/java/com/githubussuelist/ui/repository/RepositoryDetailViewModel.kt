package com.githubussuelist.ui.repository

import androidx.lifecycle.*
import com.githubussuelist.model.common.Result
import com.githubussuelist.model.common.Status
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.repository.github.GitHubRepository
import javax.inject.Inject

class RepositoryDetailViewModel @Inject constructor(private val githubRepository: GitHubRepository) : ViewModel() {
    val repositoryName = MutableLiveData("")
    private var mInitialRepositoryId = Int.MIN_VALUE

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
            mInitialRepositoryId = repositoryEntityModel.id
        }
    }

    fun saveRepositoryDetail() {
        repositoryName.value?.let { repositoryName ->
            val repositoryNameParts = repositoryName.split('/')
            if (repositoryNameParts.size != 2) {
                mRepositoryNameValidation.value = Unit
                return@let
            }

            mRepositoryDetail.addSource(githubRepository.fetchAndSaveRepository(viewModelScope, repositoryNameParts[0], repositoryNameParts[1])) {
                mRepositoryDetail.value = it

                if (it.status == Status.SUCCESS) {
                    it.data?.let { result ->
                        val repositoryDetailResult = if (result.id == mInitialRepositoryId) {
                            RepositoryDetailResult.Dismiss
                        } else {
                            RepositoryDetailResult.Updated(result)
                        }
                        mDismiss.value = repositoryDetailResult
                    }
                }
            }
        }
    }

    fun closeDialog() {
        mDismiss.value = RepositoryDetailResult.Dismiss
    }
}