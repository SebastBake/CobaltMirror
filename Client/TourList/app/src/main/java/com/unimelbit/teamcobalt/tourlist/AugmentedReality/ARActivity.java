package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TabbedTripFragment;
import com.wikitude.architect.ArchitectJavaScriptInterfaceListener;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import org.json.JSONException;
import org.json.JSONObject;

public class ARActivity extends AppCompatActivity {

    //SDK AR view
    private ArchitectView architectView;
    //AR tools including GPS and location services needed by AR sdk
    private ARTools arTool;
    //Listener for JSON objects from AR sdk
    private ARJSONListener arListener;
    //For callbacks
    private LocationCallback mLocationCallback;

    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        arTool = new ARTools(this);

        arListener = new ARJSONListener();

        //Get Trip id for AR
        id = getIntent().getStringExtra(TabbedTripFragment.INTENT_TRIPID);

        //Initialise the request
        arTool.createLocationRequest();

        //Initialise the AR framework
        architectView = (ArchitectView) this.findViewById(R.id.architectView);
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setFeatures(ArchitectStartupConfiguration.Features.Geo);
        config.setLicenseKey("FZbpQNvIcElXwHT+V0Gytj73ElrYHjl1sanA732esQSm1ZOI5T6rnlHy/o7fLuutcXBsKMGiLqwv14AdIs0/CW67b5fMViw5z+RHoy6FnWpHnLjXqw6goOhiH7MQrCYcarqJa4XnxvvClC1NRDJXWhmWeN9uK5h/rwu/hikTOwdTYWx0ZWRfX2wCy3ZjUdwWy5VYE8Vp0OY1MA/vXDelXQZpjOZWQVFQF4PCAJ9Hmb5ZQfIiOLyFgU4sfIS1ybteLCdagpNqCKPt7usuR29mcRz2oDKWTPpFW/YRlTKNVpEQcne+uT0NrJ5V/D72CEK+IFYxq21MsHxwoAyXv4QnlUWV/j14J31VFkL5/j3UQvPICQuwmT6zzVSH5y665onpY5boqt/AnSVFnIalB8SZj3gtADVzqAV20VHZz8NQZNsdIUq6gJA4puLLKcb9LtBuyD7pWQTbK0l/PEHdBii4Zo5D/WWATvy04C8p9xcXuoOMdPthtY8a5Wq+qEf/jS3RNjZtyFJMmhEqLiv1sx7z4myHPl8JX2E/lBQNf/pYf6mEzucGtZ+Uukz0unuO3g8Swfh1VHnThGQXsqVxhYap7uqQ+HXJU7OQbj+KQ9uCTyP6Glykx4cYmo6rsbcEwjDqzdPQN5B4jR1eSpfN0kyolG3ptGw83rtopY7bI43T2f04NrA1lcMgrJhqlsvMuEPN");
        architectView.onCreate(config);

        //Location to be sent to the view
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                //Loop through the results
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    if (location != null && ARActivity.this.architectView != null) {
                        Log.i("MY CURRENT LOCATION", String.valueOf(location));
                        // check if location has altitude at certain accuracy level & call right
                        // architect method (the one with altitude information)
                        if (location.hasAltitude() && location.hasAccuracy() && location.getAccuracy() < 7) {
                            ARActivity.this.architectView.setLocation(location.getLatitude(),
                                    location.getLongitude(), location.getAltitude(), location.getAccuracy());
                        } else {
                            ARActivity.this.architectView.setLocation(location.getLatitude(),
                                    location.getLongitude(), location.hasAccuracy() ? location.getAccuracy() : 1000);
                        }
                    }
                }
            };
        };

        // set JS interface listener, any calls made in JS like is forwarded to this listener, use this to interact between JS and native Android activity/fragment
        this.arListener.createListener(this);

        // set JS interface listener in architectView
        this.arListener.setListener(this.architectView);


    }




    /*
    Create view with the JS and assets provided in this function
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        architectView.onPostCreate();
        try {
            this.architectView.load("file:///android_asset/poi/index.html");
            this.architectView.callJavascript("World.newData('" + id +"')");
        } catch (Exception e) {

        }

    }

    /*
    Resume app settings
     */
    @Override
    protected void onResume() {
        super.onResume();
        architectView.onResume();
        if (!arTool.isRequestingLocation()) {
            startLocationUpdates();
        }

    }

    /*
    Starts requesting the location updates
     */
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        arTool.getLocationClient().requestLocationUpdates(arTool.getLocationRequest(),
                mLocationCallback,
                null);
    }

    /*
    Destroy activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        architectView.onDestroy();

    }

    /*
    Settings when acitivty paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        architectView.onPause();
        stopLocationUpdates();
        arTool.setmRequestingLocationUpdates(false);

    }

    /*
    Stop the location updates
     */
    private void stopLocationUpdates() {
        arTool.getLocationClient().removeLocationUpdates(mLocationCallback);
        arTool.setmRequestingLocationUpdates(false);

    }
}
