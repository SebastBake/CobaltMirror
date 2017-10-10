package com.unimelbit.teamcobalt.tourlist.GPSLocation;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.Tracking.UserTracker;

/**
 * Created by Hong Lin on 5/10/2017.
 */

/**
 * GPS tools used to provide location and utilised in Firebase.
 * This is for constantly updating the firebase with a user location and important for user tracking.
 */
public class FirebaseGoogleGpsProvider extends GoogleGpsProvider {


    public FirebaseGoogleGpsProvider(Context c) {
        super(c);
    }


    /**
     * Sends location constantly
     */
    public void callback(){

        //Location to be sent to the view
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                //Loop through the results
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    if (location != null) {

                        double latitude = location.getLatitude();

                        double longitude = location.getLongitude();

                        postToFireBase(latitude, longitude);


                    }
                }
            }

        };


    }

    /**
     * Stop tracking when called
     * @param user
     */
    public void stopTrack(User user){

        String userId = "USER_NOT_SET";

        if(user != null){

            userId = user.getId();

        }

        AppServicesFactory.getServicesFactory()
                .getFirebasePostRequester(c)
                .postToDb(UserTracker.NO_VALUE, UserTracker.NO_VALUE
                        , userId);

    }

}
