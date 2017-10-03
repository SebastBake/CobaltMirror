package com.unimelbit.teamcobalt.tourlist.Tracking;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.Chat.FirebaseChatRoomHandler;

import java.util.ArrayList;

/**
 * Created by Hong Lin on 2/10/2017.
 */

public class UserTracker {

    public final static double NO_VALUE = 0.0;

    public final static int LAT_INDEX = 0, LONG_INDEX = 1;

    private ArrayList<Double> coordinates;

    private FirebaseChatRoomHandler fbHandler;

    private Context c;

    public UserTracker(Context c){

        this.c = c;

        coordinates = new ArrayList<Double>();

        this.fbHandler = (FirebaseChatRoomHandler) AppServicesFactory
                .getServicesFactory().getFirebaseChatService(c);

    }


    /**
     * Get coordinates for user from firebase. Will return an array with the coordinates with indices
     * of lat = [0] and long = [1]
     * @param userRef
     * @return
     */
    public ArrayList<Double> getCoordinates(final String userRef) {

        fbHandler.checkRoom(userRef);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference coordinateRef = ref.child(userRef);

        coordinateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    double latVal = (double) snapshot.child("lat").getValue(Double.class);

                    double longVal = (double) snapshot.child("long").getValue(Double.class);

                    coordinates.clear();

                    coordinates.add(latVal);

                    coordinates.add(longVal);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        return coordinates;

    }



}
