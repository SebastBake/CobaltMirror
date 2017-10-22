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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * UI tests for the ChatListRoomFragment
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChatListRoomFragmentTest {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule =
            new ActivityTestRule<BaseActivity>(BaseActivity.class);


    /*
     *  A user flow that gets to the ChatListRoomFragment to test
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
    }

    /*
     * Tests that the key parts required of the ChatList Fragment are displayed
     */
    @Test
    public void chatlist() {

        // Checks for button to go to general chat room
        onView(
                withId(R.id.gen_chat_button))
                .check(matches(isDisplayed())
                );

        // Checks for button to go to random chat room
        onView(
                withId(R.id.rand_chat_button))
                .check(matches(isDisplayed())
                );
    }

}
