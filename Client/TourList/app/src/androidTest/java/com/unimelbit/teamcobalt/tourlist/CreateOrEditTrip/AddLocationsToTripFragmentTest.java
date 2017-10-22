package com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip;

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
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Tests the UI of the AddLocationsToTripFragment
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddLocationsToTripFragmentTest {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityTestRule = new ActivityTestRule<>(BaseActivity.class);

    /*
     * A user flow that gets to the AddLocationsToTripFragment to test
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

        // Goes to the create trip page
        onView(withId(R.id.createButtonMain)).perform(click());

        // Goes to the add locations to trip fragment
        onView(
                allOf(withId(R.id.create_trip_container),
                        isDisplayed()))
                .perform(swipeLeft());
    }

    /*
     *  Checks that the add locations fragment displays the required
     */
    @Test
    public void addLocationsToTrip() {

        // Checks that the button to add a location to a trip is displayed
        onView(
                withId(R.id.add_location_button))
                .check(matches(isDisplayed())
                );

        // Checks that the current locations in the trip are displayed
        onView(
                withId(R.id.listView))
                        .check(matches(isDisplayed())
                        );

        // Checks that the submit button is displayed
        onView(
                withId(R.id.done_button))
                .check(matches(isDisplayed())
                );
    }

}
