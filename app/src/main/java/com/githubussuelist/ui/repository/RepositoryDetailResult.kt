package com.githubussuelist.ui.repository

import com.githubussuelist.model.room.RepositoryEntityModel

sealed class RepositoryDetailResult {
    object Dismiss: RepositoryDetailResult()
    data class Updated constructor(val repositoryEntityModel: RepositoryEntityModel): RepositoryDetailResult()
}