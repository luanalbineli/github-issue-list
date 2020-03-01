package com.githubussuelist.model.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = RepositoryEntityModel.TABLE_NAME)
data class RepositoryEntityModel constructor(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "name")
    val name: String
): Parcelable {
    companion object {
        const val TABLE_NAME = "repository"
    }
}