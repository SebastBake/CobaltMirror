package com.unimelbit.teamcobalt.tourlist.TripDetails;

<<<<<<< HEAD

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
=======
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
>>>>>>> MainPageTouchUp

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Location> locationList;

    private boolean isMapReady;

    private Handler handler;

    private Runnable runnableCode;

    private boolean reverse;

    private MarkerOptions markerOne, markerTwo, markerThree;

    private int count = 0;

    public static final int DEFAULT_ZOOM = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        locationList = getIntent().getParcelableArrayListExtra(Location.LOC_DEFAULT_PARCEL_KEY);

        isMapReady = false;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Tracking other users

        LatLng latLng = new LatLng(-37.79867463499714, 144.96722038839107);
        markerOne = new MarkerOptions().position(latLng).title("User 1")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        latLng = new LatLng(-37.79859709859357, 144.965807367073);
        markerThree = new MarkerOptions().position(latLng).title("User 1")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        latLng = new LatLng(-37.79840335143667, 144.9643450603271);
        markerTwo = new MarkerOptions().position(latLng).title("User 1")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        handler = new Handler();
        // Define the code block to be executed
        runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                if (isMapReady) {

                    if (count == 0) {
                        mMap.clear();
                        initLocationMarkers(locationList, mMap);

                        count++;
                        reverse = false;

                        mMap.addMarker(markerOne).showInfoWindow();

                        System.out.println("Marker 1");
                    } else if (count == 1) {
                        mMap.clear();
                        initLocationMarkers(locationList, mMap);

                        mMap.addMarker(markerTwo).showInfoWindow();

                        System.out.println("Markers 2");

                        count++;

                    }else{
                        mMap.clear();
                        initLocationMarkers(locationList, mMap);

                        mMap.addMarker(markerThree).showInfoWindow();

                        System.out.println("Markers 3");
                        count = 0;

                    }

                    System.out.println("Count: "+count);

                    Log.d("Handlers", "Called on main thread");
                    // Repeat this the same runnable code block again another 2 seconds
                    // 'this' is referencing the Runnable object
                    handler.postDelayed(this, 500);
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

<<<<<<< HEAD
        // Add locations to map
        for(Location location: locationList) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions marker = new MarkerOptions().position(latLng).title(location.getTitle());
            mMap.addMarker(marker);
        }
=======
        initLocationMarkers(this.locationList, this.mMap);
>>>>>>> MainPageTouchUp

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


    /*
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        if(!refreshLocs) {
            handler.post(runnableCode);
            refreshLocs = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        if(refreshLocs) {
            handler.removeCallbacks(runnableCode);
            refreshLocs = false;
        }
    }

    */

    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass method first

        handler.removeCallbacks(runnableCode);

    }




}


