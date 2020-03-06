package com.githubussuelist.ui.main

import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.githubussuelist.model.common.Result
import com.githubussuelist.model.common.Status
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.model.room.RepositoryIssueEntityModel
import com.githubussuelist.model.sync.IssueSyncDetailModel
import com.githubussuelist.repository.github.GitHubRepository
import com.githubussuelist.repository.room.RoomRepository
import com.githubussuelist.repository.sharedPreferences.SharedPreferencesRepository
import com.githubussuelist.ui.repository.RepositoryDetailResult
import com.githubussuelist.util.Constants
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val mRepositoryEntityModel = MediatorLiveData<Result<RepositoryEntityModel>>()
    val repositoryEntityModel: LiveData<Result<RepositoryEntityModel>>
        get() = mRepositoryEntityModel

    private val mIssueList = MediatorLiveData<PagedList<RepositoryIssueEntityModel>>()
    val issueList: LiveData<PagedList<RepositoryIssueEntityModel>>
        get() = mIssueList

    private val mNetworkIssueList = MediatorLiveData<Result<List<RepositoryIssueEntityModel>>>()
    val networkIssueList: LiveData<Result<List<RepositoryIssueEntityModel>>>
        get() = mNetworkIssueList

    private val mIssueSyncDetail = MutableLiveData<IssueSyncDetailModel>(sharedPreferencesRepository.getIssueSyncDetailModel())
    val issueSyncDetail: LiveData<IssueSyncDetailModel>
        get() = mIssueSyncDetail

    private val mOpenRepositoryDialog = MutableLiveData<RepositoryEntityModel>()
    val openRepositoryDialog: LiveData<RepositoryEntityModel?>
        get() = mOpenRepositoryDialog

    private val mCloseScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = mCloseScreen

    init {
        mRepositoryEntityModel.addSource(roomRepository.getRepository()) {
            mRepositoryEntityModel.value = it

            if (it.status == Status.SUCCESS) {
                if (it.data == null) {
                    mOpenRepositoryDialog.value = null
                } else {
                    fetchRepositoryIssues(it.data, isFirstAttempt = true)
                }
            }
        }
    }


    fun openRepositoryDialog() {
        mOpenRepositoryDialog.value = repositoryEntityModel.value?.data
    }

    fun handleRepositoryDetailResult(repositoryDetailResult: RepositoryDetailResult) {
        if (repositoryDetailResult is RepositoryDetailResult.Updated) {
            val isFirstAttempt = mRepositoryEntityModel.value == null
            mRepositoryEntityModel.value =
                Result.success(repositoryDetailResult.repositoryEntityModel)

            val hasMoreIssues = if (repositoryDetailResult.repositoryEntityModel.openIssueCount > 0) {
                fetchRepositoryIssues(repositoryDetailResult.repositoryEntityModel, isFirstAttempt = isFirstAttempt)
                true
            } else {
                false
            }

            val issueSyncDetailModel = IssueSyncDetailModel(
                lastSyncDate = Date(),
                hasMoreIssues = hasMoreIssues
            )

            updateIssueSyncDetail(issueSyncDetailModel)
        } else {
            if (mRepositoryEntityModel.value?.data == null) {
                mCloseScreen.value = Unit
            }
        }
    }

    private fun fetchRepositoryIssues(
        repositoryEntityModel: RepositoryEntityModel,
        isFirstAttempt: Boolean,
        pageIndex: Int = Constants.API_INITIAL_PAGE_INDEX
    ) {
        mNetworkIssueList.addSource(
            gitHubRepository.fetchAndSaveIssueList(
                viewModelScope,
                repositoryEntityModel.id,
                repositoryEntityModel.orgName,
                repositoryEntityModel.name,
                pageIndex,
                clearPreviousList = isFirstAttempt
            )
        ) {
            mNetworkIssueList.value = it

            if (it.status != Status.LOADING) {
                if (isFirstAttempt) {
                    linkIssueListToDataSource()
                }

                if (it.data != null) {
                    val issueSyncDetailModel = IssueSyncDetailModel(
                        lastPageIndex = pageIndex,
                        lastSyncDate = Date(),
                        hasMoreIssues = it.data.size == Constants.API_ISSUE_PAGE_SIZE
                    )
                    updateIssueSyncDetail(issueSyncDetailModel)
                }
            }
        }
    }

    private fun linkIssueListToDataSource() {
        mIssueList.addSource(roomRepository.getIssueDataSourceFactory().toLiveData(pageSize = Constants.API_ISSUE_PAGE_SIZE)) {
            mIssueList.value = it
        }
    }

    private fun updateIssueSyncDetail(issueSyncDetailModel: IssueSyncDetailModel) {
        sharedPreferencesRepository.saveIssueSyncDetailModel(issueSyncDetailModel)
        mIssueSyncDetail.value = issueSyncDetailModel
    }

    fun fetchMoreIssues() {
        mIssueSyncDetail.value?.let { issueSyncDetailModel ->
            mRepositoryEntityModel.value?.data?.let { repositoryEntityModel ->
                fetchRepositoryIssues(repositoryEntityModel, isFirstAttempt = false, pageIndex = issueSyncDetailModel.lastPageIndex + 1)
            }

        }
    }
}