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
 * Created by awhite on 21/10/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SuccessActivityTest {

    @Rule
    public ActivityTestRule<SuccessActivity> mActivityRule = new ActivityTestRule<>(
            SuccessActivity.class);

    @Before
    public void setUp() throws Exception {
        onView(withId(R.id.success_message_out)).check(matches(isDisplayed()));
    }

    @Test
    public void success() {
        onView(
                withId(R.id.error_fragment_something_went_right_textview))
                .check(matches(withText("Success!"))
                );
    }

}