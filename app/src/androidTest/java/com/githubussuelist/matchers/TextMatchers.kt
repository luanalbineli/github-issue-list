package com.githubussuelist.matchers

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.githubussuelist.interactions.withViewId
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description

fun assertInputLayoutWithError(@IdRes viewId: Int, @StringRes errorId: Int): ViewInteraction =
    withViewId(viewId).check(
        matches(withError(errorId))
    )

fun assertWithText(@IdRes id: Int, text: String?): ViewInteraction =
    withViewId(id).check(matches(withText(text)))

fun withError(@StringRes error: Int) = object : BoundedMatcher<View, TextInputLayout>(
    TextInputLayout::class.java
) {
    override fun describeTo(description: Description) {
        description.appendText("with error: $error")
    }

    override fun matchesSafely(item: TextInputLayout): Boolean {
        return item.error == item.context.getString(error)
    }
}