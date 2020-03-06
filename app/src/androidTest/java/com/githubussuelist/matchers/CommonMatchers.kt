package com.githubussuelist.matchers

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import com.githubussuelist.interactions.withViewId
import com.githubussuelist.interactions.withViewText
import org.hamcrest.Matchers.not


fun assertDialogDisplayedWithText(@StringRes stringResId: Int): ViewInteraction =
    withViewText(stringResId)
        .inRoot(RootMatchers.isDialog())
        .check(matches(isDisplayed()))

fun assertViewDisabled(@IdRes viewId: Int): ViewInteraction = withViewId(viewId).check(
    matches(
        not(isEnabled())
    )
)

fun assertViewEnabled(@IdRes viewId: Int): ViewInteraction = withViewId(viewId).check(
    matches(
        isEnabled()
    )
)