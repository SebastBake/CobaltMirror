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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * UI tests for the LoginOrRegisterFragment
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginOrRegisterFragmentTest {


    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule = new ActivityTestRule<>(
            BaseActivity.class);

    /*
     *  A user flow that gets to the LoginOrRegisterFragment to test
     */
    @Before
    public void init(){

        // Handles the case when the user is logged in
        if (BaseActivity.getCurrentUser() != null) {

            // Opens up the nav bar
            onView(
                    allOf(withContentDescription("Open navigation drawer"),
                            withParent(withId(R.id.toolbar)),
                            isDisplayed()))
                    .perform(click());

            // Selects log out
            onView(
                    allOf(withId(R.id.design_menu_item_text),
                            withText("Logout"),
                            isDisplayed()))
                    .perform(click());

            // Confirms logout
            onView(
                    allOf(withId(android.R.id.button1),
                            withText("YES")))
                    .perform(click());

        }

    }

    /*
     * Tests going to the login page
     */
    @Test
    public void goToLogin() {

        // Clicks on login button
        onView(withId(R.id.go_to_login_fragment)).perform(click());

        // Checks you went to the login page
        onView(withId(R.id.login_username_field)).check(matches(isDisplayed()));

    }

    /*
     * Tests going to the register page
     */
    @Test
    public void goToregister() {

        // CLicks the register button
        onView(withId(R.id.go_to_register_fragment)).perform(click());

        // Checks you went to the register page
        onView(withId(R.id.register_username_field)).check(matches(isDisplayed()));

    }

}
