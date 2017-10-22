package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * UI tests for the ChatFragment
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChatFragmentTest {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule = new ActivityTestRule<>(
            BaseActivity.class);

    /*
     *  A user flow that gets to the ChatFragment to test
     */
    @Before
    public void init() throws Exception {

        // Handles the case when the user isn't logged in
        if (BaseActivity.getCurrentUser() == null) {
            onView(withId(R.id.go_to_login_fragment)).perform(click());

            String username = "NewTestUser";
            onView(withId(R.id.login_username_field))
                    .perform(typeText(username), closeSoftKeyboard())
                    .check(matches(withText(username)));

            String password = "password";
            onView(withId(R.id.login_password_field))
                    .perform(typeText(password), closeSoftKeyboard());

            onView(withId(R.id.button_login)).perform(click());
        }

        // Goes to the search trip page
        onView(
                allOf(withId(R.id.searchButtonMain),
                        withText("SEARCH TRIP"),
                        withParent(withId(R.id.linearLayout3)),
                        isDisplayed()))
                .perform(click());

        // Does a random search
        onView(
                allOf(withId(R.id.Random_button),
                        withText("I'm feeling adventurous"),
                        isDisplayed()))
                .perform(click());

        // Selects the first trip returned
        onView(
                firstView(withId(R.id.Go_to_trip)))
                .perform(click());

        // Swipes from trip details tab to chat fragment
        onView(
                allOf(withId(R.id.trip_container),
                        isDisplayed()))
                .perform(swipeLeft());
    }

    /*
     * Checks that all the required fields are displayed
     * and functional
     */
    @Test
    public void chatFragment() {

        // Checks that the list of users is displayed
        onView(
                withId(R.id.userListChat))
                .check(matches(isDisplayed()));

        // Checks that the join chat button is displayed
        onView(
                withId(R.id.button_chat))
                .check(matches(isDisplayed()));

        // Checks you can go to the chat
        onView(
                allOf(withId(R.id.button_chat),
                        withText("start chatting"),
                        isDisplayed()))
                .perform(click());
    }

    /*
     * A matcher that returns the first view matched
     */
    private <T> Matcher<T> firstView(final Matcher<T> matcher) {
        return new BaseMatcher<T>() {

            boolean isFirst = true;

            @Override
            public boolean matches(final Object item) {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false;
                    return true;
                }

                return false;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("match first item");
            }
        };
    }
}
