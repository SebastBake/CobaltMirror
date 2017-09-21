package com.unimelbit.teamcobalt.tourlist.Chat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Hong Lin on 22/09/2017.
 */

public class ChatroomCreator {

    private DatabaseReference root;

    public ChatroomCreator(){

        root = FirebaseDatabase.getInstance().getReference().getRoot();

    }

    public void generateRoom(String name){

        Map <String, Object> map = new HashMap<String, Object>();

        map.put(name, "");

        root.updateChildren(map);

        System.out.println("generated room REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");


    }


}
