package com.githubussuelist

import android.app.Application
import com.githubussuelist.di.AppComponent
import com.githubussuelist.di.DaggerAppComponent
import com.githubussuelist.di.DaggerComponentProvider
import timber.log.Timber

class MainApplication : Application(), DaggerComponentProvider {
    override var component: AppComponent = DaggerAppComponent
        .builder()
        .applicationContext(this)
        .build()

    override fun onCreate() {
        super.onCreate()

        // TODO: Configure Timber correctly
        Timber.plant(Timber.DebugTree())
    }
}