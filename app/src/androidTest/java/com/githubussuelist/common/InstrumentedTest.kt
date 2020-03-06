package com.githubussuelist.common

import android.content.Context
import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import com.github.javafaker.Faker
import com.githubussuelist.MainApplicationTest
import com.githubussuelist.di.DaggerTestAppComponent
import com.githubussuelist.idlingResources.EspressoIdlingResourceCounter
import com.githubussuelist.model.room.RepositoryIssueEntityModel
import com.githubussuelist.repository.base.IdlingResourceCounter
import org.junit.After
import org.junit.Before
import javax.inject.Inject
import kotlin.random.Random

abstract class InstrumentedTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val app = context.applicationContext as MainApplicationTest

    protected val faker by lazy { Faker() }

    protected val testAppComponent by lazy {
        DaggerTestAppComponent.builder()
            .applicationContext(app)
            .build()
    }

    @Inject
    lateinit var mDefaultIdlingResourceCounter: IdlingResourceCounter

    @Before
    fun daggerTestSetup() {
        app.component = testAppComponent
        testAppComponent.inject(this)
        IdlingRegistry.getInstance()
            .register((mDefaultIdlingResourceCounter as EspressoIdlingResourceCounter).countingIdlingResource)
    }

    @After
    fun finish() {
        IdlingRegistry.getInstance()
            .unregister((mDefaultIdlingResourceCounter as EspressoIdlingResourceCounter).countingIdlingResource)
    }

    protected fun createNewRepositoryIssueEntity(id: Int, repositoryId: Int) =
        RepositoryIssueEntityModel(
            id = id,
            title = faker.lorem().word(),
            userName = faker.internet().emailAddress(),
            createdAt = faker.date().birthday(),
            labelMap = List(Random.nextInt(3)) {
                faker.lorem().word() to faker.lorem().word()
            }.toMap(),
            number = faker.number().randomDigit(),
            url = faker.internet().url(),
            repositoryId = repositoryId
        )
}