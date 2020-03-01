package com.githubussuelist.repository.base

import javax.inject.Inject

class DefaultIdlingResourceCounter @Inject constructor(): IdlingResourceCounter {
    override fun increment() { }

    override fun decrement() { }
}