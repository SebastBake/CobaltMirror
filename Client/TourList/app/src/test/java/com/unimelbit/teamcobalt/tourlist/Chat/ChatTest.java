package com.unimelbit.teamcobalt.tourlist.Chat;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by awhite on 8/10/17.
 */
public class ChatTest {

    String text;
    String user;
    Chat chat;

    @Before
    public void setUp() throws Exception {
        this.text = "message";
        this.user = "user";
        this.chat = new Chat(this.text, this.user);
    }

    @Test
    public void getSetMessage() throws Exception {
        assertEquals(text, chat.getMessage());

        String newMessage = "new message";
        chat.setMessageText(newMessage);
        assertEquals(newMessage, chat.getMessage());

    }

    @Test
    public void getSetMessageUser() throws Exception {
        assertEquals(user, chat.getUserName());

        String newUser = "new user";
        chat.setUserName(newUser);
        assertEquals(newUser, chat.getUserName());
    }

    @Test
    public void getSetMessageTime() throws Exception {
        // makes sure the new time is different
        Thread.sleep(1000);
        Long newTime = new Date().getTime();

        assertTrue(newTime != chat.getTime());

        chat.setTime(newTime);
        assertTrue(newTime == chat.getTime());
    }

}