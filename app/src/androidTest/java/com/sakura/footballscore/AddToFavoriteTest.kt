package com.sakura.footballscore


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddToFavoriteTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MenuActivity::class.java)

    @Test
    fun addToFavoriteTest() {
        Thread.sleep(3000);
        val linearLayout = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(`is`("org.jetbrains.anko._RelativeLayout")),
                                0),
                        0),
                        isDisplayed()))
        linearLayout.perform(click())
        Thread.sleep(3000);

        val actionMenuItemView = onView(
                allOf(withId(R.id.add_to_favorite), withContentDescription("Favorite"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView.perform(click())
        Thread.sleep(2000);

        pressBack()
        Thread.sleep(2000);

        val bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_favorite),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                2),
                        isDisplayed()))
        bottomNavigationItemView.perform(click())

        Thread.sleep(3000);

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
