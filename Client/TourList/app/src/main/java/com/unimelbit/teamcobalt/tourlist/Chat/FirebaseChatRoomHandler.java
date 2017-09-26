package com.unimelbit.teamcobalt.tourlist.Chat;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hong Lin on 26/09/2017.
 */

public class FirebaseChatRoomHandler implements ChatAdaptor {

    private DatabaseReference root, rootRef;

    private Context context;

    public FirebaseChatRoomHandler(Context c){

        root = FirebaseDatabase.getInstance().getReference().getRoot();

        rootRef = FirebaseDatabase.getInstance().getReference();

        this.context = c;

    }


    @Override
    public void generateChatRoom(String name) {


        Map<String, Object> map = new HashMap<String, Object>();

        map.put(name, "");

        root.updateChildren(map);

    }

    @Override
    public void enterChatRoom(String userName, String room) {

        Intent chatIntent = new Intent(context, ChatroomActivity.class);

        chatIntent.putExtra("Name", userName);

        chatIntent.putExtra("Room_name", room);

        context.startActivity(chatIntent);

    }

    @Override
    public void checkRoom(String room) {

        final String roomName = room;

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild(roomName)) {

                    generateChatRoom(roomName);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
