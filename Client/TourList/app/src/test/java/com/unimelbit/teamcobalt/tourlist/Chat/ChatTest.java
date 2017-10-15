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
    public void getSetMessageText() throws Exception {
//        assertEquals(text, chat.getMessageText());
//
//        String newMessage = "new message";
//        chat.setMessageText(newMessage);
//        assertEquals(newMessage, chat.getMessageText());
    }

    @Test
    public void getSetMessageUser() throws Exception {
//        assertEquals(user, chat.getMessageUser());
//
//        String newUser = "new user";
//        chat.setMessageUser(newUser);
//        assertEquals(newUser, chat.getMessageUser());
    }

    @Test
    public void getSetMessageTime() throws Exception {
//        // makes sure the new time is different
//        Thread.sleep(1000);
//        Long newTime = new Date().getTime();
//
//        assertTrue(newTime != chat.getMessageTime());
//
//        chat.setMessageTime(newTime);
//        assertTrue(newTime == chat.getMessageTime());
    }

}