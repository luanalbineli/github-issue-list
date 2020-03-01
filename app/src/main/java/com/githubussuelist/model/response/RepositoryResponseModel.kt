package com.githubussuelist.model.response

import com.githubussuelist.model.room.RepositoryEntityModel
import com.google.gson.annotations.SerializedName

data class RepositoryResponseModel constructor(
    @SerializedName("id")
    val id: Int,

    @SerializedName("open_issues_count")
    val openIssueCount: Int,

    @SerializedName("full_name")
    val name: String
) {
    fun toEntity(): RepositoryEntityModel = RepositoryEntityModel(id = id, name = name)
}