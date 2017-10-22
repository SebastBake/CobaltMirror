package com.unimelbit.teamcobalt.tourlist.Chat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Tests for the Chat class.
 * Chat is used as a messages model for firebase
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class ChatTest {

    String text;
    String user;
    Chat chat;

    /*
     * Creates a Chat before each test to be used for testing
     */
    @Before
    public void setUp() throws Exception {
        this.text = "message";
        this.user = "user";
        this.chat = new Chat(this.text, this.user);
    }

    /*
     *  Checks the getter and setter methods for the messages text
     */
    @Test
    public void getSetMessage() throws Exception {

        //Checks that the text is what was set on construction
        assertEquals(text, chat.getMessage());

        // Sets a new message text and verifies it was updated properly
        String newMessage = "new message";
        chat.setMessageText(newMessage);
        assertEquals(newMessage, chat.getMessage());

    }

    /*
     *  Checks the getter and setter methods for the messages user
     */
    @Test
    public void getSetMessageUser() throws Exception {

        // Checks that the user is the one set when constructed
        assertEquals(user, chat.getUserName());

        // Sets a new user and verifies it was updated
        String newUser = "new user";
        chat.setUserName(newUser);
        assertEquals(newUser, chat.getUserName());

    }

    /*
     * Checks the getter and setter methods for the message time
     */
    @Test
    public void getSetMessageTime() throws Exception {

        // Makes sure the new time is different
        Thread.sleep(1000);
        Long newTime = new Date().getTime();

        // Checks that the new and old time are different
        assertTrue(newTime != chat.getTime());

        // Sets the new time and check it was updated properly
        chat.setTime(newTime);
        assertTrue(newTime == chat.getTime());

    }

}
