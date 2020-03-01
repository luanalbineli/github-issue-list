package com.githubussuelist.di.module

import com.githubussuelist.repository.base.DefaultIdlingResourceCounter
import com.githubussuelist.repository.base.IdlingResourceCounter
import dagger.Binds
import dagger.Module

@Module
abstract class BindingModule {
    @Binds
    abstract fun bindIdlingResourceCounter(defaultIdlingResourceCounter: DefaultIdlingResourceCounter): IdlingResourceCounter
}