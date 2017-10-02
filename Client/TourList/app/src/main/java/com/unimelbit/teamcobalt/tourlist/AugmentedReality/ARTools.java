package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.content.Context;
import android.location.LocationManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Hong Lin on 1/09/2017.
 */

public class ARTools {

    //Location provider and other utilities
    private FusedLocationProviderClient mFusedLocationClient;
    //Location requests
    private LocationRequest mLocationRequest;
    //Flag to see if location is being updated
    private Boolean mRequestingLocationUpdates = false;


    public ARTools(Context c){

        mFusedLocationClient = new FusedLocationProviderClient(c);

    }

    /*
    Check if the GPS is on
    */
    public boolean isGPSEnable(Context c){

        final Context con = c;

        LocationManager service = (LocationManager) con.getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            return false;
        }else{
            return true;
        }

    }


    /*
Location request settings.
 */
    public void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        //Can change interval depending on how much battery you want
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
        //Accuracy of location
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /*
    Get location requester
     */
    public LocationRequest getLocationRequest(){

        return this.mLocationRequest;

    }


    /*
    Get the location client
     */

    public FusedLocationProviderClient getLocationClient(){

        return this.mFusedLocationClient;

    }

    public Boolean isRequestingLocation(){

        return this.mRequestingLocationUpdates;

    }

    public void setmRequestingLocationUpdates(Boolean b){

        this.mRequestingLocationUpdates = b;

    }


}
