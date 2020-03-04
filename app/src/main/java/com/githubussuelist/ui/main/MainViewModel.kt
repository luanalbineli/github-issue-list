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
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    roomRepository: RoomRepository
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

    private val mIssueSyncDetail =
        MutableLiveData<IssueSyncDetailModel>(sharedPreferencesRepository.getIssueSyncDetailModel())
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
                    checkFetchIssueList()
                }
            }
        }

        mIssueList.addSource(roomRepository.getRepositoryIssueList().toLiveData(pageSize = Constants.API_ISSUE_PAGE_SIZE)) {
            Timber.d("ISSUE_LIST: $it")
            mIssueList.value = it

            checkFetchIssueList()
        }
    }

    private fun checkFetchIssueList() {
        val issueList = issueList.value
        val repositoryEntityModel = repositoryEntityModel.value?.data
        Timber.d("checkFetchIssueList - Issue list: $issueList - Repo: $repositoryEntityModel")
        if (issueList != null && issueList.size == 0 && repositoryEntityModel != null) {
            val issueSyncDetailModel = sharedPreferencesRepository.getIssueSyncDetailModel()
            issueSyncDetailModel?.let {
                if (issueSyncDetailModel.hasMoreIssues) {
                    fetchRepositoryIssues(repositoryEntityModel)
                }
            }
        }
    }

    fun openRepositoryDialog() {
        mOpenRepositoryDialog.value = repositoryEntityModel.value?.data
    }

    fun handleRepositoryDetailResult(repositoryDetailResult: RepositoryDetailResult) {
        if (repositoryDetailResult is RepositoryDetailResult.Updated) {
            mRepositoryEntityModel.value =
                Result.success(repositoryDetailResult.repositoryEntityModel)

            val hasMoreIssues = if (repositoryDetailResult.repositoryEntityModel.openIssueCount > 0) {
                fetchRepositoryIssues(repositoryDetailResult.repositoryEntityModel)
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
        pageIndex: Int = Constants.API_INITIAL_PAGE_INDEX
    ) {
        mNetworkIssueList.addSource(
            gitHubRepository.fetchAndSaveIssueList(
                viewModelScope,
                repositoryEntityModel.id,
                repositoryEntityModel.orgName,
                repositoryEntityModel.name,
                pageIndex
            )
        ) {
            mNetworkIssueList.value = it
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

    private fun updateIssueSyncDetail(issueSyncDetailModel: IssueSyncDetailModel) {
        sharedPreferencesRepository.saveIssueSyncDetailModel(issueSyncDetailModel)
        mIssueSyncDetail.value = issueSyncDetailModel
    }

    fun fetchMoreIssues() {
        mIssueSyncDetail.value?.let { issueSyncDetailModel ->
            mRepositoryEntityModel.value?.data?.let { repositoryEntityModel ->
                fetchRepositoryIssues(repositoryEntityModel, issueSyncDetailModel.lastPageIndex + 1)
            }

        }
    }
}