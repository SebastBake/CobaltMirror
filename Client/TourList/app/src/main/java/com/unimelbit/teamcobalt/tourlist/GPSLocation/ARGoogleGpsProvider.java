package com.unimelbit.teamcobalt.tourlist.GPSLocation;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.ARActivity;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Tracking.UserTracker;

/**
 * Created by Hong Lin on 5/10/2017.
 */

public class ARGoogleGpsProvider extends GoogleGpsProvider {


    private ARActivity arActivity;

    public ARGoogleGpsProvider(Context c) {
        super(c);

        arActivity = (ARActivity) c;
    }


    public void callback(){

        //Location to be sent to the view
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                //Loop through the results
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data

                    double latitude = location.getLatitude();

                    double longitude = location.getLongitude();

                    if (location != null && arActivity.getArchitectView() != null) {
                        // check if location has altitude at certain accuracy level & call right
                        // architect method (the one with altitude information)
                        if (location.hasAltitude() && location.hasAccuracy() && location.getAccuracy() < 7) {

                            arActivity.getArchitectView().setLocation(latitude,
                                    longitude, location.getAltitude(), location.getAccuracy());

                        } else {

                            arActivity.getArchitectView().setLocation(latitude,
                                    longitude, location.hasAccuracy() ? location.getAccuracy() : 1000);

                        }


                        postToFireBase(longitude, latitude);

                    }
                }
            };
        };



    }



}
