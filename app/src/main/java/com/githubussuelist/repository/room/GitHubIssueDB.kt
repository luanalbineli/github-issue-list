package com.githubussuelist.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.model.room.RepositoryIssueEntityModel
import com.githubussuelist.repository.room.dao.RepositoryDAO

@Database(entities = [
    RepositoryEntityModel::class,
    RepositoryIssueEntityModel::class
], version = 7)
// @TypeConverters(RoomConverters::class)
abstract class GitHubIssueDB: RoomDatabase() {
    abstract fun repositoryDAO() : RepositoryDAO
    /*abstract fun profileDAO(): ProfileDAO
    abstract fun creditCardDAO(): CreditCardDAO
    abstract fun bankAccountDAO(): BankAccountDAO*/
}