package com.githubussuelist.interactions

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText

fun writeEditTextText(editTextId: Int, text: String): ViewInteraction =
    withViewId(editTextId).perform(replaceText(text), closeSoftKeyboard())