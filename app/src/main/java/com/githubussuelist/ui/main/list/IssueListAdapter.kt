package com.githubussuelist.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.githubussuelist.model.room.RepositoryIssueEntityModel
import com.githubussuelist.widgets.recyclerView.CustomRecyclerViewAdapter

class IssueListAdapter : CustomRecyclerViewAdapter<RepositoryIssueEntityModel, IssueViewHolder>(RepositoryIssueDiff) {
    override fun onCreateItemViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): IssueViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindItemViewHolder(holder: IssueViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        @JvmField
        val RepositoryIssueDiff = object : DiffUtil.ItemCallback<RepositoryIssueEntityModel>() {
            override fun areItemsTheSame(
                oldItem: RepositoryIssueEntityModel,
                newItem: RepositoryIssueEntityModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RepositoryIssueEntityModel,
                newItem: RepositoryIssueEntityModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}