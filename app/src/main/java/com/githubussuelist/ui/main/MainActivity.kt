package com.githubussuelist.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubussuelist.R
import com.githubussuelist.databinding.ActivityMainBinding
import com.githubussuelist.extension.*
import com.githubussuelist.model.common.Status
import com.githubussuelist.ui.main.list.IssueListAdapter
import com.githubussuelist.ui.repository.OnRepositoryDetailDialogResult
import com.githubussuelist.ui.repository.RepositoryDetailDialog
import com.githubussuelist.ui.repository.RepositoryDetailResult
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), OnRepositoryDetailDialogResult {
    private val mViewModel by lazy {
        viewModelProvider(
            MainViewModel::class.java,
            injector.mainViewModelFactory()
        )
    }

    private val mLinearLayoutManager by lazy {
        LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private val mIssueListAdapter by lazy {
        IssueListAdapter().also {
            it.errorMessageResId = R.string.error_issue_list_fetch
            it.emptyMessageResId = R.string.text_issue_list_empty
            it.onTryAgain = {
                mViewModel.fetchMoreIssues()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).also {
            it.lifecycleOwner = this
            it.mainViewModel = mViewModel

            list_issue.adapter = mIssueListAdapter
            list_issue.layoutManager = mLinearLayoutManager
        }

        bindViewModel()
    }

    private fun bindViewModel() {
        mViewModel.openRepositoryDialog.observe(this, Observer {
            val repositoryDialogFragment = RepositoryDetailDialog.getInstance(it)
            repositoryDialogFragment.show(supportFragmentManager, RepositoryDetailDialog.TAG)
        })

        mViewModel.issueList.safeNullObserve(this) {
            mIssueListAdapter.submitList(it)
        }

        mViewModel.networkIssueList.safeNullObserve(this) {
            when (it.status) {
                Status.LOADING -> mIssueListAdapter.showLoadingStatus()
                Status.ERROR -> {
                    mIssueListAdapter.showErrorStatus(it.exception)
                    Timber.e(it.exception, "An error occurred while tried to get the issues")
                }
            }
        }

        mViewModel.issueSyncDetail.safeNullObserve(this) {
            Timber.d("issueSyncDetail - Has more issues: ${it.hasMoreIssues}")
            if (it.hasMoreIssues) {
                list_issue.enableLoadMoreItems(mLinearLayoutManager) {
                    mViewModel.fetchMoreIssues()
                }
            } else {
                list_issue.disableLoadMoreItems()
            }
        }

        mViewModel.closeScreen.safeNullObserve(this) {
            finish()
        }
    }

    override fun onResult(repositoryDetailResult: RepositoryDetailResult) {
        Timber.d("onResult - $repositoryDetailResult")
        mViewModel.handleRepositoryDetailResult(repositoryDetailResult)
    }
}
