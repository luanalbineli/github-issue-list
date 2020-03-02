package com.githubussuelist.model.response

import com.githubussuelist.model.room.RepositoryEntityModel
import com.google.gson.annotations.SerializedName

data class RepositoryResponseModel constructor(
    @SerializedName("id")
    val id: Int,

    @SerializedName("open_issues_count")
    val openIssueCount: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("fork_count")
    val forkCount: Int,

    @SerializedName("subscribers_count")
    val subscriberCount: Int,

    @SerializedName("stargazers_count")
    val starCount: Int
) {
    fun toEntity(): RepositoryEntityModel = RepositoryEntityModel(
        id = id,
        name = name,
        description = description,
        forkCount = forkCount,
        subscriberCount = subscriberCount,
        fullName = fullName,
        openIssueCount = openIssueCount,
        starCount = starCount
    )
}