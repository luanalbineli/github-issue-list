package com.githubussuelist.model.response

import com.githubussuelist.model.room.RepositoryIssueEntityModel
import com.google.gson.annotations.SerializedName
import java.util.*

data class RepositoryIssueResponseModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("number")
    val number: Int,

    @SerializedName("created_at")
    val createdAt: Date?,

    @SerializedName("user")
    val user: IssueUserModel,

    @SerializedName("labels")
    val labelList: List<IssueLabelModel>
) {
    fun toEntity(repositoryId: Int): RepositoryIssueEntityModel
    = RepositoryIssueEntityModel(
        id = id,
        title = title,
        url = url,
        number = number,
        createdAt = createdAt,
        userName = user.login,
        repositoryId = repositoryId,
        labelMap = labelList.map { it.name to it.color }.toMap()
    )
}

data class IssueUserModel(
    @SerializedName("login")
    val login: String
)

data class IssueLabelModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("color")
    val color: String
)