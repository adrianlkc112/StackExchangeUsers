package com.adrianlkc112.stackexchangeusers

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.adrianlkc112.stackexchangeusers.activity.MainActivity
import com.adrianlkc112.stackexchangeusers.activity.UserDetailsActivity
import com.adrianlkc112.stackexchangeusers.matcher.IgnoreCaseTextMatcher
import com.adrianlkc112.stackexchangeusers.matcher.RecyclerViewMatcher
import com.adrianlkc112.stackexchangeusers.util.ScreenUtil
import org.hamcrest.Matchers.containsString
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        val intent = Intent()
        mActivityRule.launchActivity(intent)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.adrianlkc112.stackexchangeusers", appContext.packageName)
    }

    @Test
    fun testSearchWithValidResponseAndItemClick() {
        onView(withId(R.id.input_search_edittext))
            .perform(clearText(),typeText("test"))
        onView(withId(R.id.search_button))
            .perform(click())

        waitFinishProcessing()
        onView(withId(R.id.user_listview)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        if (getRVCount() > 1) {     //position 0 is title
            onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(1, R.id.record_username))
                    .check(matches(IgnoreCaseTextMatcher.withText(containsString("test"))))

            onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(1, R.id.record_layout))
                    .perform(click())

            intended(hasComponent(UserDetailsActivity::class.java.name))
        } else {
            throw Exception("Fail testInputTextAndSearchWithValidResponse: RecyclerView is empty")
        }
    }

    @Test
    fun testSearchWithValidResponseAndRotation() {
        onView(withId(R.id.input_search_edittext))
            .perform(clearText(),typeText("test"))
        onView(withId(R.id.search_button))
            .perform(click())

        waitFinishProcessing()
        onView(withId(R.id.user_listview)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        if (getRVCount() > 1) {     //position 0 is title
            onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(1, R.id.record_username))
                .check(matches(IgnoreCaseTextMatcher.withText(containsString("test"))))

            ScreenUtil().rotateScreen(mActivityRule.activity)
            Thread.sleep(1000)

            onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(1, R.id.record_username))
                .check(matches(IgnoreCaseTextMatcher.withText(containsString("test"))))
        } else {
            throw Exception("Fail testInputTextAndSearchWithValidResponse: RecyclerView is empty")
        }
    }

    @Test
    fun testSearchWithEmptyResponse() {
        onView(withId(R.id.input_search_edittext))
            .perform(clearText(),typeText("test1234567890"))
        onView(withId(R.id.search_button))
            .perform(click())

        waitFinishProcessing()
        onView(withId(R.id.user_no_data_textview)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun testEmptyInputText() {
        onView(withId(R.id.input_search_edittext))
            .perform(clearText())
        onView(withId(R.id.search_button))
            .perform(click())

        waitFinishProcessing()
        onView(withText(mActivityRule.activity.getString(R.string.err_msg_min_user_name))).check(matches(isDisplayed()))
    }

    private fun waitFinishProcessing() {
        var cnt = 10
        while(mActivityRule.activity.isLoading() || cnt > 0) {
            Thread.sleep(1000)
            cnt--
        }

        assertEquals(mActivityRule.activity.isLoading(), false)
    }

    private fun getRVCount(): Int {
        val recyclerView = mActivityRule.activity.findViewById<View>(R.id.user_listview) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }
}