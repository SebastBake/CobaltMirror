package com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.unimelbit.teamcobalt.tourlist.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests the UI of the SuccessActivity
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SuccessActivityTest {

    @Rule
    public ActivityTestRule<SuccessActivity> mActivityRule = new ActivityTestRule<>(
            SuccessActivity.class);

    /*
      * Always checks that the SuccessActivity is being displayed before each test
      */
    @Before
    public void setUp() throws Exception {
        onView(withId(R.id.success_message_out)).check(matches(isDisplayed()));
    }

    /*
     * Checks that the success page displays the required information
     */
    @Test
    public void success() {

        // checks that the success message is displayed
        onView(
                withId(R.id.error_fragment_something_went_right_textview))
                .check(matches(withText("Success!"))
                );
    }

}
