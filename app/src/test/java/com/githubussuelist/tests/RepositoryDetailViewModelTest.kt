package com.githubussuelist.tests

import androidx.lifecycle.MutableLiveData
import com.githubussuelist.common.BaseTest
import com.githubussuelist.model.common.Result
import com.githubussuelist.repository.github.GitHubRepository
import com.githubussuelist.ui.repository.RepositoryDetailResult
import com.githubussuelist.ui.repository.RepositoryDetailViewModel
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RepositoryDetailViewModelTest: BaseTest() {
    private val mGithubRepository: GitHubRepository = mockk()

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `Test the class initialization and repository saving with valid name`() {
        val viewModel = viewModelInstance()

        // In order to update the repositoryDetail, we must observe it.
        viewModel.repositoryDetail.observeForever {  }

        assertEquals("", viewModel.repositoryName.value, "The repository name must start empty")

        val repositoryEntityModel = createNewRepositoryEntityModel("org/repository-name")

        viewModel.init(repositoryEntityModel)

        assertEquals(repositoryEntityModel.fullName, viewModel.repositoryName.value)

        val newOrgName = "org"
        val newRepositoryName = "new-repository-name"
        val newRepositoryFullName = "$newOrgName/$newRepositoryName"
        viewModel.repositoryName.value = newRepositoryFullName

        val newRepositoryEntityModel = createNewRepositoryEntityModel(newRepositoryFullName)
        val successResult = Result.success(newRepositoryEntityModel)
        every {
            mGithubRepository.fetchAndSaveRepository(any(), any(), any())
        } returns MutableLiveData(successResult)

        viewModel.saveRepositoryDetail()

        verify {
            mGithubRepository.fetchAndSaveRepository(any(), newOrgName, newRepositoryName)
        }

        assertEquals(successResult, viewModel.repositoryDetail.value, "Repository saving status")
        assertEquals(RepositoryDetailResult.Updated(newRepositoryEntityModel), viewModel.dismiss.value, "Close the dialog with update result")
    }

    @Test
    fun `Save repository without change the name test`() {
        val viewModel = viewModelInstance()
        val repositoryEntityModel = createNewRepositoryEntityModel("org/repository-name-readonly")

        viewModel.init(repositoryEntityModel)

        viewModel.saveRepositoryDetail()

        // Must not call the method to save the repository
        verify(inverse = true) {
            mGithubRepository.fetchAndSaveRepository(any(), any(), any())
        }

        assertEquals(RepositoryDetailResult.Dismiss, viewModel.dismiss.value, "Close the dialog with dismiss result")
    }

    @Test
    fun `Try to save repository with invalid name test`() {
        val viewModel = viewModelInstance()

        viewModel.init(null)

        viewModel.saveRepositoryDetail()

        viewModel.repositoryName.value = "invalid-repository-name"

        viewModel.saveRepositoryDetail()

        assertNotNull(viewModel.repositoryNameValidation.value, "Must show an invalid repository name message")

        // Must not call the method to save the repository
        verify(inverse = true) {
            mGithubRepository.fetchAndSaveRepository(any(), any(), any())
        }
    }

    @Test
    fun `Close dialog test`() {
        val viewModel = viewModelInstance()
        viewModel.closeDialog()
        assertEquals(RepositoryDetailResult.Dismiss, viewModel.dismiss.value, "Close the dialog with dismiss result")
    }

    private fun viewModelInstance()
            = RepositoryDetailViewModel(mGithubRepository)
}