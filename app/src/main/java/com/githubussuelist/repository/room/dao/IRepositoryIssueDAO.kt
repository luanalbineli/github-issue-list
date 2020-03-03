package com.githubussuelist.repository.room.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.githubussuelist.model.room.RepositoryIssueEntityModel

@Dao
interface IRepositoryIssueDAO {
    @Query("SELECT * FROM ${RepositoryIssueEntityModel.TABLE_NAME}")
    fun getAll(): DataSource.Factory<Int, RepositoryIssueEntityModel>

    @Insert()
    fun insertAll(vararg repositoryIssueEntityModel: RepositoryIssueEntityModel)
}