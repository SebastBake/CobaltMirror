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
 * Created by awhite on 21/10/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterFragmentTest {
    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule = new ActivityTestRule<>(
            BaseActivity.class);

    @Before
    public void init(){

        if (BaseActivity.getCurrentUser() != null) {

            onView(
                    allOf(withContentDescription("Open navigation drawer"),
                            withParent(withId(R.id.toolbar)),
                            isDisplayed()))
                    .perform(click());

            onView(
                    allOf(withId(R.id.design_menu_item_text),
                            withText("Logout"),
                            isDisplayed()))
                    .perform(click());

            onView(
                    allOf(withId(android.R.id.button1),
                            withText("YES")))
                    .perform(click());
        }

        onView(withId(R.id.go_to_register_fragment)).perform(click());

    }

    @Test
    public void register() {

        onView(
                withId(R.id.register_email_field))
                .check(matches(isDisplayed()));

        onView(
                withId(R.id.register_username_field))
                .check(matches(isDisplayed()));

        onView(
                withId(R.id.register_password_field))
                .check(matches(isDisplayed()));

        onView(
                withId(R.id.button_register))
                .check(matches(isDisplayed()));
    }

}