package com.githubussuelist.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = RepositoryIssueEntityModel.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = RepositoryEntityModel::class,
            parentColumns = ["id"],
            childColumns = ["repository_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class RepositoryIssueEntityModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "repository_id", index = true)
    val repositoryId: Int,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "number")
    val number: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Date?,

    @ColumnInfo(name = "userName")
    val userName: String,

    @ColumnInfo(name = "label_map")
    val labelMap: Map<String, String>
) {
    companion object {
        const val TABLE_NAME = "repository_issue"
    }
}