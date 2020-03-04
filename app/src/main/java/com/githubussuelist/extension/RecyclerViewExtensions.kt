package com.githubussuelist.extension

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

const val DEFAULT_VISIBLE_THRESHOLD = 2

fun RecyclerView.enableLoadMoreItems(layoutManager: LinearLayoutManager, block: () -> Unit) {
    // clearOnScrollListeners()

    var mLoading = false

    var totalItemCountBeforeLoading = 0
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            Timber.d("recyclerView.scrollState: ${recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE}")
            if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                return
            }

            val totalItemCount = layoutManager.itemCount

            if (mLoading && totalItemCount > totalItemCountBeforeLoading) {
                mLoading = false
            }

            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            if (!mLoading && totalItemCount <= (lastVisibleItem + DEFAULT_VISIBLE_THRESHOLD)) {
                mLoading = true
                block.invoke()
                totalItemCountBeforeLoading = totalItemCount
            }
        }
    })
}

fun RecyclerView.disableLoadMoreItems() {
    Timber.d("disableLoadMoreItems")
    clearOnScrollListeners()
}