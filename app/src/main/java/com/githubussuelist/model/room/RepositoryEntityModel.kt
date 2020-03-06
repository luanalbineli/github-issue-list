package com.githubussuelist.model.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = RepositoryEntityModel.TABLE_NAME)
data class RepositoryEntityModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "open_issue_count")
    val openIssueCount: Int,

    @ColumnInfo(name = "star_count")
    val starCount: Int,

    @ColumnInfo(name = "fork_count")
    val forkCount: Int,

    @ColumnInfo(name = "subscriber_count")
    val subscriberCount: Int
) : Parcelable {
    @IgnoredOnParcel
    @Ignore
    val orgName: String = fullName.split('/')[0]

    companion object {
        const val TABLE_NAME = "repository"
    }
}