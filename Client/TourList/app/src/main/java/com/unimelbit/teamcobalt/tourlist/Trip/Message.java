package com.unimelbit.teamcobalt.tourlist.Trip;

/**
 * Created by awhite on 9/09/17.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by awhite on 13/09/17.
 */

public class Message {
    private String message;
    private String username;
    private String time;

    public Message() {
    }

    public Message(String message, String username) {
        this.message = message;
        this.username = username;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        this.time = dateFormat.format(new Date());
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


