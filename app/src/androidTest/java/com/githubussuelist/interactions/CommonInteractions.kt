package com.githubussuelist.interactions

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.actionWithAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher


fun withViewId(viewId: Int): ViewInteraction = onView(withId(viewId))

fun withViewText(@StringRes stringResId: Int): ViewInteraction = onView(withText(stringResId))
fun withViewText(text: String): ViewInteraction = onView(withText(text))

fun clickOnView(viewId: Int): ViewInteraction = withViewId(viewId).perform(ViewActions.click())