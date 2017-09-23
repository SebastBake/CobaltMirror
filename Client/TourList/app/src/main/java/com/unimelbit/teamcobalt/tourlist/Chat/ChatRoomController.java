package com.unimelbit.teamcobalt.tourlist.Chat;

import android.content.Context;
import android.content.Intent;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;

/**
 * Created by Hong Lin on 23/09/2017.
 */

public class ChatRoomController {

    BaseActivity base;

    public ChatRoomController(Context c){

        base = (BaseActivity) c;

    }

    public void enterRoom(String name, String room) {

        Intent chatIntent = new Intent(base, ChatroomActivity.class);

        chatIntent.putExtra("Name", name);

        chatIntent.putExtra("Room_name", room);

        base.startActivity(chatIntent);
    }



}
