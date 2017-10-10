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

/**
 * Created by Hong Lin on 5/10/2017.
 */

/**
 * Handler class to be responsible for tracking activities called.
 * The functions will provide the ability to retrieve user locations and update the map live
 * as the coordinates are recived on a periodic basis.
 * It also manages markers to be placed on the Google Map.
 */
public class GoogleMapTrackingHandler {

    private Context c;
    private HashMap<String, UserTracker> users; // <userId, userName>
    private ArrayList<MarkerOptions> markerList;
    private ArrayList<Marker> markersOnMap;

    public GoogleMapTrackingHandler(Context c){
        this.c = c;
        users = new HashMap<>();
        markerList = new ArrayList<>();
        markersOnMap = new ArrayList<>();
    }

    /**
     * Create a user tracker object for the user
     * @param userName
     * @param c
     * @return
     */
    public UserTracker makeUserTracker(String userName, Context c){

        UserTracker userTracker = new UserTracker(c);
      
        //Create special icon for user
        Bitmap icon = userTracker.createUserIcon(userName);
        userTracker.setUserIcon(icon);
        return userTracker;
    }

    /**
     * Remove all markers that were added to the map
     * @param markers
     */
    public void removeUserMarkers(ArrayList<Marker> markers){

        for(Marker marker : markers) {
            marker.remove();
        }

        markers.clear();
    }

    /**
     * Get marker for user to use on map
     * @param userId
     * @return
     */
    public MarkerOptions getUserMarker(String userId, UserTracker coordinateRequester){

        ArrayList<Double> coordinates = coordinateRequester.getCoordinates(userId);

        //Only create the marker if the user has a location or has location enabled
        if(!coordinates.isEmpty() && coordinates.get(UserTracker.LAT_INDEX) != UserTracker.NO_VALUE) {

            LatLng latLng = new LatLng(coordinates.get(UserTracker.LAT_INDEX), coordinates.get(UserTracker.LONG_INDEX));

            //Get the custom icon to set to the marker
            Bitmap userIcon = coordinateRequester.getUserIcon();
            MarkerOptions marker = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(userIcon));

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
    public void getAllMarkers( HashMap<String, UserTracker> users, ArrayList<MarkerOptions> markerList){

        markerList.clear();

        //Iterate through all the users in the trip and create markers for them
        for (HashMap.Entry<String, UserTracker> entry : users.entrySet()) {

            MarkerOptions userMarker = getUserMarker(entry.getKey(), entry.getValue());

            //Add if user is allowing their info to be shared
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

    public void putIntoUserList(ArrayList<String> userIds, ArrayList<String> userNames) {

        for(int i=0; i<userIds.size(); i++) {

            users.put(userIds.get(i), makeUserTracker(userNames.get(i), c));

            Log.i("REEEEEEEEE", "Username: "+ makeUserTracker(userNames.get(i), c) );
            Log.i("REEEEEEEEE", "UserId: "+ userIds.get(i));
        }
    }

    public HashMap<String, UserTracker> getUserList() { return users; }
    public ArrayList<Marker> getMarkersOnMap() { return markersOnMap; }
    public ArrayList<MarkerOptions> getMarkerList() { return markerList; }
}
