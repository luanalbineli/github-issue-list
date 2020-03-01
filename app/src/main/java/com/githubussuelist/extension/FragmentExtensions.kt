package com.githubussuelist.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.githubussuelist.di.DaggerComponentProvider
import com.githubussuelist.di.ViewModelFactory

val Fragment.injector get() = (requireActivity().application as DaggerComponentProvider).component

fun <VM : ViewModel> Fragment.fragmentViewModelProvider(clazz: Class<VM>, viewModelFactory: ViewModelFactory<VM>) = ViewModelProvider(this, viewModelFactory).get(clazz)