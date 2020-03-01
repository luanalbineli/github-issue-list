package com.githubussuelist.di

import android.content.Context
import com.githubussuelist.di.module.AppModule
import com.githubussuelist.ui.repository.RepositoryViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): AppComponent
    }

    fun repositoryViewModelFactory(): ViewModelFactory<RepositoryViewModel>
}