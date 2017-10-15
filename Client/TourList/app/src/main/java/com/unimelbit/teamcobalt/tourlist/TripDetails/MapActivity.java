package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.FirebaseGoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.GoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.Tracking.GoogleMapTrackingHandler;

import java.util.ArrayList;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Location> locationList;
    private boolean isMapReady;
    private Handler handler;
    private Runnable updateMarkerLocations;
    private GoogleGpsProvider gpsTool;
    private GoogleMapTrackingHandler mapHandler;

    public static final int DEFAULT_ZOOM = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        isMapReady = false;

        locationList = BaseActivity.getCurrentTrip().getLocations();
        ArrayList<String> userIdList = BaseActivity.getCurrentTrip().getUserids();
        ArrayList<String> userNameList = BaseActivity.getCurrentTrip().getUsernames();

        mapHandler = new GoogleMapTrackingHandler(this);
        mapHandler.putIntoUserList(userIdList, userNameList);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        gpsTool = AppServicesFactory.getServicesFactory().getFirebaseGpsProvider(this);
        gpsTool.createLocationRequest();
        gpsTool.callback();

        // Periodically update user markers
        setPeriodicTask();
        handler = new Handler();
        handler.post(updateMarkerLocations);
    }

    /**
     * Manipulates the map once available.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if(locationList.size() > 0) {
            addLocationToMap(locationList, mMap);
            mapHandler.initLocationMarkers(this.locationList, this.mMap);
            Location firstLocation = locationList.get(0);
            centerCameraGoogleMap(firstLocation, mMap, DEFAULT_ZOOM);
        }
        //transmit user location if allowed
        boolean permissionToTransmitUserLocation = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        if (permissionToTransmitUserLocation) {

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
        }

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
        ((FirebaseGoogleGpsProvider)gpsTool).stopTrack(BaseActivity.getCurrentUser());
        gpsTool.stopLocationUpdates();
        gpsTool.setmRequestingLocationUpdates(false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateMarkerLocations);
    }


    /**
     * Add locations from a list of lcoations to a given google map
     * @param locationList
     * @param mMap
     */
    public void addLocationToMap(ArrayList<Location> locationList, GoogleMap mMap){

        for(Location location: locationList) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions marker = new MarkerOptions().position(latLng).title(location.getTitle());
            mMap.addMarker(marker);
        }
    }


    /**
     * Center the google map on a given location
     * @param loc
     * @param mMap
     */
    public void centerCameraGoogleMap(Location loc, GoogleMap mMap, int zoom){

        // Zoom in on the given location
        LatLng fstLatLng = new LatLng(loc.getLatitude(), loc.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(fstLatLng).zoom(zoom).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * Periodically run the code to update markers on the map
     */
    private void setPeriodicTask(){

        final int DELAY = 1500;

        updateMarkerLocations = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                if (isMapReady) {

                    mapHandler.getAllMarkers(mapHandler.getUserList(), mapHandler.getMarkerList());
                    mapHandler.removeUserMarkers(mapHandler.getMarkersOnMap());
                    mapHandler.initUserMarkers(mapHandler.getMarkerList(), mapHandler.getMarkersOnMap(), mMap);

                    // Delay the task for 1.5 seconds
                    handler.postDelayed(this, DELAY);
                }
            }
        };
    }
}


