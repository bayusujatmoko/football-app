package match.football.submission2.submission2.TeamsTest

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import match.football.submission2.submission2.R.id.*
import match.football.submission2.submission2.app.adapter.HomeActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class TeamsTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun RecyclerViewBehaviourTestTeams() {
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(bottom_navigation))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(teams)).perform(ViewActions.click())
        Thread.sleep(7000)
        Espresso.onView(ViewMatchers.withId(rvTeam)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(rvTeam)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))
        Espresso.onView(ViewMatchers.withId(rvTeam))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, ViewActions.click()))
    }

    @Test
    fun PlayerBehaviourTestTeams() {
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(teams)).perform(ViewActions.click())
        Thread.sleep(7000)
        Espresso.onView(ViewMatchers.withId(rvTeam))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, ViewActions.click()))
        Thread.sleep(7000)
        Espresso.onView(ViewMatchers.withText("Players")).perform(ViewActions.click())
        Thread.sleep(7000)
        Espresso.onView(ViewMatchers.withId(rvPlayer)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        Espresso.onView(ViewMatchers.withId(rvPlayer))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, ViewActions.click()))
    }

    @Test
    fun DetailBehaviourTestTeams() {
        Espresso.onView(ViewMatchers.withId(teams)).perform(ViewActions.click())
        Thread.sleep(6000)
        Espresso.onView(ViewMatchers.withId(rvTeam))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, ViewActions.click()))
        Thread.sleep(6000)
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Added to Favorite"))
            .inRoot(RootMatchers.withDecorView(Matchers.not(activityRule.getActivity().getWindow().getDecorView())))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(4000)

        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Removed to Favorite"))
            .inRoot(RootMatchers.withDecorView(Matchers.not(activityRule.getActivity().getWindow().getDecorView())))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(4000)

        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Added to Favorite"))
            .inRoot(RootMatchers.withDecorView(Matchers.not(activityRule.getActivity().getWindow().getDecorView())))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(4000)

        Espresso.pressBack()

        Espresso.onView(ViewMatchers.withId(bottom_navigation))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(favorites)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withText("Team")).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(rvFavorite),
                ViewMatchers.isDisplayed()
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        Thread.sleep(6000)

        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Removed to Favorite"))
            .inRoot(RootMatchers.withDecorView(Matchers.not(activityRule.getActivity().getWindow().getDecorView())))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(2000)

        Espresso.pressBack()

        Espresso.onView(ViewMatchers.withId(favorites)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withText("Team")).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(rvFavorite),
                ViewMatchers.isDisplayed()
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}