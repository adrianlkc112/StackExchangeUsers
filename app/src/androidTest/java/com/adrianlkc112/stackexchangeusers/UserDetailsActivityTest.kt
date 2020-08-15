package com.adrianlkc112.stackexchangeusers

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.adrianlkc112.stackexchangeusers.activity.UserDetailsActivity
import com.adrianlkc112.stackexchangeusers.enum.UserType
import com.adrianlkc112.stackexchangeusers.matcher.IgnoreCaseTextMatcher
import com.adrianlkc112.stackexchangeusers.matcher.ImageViewMatcher
import com.adrianlkc112.stackexchangeusers.matcher.RecyclerViewMatcher
import com.adrianlkc112.stackexchangeusers.model.BadgeCount
import com.adrianlkc112.stackexchangeusers.model.User
import com.adrianlkc112.stackexchangeusers.util.ScreenUtil
import org.hamcrest.Matchers.containsString
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
class UserDetailsActivityTest {
    @get:Rule
    val mActivityRule: ActivityTestRule<UserDetailsActivity> = ActivityTestRule<UserDetailsActivity>(UserDetailsActivity::class.java, false, false)

    @Before
    fun setup() {
    }

    @Test
    fun testValidUserWithRotation() {
        val intent = Intent()
        intent.putExtra(UserDetailsActivity.ARG_USER, fakeUser())
        mActivityRule.launchActivity(intent)

        //Avatar
        onView(withId(R.id.avatar_imageview)).check(matches(ImageViewMatcher.DrawableMatcher(R.drawable.test_avatar_image)))

        //User Name
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(0, R.id.record_content))
            .check(matches(withText("Fake User")))

        //Reputation
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(1, R.id.record_content))
            .check(matches(withText("100")))

        //Badges
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(2, R.id.record_content))
            .check(matches(IgnoreCaseTextMatcher.withText(containsString("gold: 3"))))
            .check(matches(IgnoreCaseTextMatcher.withText(containsString("silver: 2"))))
            .check(matches(IgnoreCaseTextMatcher.withText(containsString("bronze: 1"))))

        //Location
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(3, R.id.record_content))
            .check(matches(withText("Hong Kong")))

        //Age
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(4, R.id.record_content))
            .check(matches(withText("30")))

        //Creation Date
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(5, R.id.record_content))
            .check(matches(withText(containsString("2020-07-26"))))

        //Rotation and double check some of the values
        ScreenUtil().rotateScreen(mActivityRule.activity)
        Thread.sleep(1000)

        onView(withId(R.id.avatar_imageview)).check(matches(ImageViewMatcher.DrawableMatcher(R.drawable.test_avatar_image)))

        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(0, R.id.record_content))
            .check(matches(withText("Fake User")))
    }

    @Test
    fun testInvalidUser() {
        val intent = Intent()
        mActivityRule.launchActivity(intent)

        //Avatar
        onView(withId(R.id.avatar_imageview)).check(matches(ImageViewMatcher.DrawableMatcher(R.drawable.empty_avatar_place_holder)))

        //User Name
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(0, R.id.record_content))
            .check(matches(withText(diplayNA())))

        //Reputation
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(1, R.id.record_content))
            .check(matches(withText(diplayNA())))

        //Badges
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(2, R.id.record_content))
            .check(matches(withText(diplayNA())))

        //Location
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(3, R.id.record_content))
            .check(matches(withText(diplayNA())))

        //Age
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(4, R.id.record_content))
            .check(matches(withText(diplayNA())))

        //Creation Date
        onView(RecyclerViewMatcher.recyclerViewWithId(R.id.user_listview).viewHolderViewAtPosition(5, R.id.record_content))
            .check(matches(withText(diplayNA())))
    }

    private fun fakeUser(): User {
        return User (
            about_me = null,
            accept_rate = null,
            account_id = 0,
            age = 30,
            answer_count = 0,
            badge_counts = BadgeCount(bronze = 1, silver = 2, gold = 3),
            creation_date = 1595747749,     //2020-07-26 15:15:49 GMT+8
            display_name = "Fake User",
            down_vote_count = 0,
            is_employee = false,
            last_access_date = 1595747749,     //2020-07-26 15:15:49 GMT+8
            last_modified_date = null,
            link = "",
            location = "Hong Kong",
            profile_image = "https://www.gravatar.com/avatar/fd107f4803495da58b8e4970986860f8?s=128&d=identicon&r=PG&f=1",
            question_count = 0,
            reputation = 100,
            reputation_change_day = 1,
            reputation_change_month = 1,
            reputation_change_quarter = 1,
            reputation_change_week = 1,
            reputation_change_year = 2020,
            timed_penalty_date= null,
            up_vote_count = 0,
            user_id = 1000,
            user_type = UserType.registered,
            view_count = 0,
            website_url = null
        )
    }

    private fun diplayNA(): String {
        return mActivityRule.activity.getString(R.string.label_not_available)
    }
}