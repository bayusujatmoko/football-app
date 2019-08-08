package match.football.submission2.submission2.MatchTest

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
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
class LastMatchTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun RecyclerViewBehaviourTestNextMatch() {
        Espresso.onView(ViewMatchers.withId(match)).perform(ViewActions.click())
        Thread.sleep(6000)
        Espresso.onView(ViewMatchers.withText("Last Match")).perform(ViewActions.click())
        Thread.sleep(7000)
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(rvLastMatch), ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(rvLastMatch), ViewMatchers.isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(rvLastMatch), ViewMatchers.isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))
    }

    @Test
    fun DetailBehaviourTestNextMatch() {
        Espresso.onView(ViewMatchers.withId(match)).perform(ViewActions.click())
        Thread.sleep(6000)
        Espresso.onView(ViewMatchers.withText("Last Match")).perform(ViewActions.click())
        Thread.sleep(7000)
        onView(Matchers.allOf(ViewMatchers.withId(rvLastMatch), ViewMatchers.isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, ViewActions.click()))
        Thread.sleep(7000)

        Espresso.onView(ViewMatchers.withId(match_date))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(img_home))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(home_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(img_away))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(home_score_match))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_score_match))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(home_shots))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_shot))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(home_goals))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_goals))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(home_goalkeeper))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_goalkeeper))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(home_defense))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_defense))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(home_midfield))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_midfield))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(home_substitutes)).perform(ViewActions.scrollTo())
        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(home_forward))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_forward))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(home_substitutes))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_substitutes))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun FavoriteBehaviourTestLastMatch() {
        Espresso.onView(ViewMatchers.withId(match)).perform(ViewActions.click())
        Thread.sleep(6000)
        Espresso.onView(ViewMatchers.withText("Last Match")).perform(ViewActions.click())
        Thread.sleep(7000)
        Espresso.onView(ViewMatchers.withId(rvLastMatch))
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
        onView(
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
        onView(
            Matchers.allOf(
                ViewMatchers.withId(rvFavorite),
                ViewMatchers.isDisplayed()
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}