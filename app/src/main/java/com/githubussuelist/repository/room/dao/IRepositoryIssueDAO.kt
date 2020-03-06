package com.githubussuelist.repository.room.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.githubussuelist.model.room.RepositoryIssueEntityModel

@Dao
interface IRepositoryIssueDAO {
    @Query("SELECT * FROM ${RepositoryIssueEntityModel.TABLE_NAME} ORDER BY created_at DESC")
    fun getDataSourceFactory(): DataSource.Factory<Int, RepositoryIssueEntityModel>

    @Query("SELECT * FROM ${RepositoryIssueEntityModel.TABLE_NAME} ORDER BY created_at DESC")
    suspend fun getAll(): List<RepositoryIssueEntityModel>

    @Insert()
    suspend fun insertAll(vararg repositoryIssueEntityModel: RepositoryIssueEntityModel)

    @Query("DELETE FROM ${RepositoryIssueEntityModel.TABLE_NAME}")
    suspend fun removeAll()
}