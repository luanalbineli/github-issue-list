package com.githubussuelist.repository.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.githubussuelist.model.room.RepositoryEntityModel

@Dao
interface RepositoryDAO {
    @Transaction
    @Query("SELECT * FROM ${RepositoryEntityModel.TABLE_NAME} LIMIT 1")
    suspend fun getOrNull(): RepositoryEntityModel?
}