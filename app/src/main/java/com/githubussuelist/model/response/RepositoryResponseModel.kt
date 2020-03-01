package com.githubussuelist.model.response

import com.google.gson.annotations.SerializedName

data class RepositoryResponseModel constructor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("open_issues_count")
    val openIssueCount: Int
)