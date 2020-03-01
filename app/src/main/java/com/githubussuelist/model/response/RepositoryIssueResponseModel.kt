package com.githubussuelist.model.response

import com.google.gson.annotations.SerializedName

data class RepositoryIssueResponseModel(
    @SerializedName("id")
    val id: Int
)