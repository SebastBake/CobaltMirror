package com.unimelbit.teamcobalt.tourlist.Home;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * UI tests for the HomeFragment
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeFragmentTest {
    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule = new ActivityTestRule<>(
            BaseActivity.class);

    /*
     *  A user flow that gets to the HomeFragment to test
     */
    @Before
    public void init(){

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

    }

    /*
     * Tests that the key parts required of the Home Fragment are displayed
     */
    @Test
    public void HomeUI() {

        // Button to go to the my trips page
        onView(withId(R.id.myTripButton)).check(matches(isDisplayed()));

        // Button to go to the create trip page
        onView(withId(R.id.createButtonMain)).check(matches(isDisplayed()));

        // Button to go to search page
        onView(withId(R.id.searchButtonMain)).check(matches(isDisplayed()));

        // Button to go to chatroom list page
        onView(withId(R.id.generalChat)).check(matches(isDisplayed()));

    }
}
