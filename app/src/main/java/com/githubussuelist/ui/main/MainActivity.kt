package com.githubussuelist.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.githubussuelist.R
import com.githubussuelist.databinding.ActivityMainBinding
import com.githubussuelist.extension.injector
import com.githubussuelist.extension.viewModelProvider
import com.githubussuelist.ui.repository.RepositoryDetailDialog

class MainActivity : AppCompatActivity() {
    private val mViewModel by lazy { viewModelProvider(MainViewModel::class.java, injector.mainViewModelFactory()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).also {
            it.mainViewModel = mViewModel
            it.lifecycleOwner = this

            setSupportActionBar(it.toolbar)
        }

        bindViewModel()
    }

    private fun bindViewModel() {
        mViewModel.openRepositoryDialog.observe(this, Observer {
            val repositoryDialogFragment = RepositoryDetailDialog.getInstance(it)
            repositoryDialogFragment.show(supportFragmentManager, RepositoryDetailDialog.TAG)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
