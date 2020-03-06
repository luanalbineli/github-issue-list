package com.githubussuelist.test.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.githubussuelist.common.InstrumentedTest
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.repository.room.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.random.Random


@RunWith(AndroidJUnit4::class)
class GitHubIssueDbTest : InstrumentedTest() {
    @Inject
    lateinit var mRoomRepository: RoomRepository

    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Before
    fun setup() {
        testAppComponent.inject(this)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun test_save_repository() {
        val repositoryEntityModel = RepositoryEntityModel(
            id = 1,
            description = "Repository description",
            forkCount = 102,
            fullName = "org/repository-name",
            name = "repository-name",
            openIssueCount = 104,
            starCount = 124,
            subscriberCount = 5657
        )
        runBlocking {
            mRoomRepository.saveRepository(repositoryEntityModel)
        }

        val savedRepositoryEntityModel = runBlocking {
            mRoomRepository.getRepositoryAsync()
        }

        assertNotNull("There must be a repository saved", savedRepositoryEntityModel)
        assertTrue(
            "The repository must be equal",
            repositoryEntityModel == savedRepositoryEntityModel
        )

        val repositoryIssueEntityList = List(Random.nextInt(10)) {
            createNewRepositoryIssueEntity(it, savedRepositoryEntityModel!!.id)
        }

        runBlocking {
            mRoomRepository.saveIssueList(repositoryIssueEntityList)
        }

        val savedRepositoryIssueList = runBlocking {
            mRoomRepository.getAllIssueAsync()
        }

        assertEquals(
            "The issue list size must be the same",
            repositoryIssueEntityList.size,
            savedRepositoryIssueList.size
        )

        repositoryIssueEntityList.sortedByDescending { it.createdAt }
            .forEachIndexed { index, repositoryIssueEntityModel ->
                assertEquals(
                    "The issue: $repositoryIssueEntityModel must be equal",
                    repositoryIssueEntityModel,
                    savedRepositoryIssueList[index]
                )
            }

        runBlocking {
            mRoomRepository.removeAllIssues()
        }

        val emptyRepositoryIssueList = runBlocking {
            mRoomRepository.getAllIssueAsync()
        }

        assertTrue("After remove all issues, the fetch method must return an empty list", emptyRepositoryIssueList.isEmpty())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}