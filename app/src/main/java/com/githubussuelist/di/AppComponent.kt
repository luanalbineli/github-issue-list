package com.githubussuelist.di

import android.content.Context
import com.githubussuelist.di.module.AppModule
import com.githubussuelist.di.module.BindingModule
import com.githubussuelist.ui.main.MainViewModel
import com.githubussuelist.ui.repository.RepositoryDetailViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    BindingModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): AppComponent
    }

    fun mainViewModelFactory(): ViewModelFactory<MainViewModel>
    fun repositoryViewModelFactory(): ViewModelFactory<RepositoryDetailViewModel>
}