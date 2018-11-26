package com.jelekong.footballmatchschedule

import android.os.SystemClock
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.rule.ActivityTestRule
import android.view.View
import android.view.ViewGroup
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun MainActivityTest() {

        val appCompatSpinner = onView(
                allOf(withId(R.id.spinner_next),
                        childAtPosition(
                                withParent(withId(R.id.viewpager_mathes)),
                                0),
                        isDisplayed()))
        appCompatSpinner.perform(click())

        val appCompatCheckedTextView = onData(anything())
                .inAdapterView(withClassName(`is`("android.support.v7.widget.DropDownListView")))
                .atPosition(1)
        appCompatCheckedTextView.perform(click())

        val recyclerView = onView(
                allOf(withId(R.id.rv_next),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                1)))
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        val actionMenuItemView = onView(
                allOf(withId(R.id.add_to_favorite), withContentDescription("favorite"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView.perform(click())

        val appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        val tabView = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs_matches),
                                0),
                        1),
                        isDisplayed()))
        tabView.perform(click())

        val viewPager = onView(
                allOf(withId(R.id.viewpager_mathes),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_container),
                                        0),
                                1),
                        isDisplayed()))
        viewPager.perform(swipeLeft())

        val appCompatSpinner2 = onView(
                allOf(withId(R.id.spinner_last),
                        childAtPosition(
                                withParent(withId(R.id.viewpager_mathes)),
                                0),
                        isDisplayed()))
        appCompatSpinner2.perform(click())

        val appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(withClassName(`is`("android.support.v7.widget.DropDownListView")))
                .atPosition(1)
        appCompatCheckedTextView2.perform(click())

        val recyclerView2 = onView(
                allOf(withId(R.id.rv_last),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                1)))
        recyclerView2.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        val actionMenuItemView2 = onView(
                allOf(withId(R.id.add_to_favorite), withContentDescription("favorite"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()))
        actionMenuItemView2.perform(click())

        val appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton2.perform(click())

        val actionMenuItemView3 = onView(
                allOf(withId(R.id.search), withContentDescription("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()))
        actionMenuItemView3.perform(click())

        val searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()))
        searchAutoComplete.perform(replaceText("c"), closeSoftKeyboard())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }


        val searchAutoComplete2 = onView(
                allOf(withId(R.id.search_src_text), withText("c"),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()))
        searchAutoComplete2.perform(replaceText("ch"))

        val searchAutoComplete3 = onView(
                allOf(withId(R.id.search_src_text), withText("ch"),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()))
        searchAutoComplete3.perform(closeSoftKeyboard())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }


        val searchAutoComplete4 = onView(
                allOf(withId(R.id.search_src_text), withText("ch"),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()))
        searchAutoComplete4.perform(replaceText("che"))

        val searchAutoComplete5 = onView(
                allOf(withId(R.id.search_src_text), withText("che"),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()))
        searchAutoComplete5.perform(closeSoftKeyboard())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val appCompatImageButton3 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButton3.perform(click())

        val bottomNavigationItemView = onView(
                allOf(withId(R.id.teams),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                1),
                        isDisplayed()))
        bottomNavigationItemView.perform(click())

        val appCompatSpinnerTeam = onView(
                allOf(withId(R.id.spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_container),
                                        0),
                                0),
                        isDisplayed()))
        appCompatSpinnerTeam.perform(click())

        val appCompatCheckedTextViewTeam = onData(anything())
                .inAdapterView(withClassName(`is`("android.support.v7.widget.DropDownListView")))
                .atPosition(1)
        appCompatCheckedTextViewTeam.perform(click())

        val recyclerViewTeam = onView(
                allOf(withId(R.id.rv_teams),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                1)))
        recyclerViewTeam.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val tabViewTeam = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.detail_tab_team),
                                0),
                        1),
                        isDisplayed()))
        tabViewTeam.perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.rv_player))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_player)).perform(RecyclerViewActions.scrollToPosition< RecyclerView.ViewHolder>(5))

        SystemClock.sleep(5000)
        val recyclerViewPlayer = onView(
                allOf(withId(R.id.rv_player),
                        childAtPosition(
                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                0)))
        recyclerViewPlayer.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val appCompatImageButtonPlayer = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageButtonPlayer.perform(click())

        Espresso.onView(withId(R.id.iv_back)).perform(click());

        val bottomNavigationItemView2 = onView(
                allOf(withId(R.id.favorite),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                2),
                        isDisplayed()))
        bottomNavigationItemView2.perform(click())

        val tabView2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tabs_main),
                                0),
                        1),
                        isDisplayed()))
        tabView2.perform(click())

        val viewPager2 = onView(
                allOf(withId(R.id.viewpager_main),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_container),
                                        0),
                                1),
                        isDisplayed()))
        viewPager2.perform(swipeLeft())

        pressBack()


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
                return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
            }
        }
    }
}