package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.FirebaseGoogleGpsProbvider;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.GoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.Tracking.GoogleMapTrackingHandler;
import com.unimelbit.teamcobalt.tourlist.Tracking.UserTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ArrayList<Location> locationList;

    private boolean isMapReady;

    private Handler handler;

    private Runnable runnableCode;

    private GoogleGpsProvider gpsTool;

    private GoogleMapTrackingHandler mapHandler;

    public static final int DEFAULT_ZOOM = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        locationList = getIntent().getParcelableArrayListExtra(Location.LOC_DEFAULT_PARCEL_KEY);

        isMapReady = false;

        mapHandler = new GoogleMapTrackingHandler(this);

        mapHandler.putIntoUserList("TestUser");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gpsTool = AppServicesFactory.getServicesFactory().getFirebaseGpsProvider(this);

        gpsTool.createLocationRequest();

        gpsTool.setUser("TestUser");

        gpsTool.callback();

        handler = new Handler();
        // Define the code block to be executed
        runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                if (isMapReady) {

                    mapHandler.getAllMarkers(mapHandler.getUserList(), mapHandler.getMarkerList());

                    mapHandler.removeUserMarkers(mapHandler.getMarkersOnMap());

                    mapHandler.initUserMarkers(mapHandler.getMarkerList(), mapHandler.getMarkersOnMap(), mMap);

                    Log.d("Handlers", "Called on main thread");
                    // Repeat this the same runnable code block again another 2 seconds
                    // 'this' is referencing the Runnable object
                    handler.postDelayed(this, 1500);
                }
            }
        };
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);


    }

    /**
     * Manipulates the map once available.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add locations to map
        for(Location location: locationList) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions marker = new MarkerOptions().position(latLng).title(location.getTitle());
            mMap.addMarker(marker);
        }

        mapHandler.initLocationMarkers(this.locationList, this.mMap);

        // Zoom in on the first location
        LatLng fstLatLng = new LatLng(locationList.get(0).getLatitude(), locationList.get(0).getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(fstLatLng).zoom(DEFAULT_ZOOM).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //Show user location
        mMap.setMyLocationEnabled(true);

        //Show compass thingy
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.getUiSettings().setCompassEnabled(true);

        isMapReady = true;
    }




    protected void onResume() {
        super.onResume();
        if (!gpsTool.isRequestingLocation()) {
            gpsTool.startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        ((FirebaseGoogleGpsProbvider)gpsTool).stopTrack();

        gpsTool.stopLocationUpdates();
        gpsTool.setmRequestingLocationUpdates(false);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(runnableCode);

    }


}


