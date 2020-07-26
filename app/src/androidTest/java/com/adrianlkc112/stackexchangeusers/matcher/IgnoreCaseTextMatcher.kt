package com.adrianlkc112.stackexchangeusers.matcher

import android.view.View
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers

object IgnoreCaseTextMatcher {
    /**
     * Returns a matcher that matches [TextView] based on its text property value BUT IGNORES CASE.
     * Note: View's Sugar for withText(is("string")).
     *
     * @param text [String] with the text to match
     */
    fun withText(text: String): BoundedMatcher<View?, TextView> {
        return withText(Matchers.`is`(text.toLowerCase()))
    }

    fun withText(stringMatcher: Matcher<String>): BoundedMatcher<View?, TextView> {
        checkNotNull(stringMatcher)
        return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with text (ignoring case): ")
                stringMatcher.describeTo(description)
            }

            public override fun matchesSafely(textView: TextView): Boolean {
                return stringMatcher.matches(textView.text.toString().toLowerCase())
            }
        }
    }
}