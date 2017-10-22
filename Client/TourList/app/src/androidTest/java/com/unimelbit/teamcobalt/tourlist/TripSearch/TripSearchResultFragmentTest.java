package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.support.test.rule.ActivityTestRule;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * UI tests for the TripSearchResultFragment
 */

public class TripSearchResultFragmentTest {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule = new ActivityTestRule<>(
            BaseActivity.class);

    /*
     *  A user flow that gets to the TripSearchResultFragment to test
     */
    @Before
    public void init() throws Exception {
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

        // Goes to the search page
        onView(withId(R.id.searchButtonMain)).perform(click());

        // Does a random search to go to results page
        onView(withId(R.id.Random_button)).perform(click());

    }

    /*
     * Checks that all the required fields are displayed for the results page
     */
    @Test
    public void tripSearchResults() {

        // Checks user can add filters to the results
        onView(
                withId(R.id.searchFilter))
                .check(matches(isDisplayed()));

        // Checks the results are displayed
        onView(
                withId(R.id.results_list))
                .check(matches(isDisplayed()));
    }

}