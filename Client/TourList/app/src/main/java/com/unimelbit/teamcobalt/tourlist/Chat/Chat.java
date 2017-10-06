package com.unimelbit.teamcobalt.tourlist.Chat;

import java.util.Date;

/**
 * Created by Hong Lin on 22/09/2017.
 */

public class Chat {

    private String message;
    private String userName;
    private long time;

    public Chat(){
    }

    public Chat(String message, String user) {
        this.message = message;
        this.userName = user;

        // Initialize to current time
        time = new Date().getTime();
    }

    public String getMessage() {
        return message;
    }

    public void setMessageText(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
