package com.unimelbit.teamcobalt.tourlist.Tracking;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.Chat.FirebaseChatRoomHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hong Lin on 1/10/2017.
 */

public class FireBaseRequester implements CoordinateDBPostRequester{

    private Context c;

    private FirebaseChatRoomHandler fbHandler;

    public FireBaseRequester(Context c){

        this.c = c;

        this.fbHandler = (FirebaseChatRoomHandler) AppServicesFactory
                .getServicesFactory().getFirebaseChatService(c);

    }

    /**
     * Post coordinates to the database specified by a reference
     * @param latitude
     * @param longitude
     * @param ref
     */
    @Override
    public void postToDb(double latitude, double longitude, String ref) {

        //Coordinates to push to DB
        Map coordinates = new HashMap<>();

        coordinates.put("lat", latitude);

        coordinates.put("long", longitude);

        //Check if node exists
        fbHandler.checkRoom(ref);

        //Remove old values
        fbHandler.deleteRoom(ref);

        //Push coordinates to DB
        FirebaseDatabase.getInstance()
                .getReference().child(ref).push().setValue(coordinates);
    }


}
