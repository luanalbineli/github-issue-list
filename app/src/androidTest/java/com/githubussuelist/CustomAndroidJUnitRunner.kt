package com.githubussuelist

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class CustomAndroidJUnitRunner: AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, MainApplicationTest::class.java.name, context)
    }
}