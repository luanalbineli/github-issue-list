package com.githubussuelist.model.sync

import com.githubussuelist.util.Constants
import java.util.*

data class IssueSyncDetailModel(
    val lastPageIndex: Int = Constants.API_INITIAL_PAGE_INDEX,
    val lastSyncDate: Date,
    val hasMoreIssues: Boolean
)