package com.githubussuelist.widgets.recyclerView

import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_request_status.view.*

class RequestStatusVH constructor(itemView: View): CustomVH(itemView) {
    fun bind(status: RequestStatusView.Status, isListEmpty: Boolean, errorMessageResId: Int?, emptyMessageResId: Int?, onTryAgain: (() -> Unit)? = null) {
        val layoutParams = itemView.view_request_status.layoutParams
        if (isListEmpty) {
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        itemView.view_request_status.onTryAgain = onTryAgain
        itemView.view_request_status.setErrorMessage(errorMessageResId)
        itemView.view_request_status.setEmptyMessage(emptyMessageResId)
        itemView.view_request_status.layoutParams = layoutParams
        itemView.view_request_status.toggleStatus(status)
    }
}