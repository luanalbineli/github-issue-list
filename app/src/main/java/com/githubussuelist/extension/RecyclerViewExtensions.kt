package com.githubussuelist.extension

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val DEFAULT_VISIBLE_THRESHOLD = 2

fun RecyclerView.enableLoadMoreItems(layoutManager: LinearLayoutManager, block: () -> Unit) {
    clearOnScrollListeners()

    var mLoading = false

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                return
            }

            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            if (!mLoading && totalItemCount <= (lastVisibleItem + DEFAULT_VISIBLE_THRESHOLD)) {
                mLoading = true
                post { block.invoke() }
            }
        }
    })
}

fun RecyclerView.disableLoadMoreItems() {
    clearOnScrollListeners()
}