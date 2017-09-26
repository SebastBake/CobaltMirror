package com.unimelbit.teamcobalt.tourlist.Chat;

/**
 * Created by Hong Lin on 26/09/2017.
 */

/**
 * Adaptor interface for chat used by this app
 */
public interface ChatAdaptor {

    //Generates chat room
    void generateChatRoom(String name);

    //Takes uer into the chat room
    void enterChatRoom(String userName, String room);

    //Checks if the room is present in DB
    void checkRoom(String room);


}
