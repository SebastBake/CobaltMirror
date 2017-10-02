package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompatBase;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.ARTools;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.Tracking.CoordinateDBPostRequester;

import java.util.ArrayList;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ArrayList<Location> locationList;

    private boolean isMapReady;

    private Handler handler;

    private Runnable runnableCode;

    private ARTools arTool;

    private LocationCallback mLocationCallback;

    private MarkerOptions markerOne, markerTwo, markerThree;

    private boolean locationSharing;

    private CoordinateDBPostRequester coordinateRequester;

    public static final int DEFAULT_ZOOM = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        locationList = getIntent().getParcelableArrayListExtra(Location.LOC_DEFAULT_PARCEL_KEY);

        isMapReady = false;

        locationSharing = getIntent().getExtras().getBoolean("location_sharing");

        coordinateRequester = AppServicesFactory.getServicesFactory().getFirebasePostRequester(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Tracking other users

        LatLng latLng = new LatLng(-37.79867463499714, 144.96722038839107);

        markerOne = new MarkerOptions().position(latLng).title("User 1")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        arTool = new ARTools(this);

        arTool.createLocationRequest();

        //Location to be sent to the view
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                //Loop through the results
                for (android.location.Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    if (location != null) {
                        Log.i("MY CURRENT LOCATION", String.valueOf(location));

                        if(locationSharing) {

                            AppServicesFactory.getServicesFactory()
                                    .getFirebasePostRequester(getApplicationContext())
                                    .postToDb(location.getLatitude(), location.getLongitude()
                                            , "TestUser");
                        }
                    }
                }
            }
        };

        handler = new Handler();
        // Define the code block to be executed
        runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                if (isMapReady) {

                        mMap.clear();
                        initLocationMarkers(locationList, mMap);

                        mMap.addMarker(markerOne).showInfoWindow();

                    Log.d("Handlers", "Called on main thread");
                    // Repeat this the same runnable code block again another 2 seconds
                    // 'this' is referencing the Runnable object
                    handler.postDelayed(this, 3000);
                }
            }
        };
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
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
        initLocationMarkers(this.locationList, this.mMap);

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


    /*
    Place the markers for each location
     */
    protected void initLocationMarkers(ArrayList<Location> locList, GoogleMap map){

        for(Location location: locList) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions marker = new MarkerOptions().position(latLng).title(location.getTitle());
            map.addMarker(marker);
        }


    }

    protected void onResume() {
        super.onResume();
        if (!arTool.isRequestingLocation()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
        arTool.setmRequestingLocationUpdates(false);

    }


    /*
    Starts requesting the location updates
     */
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        arTool.getLocationClient().requestLocationUpdates(arTool.getLocationRequest(),
                mLocationCallback,
                null);
    }

    /*
    Stop the location updates
     */
    private void stopLocationUpdates() {
        arTool.getLocationClient().removeLocationUpdates(mLocationCallback);
        arTool.setmRequestingLocationUpdates(false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass method first

        handler.removeCallbacks(runnableCode);

    }







}


