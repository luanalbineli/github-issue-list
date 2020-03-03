package com.githubussuelist.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubussuelist.R
import com.githubussuelist.databinding.ActivityMainBinding
import com.githubussuelist.extension.injector
import com.githubussuelist.extension.safeNullObserve
import com.githubussuelist.extension.viewModelProvider
import com.githubussuelist.model.common.Status
import com.githubussuelist.ui.main.list.IssueListAdapter
import com.githubussuelist.ui.repository.OnRepositoryDetailDialogResult
import com.githubussuelist.ui.repository.RepositoryDetailDialog
import com.githubussuelist.ui.repository.RepositoryDetailResult
import kotlinx.android.synthetic.main.content_scrolling.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), OnRepositoryDetailDialogResult {
    private val mViewModel by lazy {
        viewModelProvider(
            MainViewModel::class.java,
            injector.mainViewModelFactory()
        )
    }

    private val mIssueListAdapter by lazy {
        IssueListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).also {
            it.mainViewModel = mViewModel
            it.lifecycleOwner = this

            setSupportActionBar(it.toolbar)

            list_issue.adapter = mIssueListAdapter
            list_issue.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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

        mViewModel.closeScreen.safeNullObserve(this) {
            finish()
        }
    }

    override fun onResult(repositoryDetailResult: RepositoryDetailResult) {
        Timber.d("onResult - $repositoryDetailResult")
        mViewModel.handleRepositoryDetailResult(repositoryDetailResult)
    }
}
