package com.githubussuelist.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    
    @ColumnInfo(name = "repository_id")
    val repositoryId: Int
) {
    companion object {
        const val TABLE_NAME = "repository_issue"
    }
}