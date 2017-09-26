package com.unimelbit.teamcobalt.tourlist.Chat;

/**
 * Created by Hong Lin on 26/09/2017.
 */

public interface ChatAdaptor {


    public void generateChatRoom(String name);

    public void enterChatRoom(String userName, String room);

    public void checkRoom(String room);


}
