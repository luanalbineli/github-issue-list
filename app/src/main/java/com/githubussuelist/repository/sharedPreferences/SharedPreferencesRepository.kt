package com.githubussuelist.repository.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.githubussuelist.model.sync.IssueSyncDetailModel
import com.githubussuelist.util.Constants
import java.util.*
import javax.inject.Inject

class SharedPreferencesRepository @Inject constructor(private val context: Context) {
    fun getIssueSyncDetailModel(): IssueSyncDetailModel? = withSharedPreferences {
        if (contains(KEY_SYNC_LAST_PAGE_INDEX)) {
            return@withSharedPreferences IssueSyncDetailModel(
                lastPageIndex = getInt(KEY_SYNC_LAST_PAGE_INDEX, Constants.API_INITIAL_PAGE_INDEX),
                lastSyncDate = Date(getLong(KEY_SYNC_LAST_PAGE_INDEX, 0)),
                hasMoreIssues = getBoolean(KEY_SYNC_HAS_MORE_ISSUES, true)
            )
        }
        return@withSharedPreferences null
    }

    private fun <T> withSharedPreferences(body: SharedPreferences.() -> T): T {
        val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return body(sharedPreferences)
    }

    fun saveIssueSyncDetailModel(issueSyncDetailModel: IssueSyncDetailModel) {

    }

    companion object {
        private const val SP_NAME = "shared_preferences"
        private const val KEY_SYNC_LAST_PAGE_INDEX = "key_sync_last_page_index"
        private const val KEY_SYNC_LAST_SYNC_DATE = "key_sync_last_sync_date"
        private const val KEY_SYNC_HAS_MORE_ISSUES = "key_sync_has_more_issues"
    }
}