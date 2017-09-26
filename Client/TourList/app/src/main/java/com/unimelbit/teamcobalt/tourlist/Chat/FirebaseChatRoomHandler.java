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
 *
 */

/**
 * Handler for chat in the app using Firebase. Implements the ChatAdaptor interface and
 * will offer services needed to function using firebase
 */
public class FirebaseChatRoomHandler implements ChatAdaptor {

    //References to database
    private DatabaseReference root, rootRef;

    private Context context;

    public FirebaseChatRoomHandler(Context c){

        root = FirebaseDatabase.getInstance().getReference().getRoot();

        rootRef = FirebaseDatabase.getInstance().getReference();

        this.context = c;

    }


    /**
     * Generates a chat room based on the name it receives
     * @param name
     */
    @Override
    public void generateChatRoom(String name) {


        Map<String, Object> map = new HashMap<String, Object>();

        map.put(name, "");

        root.updateChildren(map);

    }


    /**
     * Creates an intent and takes user to the chat room given with a user name
     * @param userName
     * @param room
     */
    @Override
    public void enterChatRoom(String userName, String room) {

        Intent chatIntent = new Intent(context, ChatroomActivity.class);

        chatIntent.putExtra("Name", userName);

        chatIntent.putExtra("Room_name", room);

        context.startActivity(chatIntent);

    }

    /**
     * Checks and generates a chat room when invoked with given name
     * @param room
     */
    @Override
    public void checkRoom(String room) {

        final String roomName = room;

        //Listener for changes and seeing if chat room present or not
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
