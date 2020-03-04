package com.githubussuelist.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date?.format(format: String): String? {
    if (this == null) {
        return null
    }
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return simpleDateFormat.format(this)
}