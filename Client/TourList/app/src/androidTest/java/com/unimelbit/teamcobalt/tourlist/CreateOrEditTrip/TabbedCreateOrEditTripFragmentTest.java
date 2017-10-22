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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by awhite on 21/10/17.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class TabbedCreateOrEditTripFragmentTest {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityTestRule = new ActivityTestRule<>(BaseActivity.class);

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

        onView(withId(R.id.createButtonMain)).perform(click());
    }

    @Test
    public void TabbedCreateOrEditTrip() {

        onView(
                allOf(withId(R.id.create_trip_name_field),
                        isDisplayed()))
                .check(matches(withText(""))
                );

        onView(
                allOf(withId(R.id.create_trip_desc_field),
                        isDisplayed()))
                .check(matches(withText(""))
                );

        onView(
                allOf(withId(R.id.button_set_date),
                        isDisplayed()))
                .check(matches(isDisplayed())
                );

       onView(
                allOf(withId(R.id.done_button),
                        isDisplayed()))
               .check(matches(isDisplayed())
               );
    }


}