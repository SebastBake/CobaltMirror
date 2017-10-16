package com.unimelbit.teamcobalt.tourlist.GPSLocation;

import android.content.Context;

/**
 * Created by Hong Lin on 5/10/2017.
 */

/**
 * Adaptor for services needed by the app.
 * Currently, only a Google services adaptor has been implemented.
 */
public interface GPSProviderAdaptor {

    //Check if the GPS is enabled
    boolean isGPSEnable(Context c);

    //Setup a way to request GPS location and have settings done
    void createLocationRequest();

    //Begin the location updates
    void startLocationUpdates();

    //Stop updating location
    void stopLocationUpdates();

    //Be able to callback and continuously send location
    void callback();


}
