package com.githubussuelist.common

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import com.githubussuelist.MainApplicationTest
import com.githubussuelist.di.DaggerTestAppComponent
import com.githubussuelist.idlingResources.EspressoIdlingResourceCounter
import com.githubussuelist.repository.base.IdlingResourceCounter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import timber.log.Timber
import javax.inject.Inject

abstract class InstrumentedTest {
    protected val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    protected val app = context.applicationContext as MainApplicationTest

    protected val testAppComponent by lazy {
        DaggerTestAppComponent.builder()
            .applicationContext(app)
            .build()
    }

    @Inject
    lateinit var mDefaultIdlingResourceCounter: IdlingResourceCounter

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun daggerTestSetup() {
        app.component = testAppComponent
        testAppComponent.inject(this)
        IdlingRegistry.getInstance().register((mDefaultIdlingResourceCounter as EspressoIdlingResourceCounter).countingIdlingResource)
    }

    @After
    fun finish() {
        IdlingRegistry.getInstance().unregister((mDefaultIdlingResourceCounter as EspressoIdlingResourceCounter).countingIdlingResource)
    }
}