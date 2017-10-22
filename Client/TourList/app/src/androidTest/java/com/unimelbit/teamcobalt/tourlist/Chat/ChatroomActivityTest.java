package com.unimelbit.teamcobalt.tourlist.Chat;

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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * UI testing for ChatroomActivity
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChatroomActivityTest {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule =
            new ActivityTestRule<BaseActivity>(BaseActivity.class);

    /*
     * User UI flow to get to the Chatroom for tests
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

        onView(withId(R.id.generalChat)).perform(click());

        onView(
                allOf(
                        withId(R.id.gen_chat_button),
                        withText("GENERAL CHAT"),
                        isDisplayed()))
                .perform(click()
                );
    }

    /*
     * Tests that all UI elements required of a chatroom are there
     * and that they are functional
     * i.e. can type message into text box
     */
    @Test
    public void chatroom() throws Exception {

        // Checks that the message box is displayed
        onView(
                withId(R.id.input))
                .check(matches(isDisplayed())
                );

        // Simulates a user typing a message
        onView(
                allOf(withId(R.id.input), isDisplayed()))
                .perform(replaceText("Hi"), closeSoftKeyboard()
                );

        // Checks that the message is displayed
        onView(
                withId(R.id.input))
                .check(matches(withText("Hi"))
                );

        // Tries to send message
        onView(
                allOf(withId(R.id.fab),
                        withParent(allOf(withId(R.id.activity_main),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()))
                .perform(click());
    }

}
