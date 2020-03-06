package com.githubussuelist.test.issueList

import androidx.test.espresso.Espresso
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.githubussuelist.R
import com.githubussuelist.common.InstrumentedTest
import com.githubussuelist.interactions.clickOnView
import com.githubussuelist.interactions.writeEditTextText
import com.githubussuelist.matchers.*
import com.githubussuelist.ui.main.MainActivity
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
class RepositoryDetailTest: InstrumentedTest() {
    @Rule
    @JvmField
    val mActivityRule = IntentsTestRule(MainActivity::class.java, true, true)

    @Test
    fun repository_dialog_test() {
        assertDialogDisplayedWithText(R.string.text_dialog_repository_detail_title)

        // The save button must start disabled
        assertViewDisabled(R.id.button_repository_detail_save)
        val invalidRepositoryFullName = "architecture-components-samples"
        writeEditTextText(R.id.edit_text_repository_detail_name, invalidRepositoryFullName)
        // After fill the text input, it must be enabled for click
        assertViewEnabled(R.id.button_repository_detail_save)
        clickOnView(R.id.button_repository_detail_save)

        // Check if is showing the invalid repository name message
        assertInputLayoutWithError(R.id.edit_text_layout_repository_detail_name, R.string.error_repository_detail_invalid)

        // Save the repository and close the repository
        val repositoryFullName = "android/architecture-components-samples"
        writeEditTextText(R.id.edit_text_repository_detail_name, repositoryFullName)
        clickOnView(R.id.button_repository_detail_save)
        // Wait the bottom sheet to close
        Thread.sleep(500)
        // Check if the bottom sheet was closed (the TextView is not available if the bottom sheet is opened) and if the toolbar title matches the repository full name.
        assertWithText(R.id.text_issue_list_title, repositoryFullName)
    }

    @Test
    fun close_dialog_without_repository_test() {
        // Checking if the dialog is being shown
        assertDialogDisplayedWithText(R.string.text_dialog_repository_detail_title)

        Espresso.pressBackUnconditionally()

        assertTrue("The MainActivity must also be closed", mActivityRule.activity.isFinishing)
    }
}