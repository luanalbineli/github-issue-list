package com.githubussuelist.widgets.recyclerView

import android.content.Context
import android.view.View

open class CustomVH(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    val context: Context = itemView.context
}