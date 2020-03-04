package com.githubussuelist.idlingResources

import androidx.test.espresso.idling.CountingIdlingResource
import com.githubussuelist.repository.base.DefaultIdlingResourceCounter
import com.githubussuelist.repository.base.IdlingResourceCounter
import javax.inject.Inject

class EspressoIdlingResourceCounter @Inject constructor() : IdlingResourceCounter {
    val countingIdlingResource = CountingIdlingResource("espresso")
    override fun increment() {
        countingIdlingResource.increment()
    }

    override fun decrement() {
        countingIdlingResource.decrement()
    }
}