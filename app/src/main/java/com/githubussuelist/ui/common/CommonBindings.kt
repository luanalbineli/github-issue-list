package com.githubussuelist.ui.common

import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.githubussuelist.extension.format
import com.githubussuelist.extension.setDisplay
import java.util.*

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.setDisplay(visible)
}

@BindingAdapter("timeElapsed")
fun timeElapsed(textView: TextView, date: Date) {
    val now = System.currentTimeMillis()
    textView.text = DateUtils.getRelativeTimeSpanString(date.time, now, DateUtils.MINUTE_IN_MILLIS)
}

@BindingAdapter("date", "dateFormat")
fun dateAdapter(textView: TextView, date: Date?, dateFormat: String) {
    textView.text = date.format(dateFormat)
}
