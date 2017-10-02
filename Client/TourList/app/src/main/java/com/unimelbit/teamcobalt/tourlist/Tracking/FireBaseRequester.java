package com.unimelbit.teamcobalt.tourlist.Tracking;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.Chat.FirebaseChatRoomHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hong Lin on 1/10/2017.
 */

public class FireBaseRequester implements CoordinateDBPostRequester{

    private Context c;

    public final static int NO_VALUE = 0;

    public final static int LAT_INDEX = 0, LONG_INDEX = 1;

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


    /**
     * Get coordinates for user from firebase. Will return an array with the coordinates with indices
     * of lat = [0] and long = [1]
     * @param userRef
     * @return
     */
    public double[] getCoordinates(String userRef) {

        final double[] coordinates = {};

        fbHandler.checkRoom(userRef);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        DatabaseReference latRef = ref.child(userRef).child("lat");

        DatabaseReference longRef = ref.child(userRef).child("long");

        latRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String lat_String = dataSnapshot.getValue(String.class);

                if (lat_String.isEmpty() || lat_String == null) {

                    coordinates[LAT_INDEX] = NO_VALUE;

                } else {

                    coordinates[LAT_INDEX] = Double.valueOf(lat_String);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        longRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String long_String = dataSnapshot.getValue(String.class);

                if (long_String.isEmpty() || long_String == null) {

                    coordinates[LONG_INDEX] = NO_VALUE;

                } else {

                    coordinates[LONG_INDEX] = Double.valueOf(long_String);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        return coordinates;

    }


}
