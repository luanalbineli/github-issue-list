package com.githubussuelist.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.githubussuelist.di.AppComponent
import com.githubussuelist.di.DaggerComponentProvider
import com.githubussuelist.di.ViewModelFactory

val AppCompatActivity.injector get() = (application as DaggerComponentProvider).component

fun <VM: ViewModel> AppCompatActivity.viewModelProvider(clazz: Class<VM>, viewModelFactory: ViewModelFactory<VM>)
        = ViewModelProvider(this, viewModelFactory).get(clazz)