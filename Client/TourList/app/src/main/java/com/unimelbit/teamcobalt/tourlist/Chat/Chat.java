package com.unimelbit.teamcobalt.tourlist.Chat;

import java.util.Date;

/**
 * Model class to act as template for the messages used in firebase
 *
 * Tutorial from: Ashraff Hathibelagal
 * @https://code.tutsplus.com/tutorials/how-to-create-an-android-chat-app-using-firebase--cms-27397
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
