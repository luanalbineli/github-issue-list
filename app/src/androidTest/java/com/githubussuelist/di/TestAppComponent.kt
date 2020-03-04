package com.githubussuelist.di

import android.content.Context
import com.githubussuelist.common.InstrumentedTest
import com.githubussuelist.di.module.TestAppModule
import com.githubussuelist.di.module.TestBindingModule
import com.githubussuelist.test.repository.GitHubIssueDbTest
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TestAppModule::class,
    TestBindingModule::class
])
interface TestAppComponent: AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): TestAppComponent
    }

    fun inject(test: InstrumentedTest)
    fun inject(test: GitHubIssueDbTest)
}