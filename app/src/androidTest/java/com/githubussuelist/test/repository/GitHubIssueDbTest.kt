package com.githubussuelist.test.repository

import android.os.AsyncTask
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.githubussuelist.common.InstrumentedTest
import com.githubussuelist.extension.blockingObserve
import com.githubussuelist.model.room.RepositoryEntityModel
import com.githubussuelist.repository.room.RoomRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@RunWith(AndroidJUnit4::class)
class GitHubIssueDbTest: InstrumentedTest() {
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
        Timber.d("test_save_repository - 1")
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
        Timber.d("test_save_repository - 2")
        CoroutineScope(AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()).launch {
            mRoomRepository.saveRepository(repositoryEntityModel)
        }

        Thread.sleep(10000)
        Timber.d("test_save_repository - 3")
        val savedRepository = mRoomRepository.getRepository().blockingObserve()
        Timber.d("test_save_repository - 4")
        assertNotNull("There must be a repository saved", savedRepository?.data)
        Timber.d("test_save_repository - 5")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}