package com.githubussuelist.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.githubussuelist.databinding.ItemIssueBinding
import com.githubussuelist.model.room.RepositoryIssueEntityModel
import com.githubussuelist.widgets.recyclerView.CustomRecyclerViewAdapter

class IssueListAdapter constructor(recyclerView: RecyclerView) : CustomRecyclerViewAdapter<RepositoryIssueEntityModel, IssueViewHolder>(recyclerView, RepositoryIssueDiff) {
    override fun onCreateItemViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): IssueViewHolder {
        val binding = ItemIssueBinding.inflate(layoutInflater, parent, false)
        return IssueViewHolder(binding)
    }

    override fun onBindItemViewHolder(holder: IssueViewHolder, position: Int) {
        holder.binding.repositoryIssueEntityModel = getItem(position)
        holder.binding.executePendingBindings()
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