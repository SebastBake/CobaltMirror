package com.unimelbit.teamcobalt.tourlist.Tracking;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unimelbit.teamcobalt.tourlist.Model.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hong Lin on 5/10/2017.
 */

public class GoogleMapTrackingHandler {

    private Context c;

    private HashMap<String, UserTracker> userList;

    private ArrayList<MarkerOptions> markerList;

    private ArrayList<Marker> markersOnMap;


    public GoogleMapTrackingHandler(Context c){

        this.c = c;

        userList = new HashMap<>();

        markerList = new ArrayList<MarkerOptions>();

        markersOnMap = new ArrayList<Marker>();

        userList.put("TestUser", makeUserTracker("TestUser", c));

    }


    /**
     * Create a user tracker object for the user
     * @param userName
     * @param c
     * @return
     */
    public UserTracker makeUserTracker(String userName, Context c){

        UserTracker userTracker = new UserTracker(c);

        Bitmap icon = userTracker.createUserIcon(userName);

        userTracker.setUserIcon(icon);

        return userTracker;
    }



    /**
     * Remove all markers that were added to the map
     * @param markers
     */
    public void removeUserMarkers(ArrayList<Marker> markers){

        for(Marker marker : markers){

            marker.remove();

        }

        markers.clear();

    }

    /**
     * Get marker for user to use on map
     * @param user
     * @return
     */
    public MarkerOptions getUserMarker(String user, UserTracker coordinateRequester){

        ArrayList<Double> coordinates = coordinateRequester.getCoordinates(user);

        //Only create the marker if the user has a location or has location enabled
        if(!coordinates.isEmpty() && coordinates.get(UserTracker.LAT_INDEX) != UserTracker.NO_VALUE) {

            LatLng latLng = new LatLng(coordinates.get(UserTracker.LAT_INDEX),
                    coordinates.get(UserTracker.LONG_INDEX));

            //Get the custom icon to set to the marker
            Bitmap userIcon = coordinateRequester.getUserIcon();

            MarkerOptions marker = new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(userIcon));

            return marker;
        }

        return null;

    }


    /**
     * Get a list of all markers based on the user array
     * @param users
     * @param markerList
     * @return
     */
    public void getAllMarkers(HashMap<String, UserTracker> users, ArrayList<MarkerOptions> markerList){

        markerList.clear();

        //Iterate through all the users in the trip and create markers for them
        for (Map.Entry<String, UserTracker> entry : users.entrySet()){

            String user = entry.getKey();

            UserTracker tracker = entry.getValue();

            MarkerOptions userMarker = getUserMarker(user, tracker);

            if(userMarker != null){

                markerList.add(userMarker);

            }


        }

    }


    /**
     * Add the markers to the map
     * @param markers
     * @param markersAdded
     * @param mMap
     */
    public void initUserMarkers(ArrayList<MarkerOptions> markers, ArrayList<Marker> markersAdded, GoogleMap mMap){

        for(MarkerOptions marker : markers){

            markersAdded.add(mMap.addMarker(marker));


        }
    }


    /**
     *Place the markers for each location
    */
    public void initLocationMarkers(ArrayList<Location> locList, GoogleMap map){

        for(Location location: locList) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions marker = new MarkerOptions().position(latLng).title(location.getTitle());
            map.addMarker(marker);
        }


    }


    public void putIntoUserList(String user){

        userList.put("TestUser", makeUserTracker("TestUser", c));


    }


    public HashMap<String, UserTracker> getUserList() {
        return userList;
    }

    public ArrayList<Marker> getMarkersOnMap() {
        return markersOnMap;
    }

    public ArrayList<MarkerOptions> getMarkerList() {
        return markerList;
    }
}
