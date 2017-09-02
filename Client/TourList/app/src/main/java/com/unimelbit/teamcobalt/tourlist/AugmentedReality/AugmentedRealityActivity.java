package com.unimelbit.teamcobalt.tourlist.AugmentedReality;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.unimelbit.teamcobalt.tourlist.R;
import com.wikitude.architect.*;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AugmentedRealityActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    //SDK AR view
    private ArchitectView architectView;

    //Location provider and other utilities
    private FusedLocationProviderClient mFusedLocationClient;
    //For callbacks
    private LocationCallback mLocationCallback;
    //Location requests
    private LocationRequest mLocationRequest;
    //Flag to see if location is being updated
    private Boolean mRequestingLocationUpdates = false;
    //Listener for interactions being sent to AR View
    protected ArchitectJavaScriptInterfaceListener mArchitectJavaScriptInterfaceListener;


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_augmented_reality);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //Initialise the request
        createLocationRequest();

        architectView = (ArchitectView) this.findViewById(R.id.architectView);
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setFeatures(ArchitectStartupConfiguration.Features.Geo);
        config.setLicenseKey(" FZbpQNvIcElXwHT+V0Gytj73ElrYHjl1sanA732esQSm1ZOI5T6rnlHy/o7fLuutcXBsKMGiLqwv14AdIs0/CW67b5fMViw5z+RHoy6FnWpHnLjXqw6goOhiH7MQrCYcarqJa4XnxvvClC1NRDJXWhmWeN9uK5h/rwu/hikTOwdTYWx0ZWRfX2wCy3ZjUdwWy5VYE8Vp0OY1MA/vXDelXQZpjOZWQVFQF4PCAJ9Hmb5ZQfIiOLyFgU4sfIS1ybteLCdagpNqCKPt7usuR29mcRz2oDKWTPpFW/YRlTKNVpEQcne+uT0NrJ5V/D72CEK+IFYxq21MsHxwoAyXv4QnlUWV/j14J31VFkL5/j3UQvPICQuwmT6zzVSH5y665onpY5boqt/AnSVFnIalB8SZj3gtADVzqAV20VHZz8NQZNsdIUq6gJA4puLLKcb9LtBuyD7pWQTbK0l/PEHdBii4Zo5D/WWATvy04C8p9xcXuoOMdPthtY8a5Wq+qEf/jS3RNjZtyFJMmhEqLiv1sx7z4myHPl8JX2E/lBQNf/pYf6mEzucGtZ+Uukz0unuO3g8Swfh1VHnThGQXsqVxhYap7uqQ+HXJU7OQbj+KQ9uCTyP6Glykx4cYmo6rsbcEwjDqzdPQN5B4jR1eSpfN0kyolG3ptGw83rtopY7bI43T2f04NrA1lcMgrJhqlsvMuEPN");
        architectView.onCreate(config);

        //Location to be sent to the view
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    if (location != null && AugmentedRealityActivity.this.architectView != null) {
                        Log.i("MY CURRENT LOCATION", String.valueOf(location));
                        // check if location has altitude at certain accuracy level & call right architect method (the one with altitude information)
                        if (location.hasAltitude() && location.hasAccuracy() && location.getAccuracy() < 7) {
                            AugmentedRealityActivity.this.architectView.setLocation(location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy());
                        } else {
                            AugmentedRealityActivity.this.architectView.setLocation(location.getLatitude(), location.getLongitude(), location.hasAccuracy() ? location.getAccuracy() : 1000);
                        }
                    }
                }
            };
        };

        // set JS interface listener, any calls made in JS like is forwarded to this listener, use this to interact between JS and native Android activity/fragment
        this.mArchitectJavaScriptInterfaceListener = this.getArchitectJavaScriptInterfaceListener();

        // set JS interface listener in architectView
        if (this.mArchitectJavaScriptInterfaceListener != null && this.architectView != null) {
            this.architectView.addArchitectJavaScriptInterfaceListener(mArchitectJavaScriptInterfaceListener);
        }

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);

        architectView.onPostCreate();

        try {
            this.architectView.load("file:///android_asset/poi/index.html");
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }



    @Override
    protected void onResume() {
        super.onResume();
        architectView.onResume();
        if (!mRequestingLocationUpdates) {
            startLocationUpdates();
        }

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
        architectView.onPause();
        stopLocationUpdates();
        mRequestingLocationUpdates = false;



    }

    /*
Location request settings.
 */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        //Can change interval depending on how much battery you want
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        //Accuracy of location
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    /*
    Listener to recieve JSON objects from the JS Architect View.
    Starts the activity with appropriate information from teh JSON object
     */
    public ArchitectJavaScriptInterfaceListener getArchitectJavaScriptInterfaceListener() {
        return new ArchitectJavaScriptInterfaceListener() {
            @Override
            public void onJSONObjectReceived(JSONObject jsonObject) {
                try {
                    switch (jsonObject.getString("action")) {

                        //JSON from pressing 'More' on the AR View
                        case "present_poi_details":
                            //Send details and intiate the details activity
                           /* final Intent poiDetailIntent = new Intent(ARActivity.this, POIDetailActivity.class);
                            poiDetailIntent.putExtra(POIDetailActivity.EXTRAS_KEY_POI_ID, jsonObject.getString("id"));
                            poiDetailIntent.putExtra(POIDetailActivity.EXTRAS_KEY_POI_TITILE, jsonObject.getString("title"));
                            poiDetailIntent.putExtra(POIDetailActivity.EXTRAS_KEY_POI_DESCR, jsonObject.getString("description"));
                            //POI detail activity
                            ARActivity.this.startActivity(poiDetailIntent);*/
                            break;

                    }
                }catch (JSONException e) {
                    // Log.e(TAG, "onJSONObjectReceived: ", e);
                }


            }
        };
    }

    /*
 Starts requesting the location updates
  */
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null);
    }


    /*
Stop the location updates
 */
    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        mRequestingLocationUpdates = false;

    }


}

