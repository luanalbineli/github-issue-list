package com.githubussuelist.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RepositoryEntityModel.TABLE_NAME)
data class RepositoryEntityModel constructor(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String
) {
    companion object {
        const val TABLE_NAME = "repository"
    }
}