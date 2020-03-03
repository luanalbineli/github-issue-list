package com.githubussuelist.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = IssueLabelEntityModel.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = RepositoryIssueEntityModel::class,
            parentColumns = ["id"],
            childColumns = ["issue_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IssueLabelEntityModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "color")
    val color: String,

    @ColumnInfo(name = "issue_id", index = true)
    val issueId: Int
) {
    companion object {
        const val TABLE_NAME = "repository_label"
    }
}