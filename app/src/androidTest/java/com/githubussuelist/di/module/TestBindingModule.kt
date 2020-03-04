package com.githubussuelist.di.module

import com.githubussuelist.idlingResources.EspressoIdlingResourceCounter
import com.githubussuelist.repository.base.IdlingResourceCounter
import dagger.Binds
import dagger.Module

@Module
abstract class TestBindingModule {
    @Binds
    abstract fun bindIdlingResourceCounter(defaultIdlingResourceCounter: EspressoIdlingResourceCounter): IdlingResourceCounter
}