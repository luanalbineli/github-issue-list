package com.githubussuelist.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.model.room.RepositoryIssueEntityModel
import com.githubussuelist.repository.room.dao.IRepositoryDAO
import com.githubussuelist.repository.room.dao.IRepositoryIssueDAO

@Database(entities = [
    RepositoryEntityModel::class,
    RepositoryIssueEntityModel::class
], version = 1)
@TypeConverters(RoomConverters::class)
abstract class GitHubIssueDB: RoomDatabase() {
    abstract fun repositoryDAO() : IRepositoryDAO
    abstract fun repositoryIssueDAO() : IRepositoryIssueDAO
}