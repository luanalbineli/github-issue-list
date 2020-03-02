package com.githubussuelist.ui.common

import android.view.View
import androidx.databinding.BindingAdapter
import com.githubussuelist.extension.setDisplay

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.setDisplay(visible)
}