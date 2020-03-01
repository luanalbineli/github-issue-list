package com.githubussuelist.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.githubussuelist.model.common.RequestResult

object CommonBindings {
    fun <R> ifSuccess(requestResult: LiveData<RequestResult<R>>): R? {
        val value = requestResult.value
        if (value is RequestResult.Success) {
            return value.data
        }
        return null
    }
}