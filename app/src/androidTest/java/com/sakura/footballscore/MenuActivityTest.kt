package com.sakura.footballscore


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4::class)
class MenuActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MenuActivity::class.java)

    @Test
    fun menuActivityTest() {
        sleep(5000);
        val bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_notifications),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                1),
                        isDisplayed()))
        bottomNavigationItemView.perform(click())

        sleep(5000);
        val bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_favorite),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                2),
                        isDisplayed()))
        bottomNavigationItemView2.perform(click())

        sleep(5000);
        val bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_home),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                0),
                        isDisplayed()))
        bottomNavigationItemView3.perform(click())

        sleep(2000);Ae
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
