package com.githubussuelist.repository.room.dao

import androidx.room.*
import com.githubussuelist.model.room.RepositoryEntityModel

@Dao
interface IRepositoryDAO {
    @Transaction
    @Query("SELECT * FROM ${RepositoryEntityModel.TABLE_NAME} LIMIT 1")
    suspend fun getOrNull(): RepositoryEntityModel?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(repositoryEntityModel: RepositoryEntityModel)

    @Query("DELETE FROM ${RepositoryEntityModel.TABLE_NAME}")
    suspend fun deleteAll()
}