package com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip;

import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sebastian on 10/10/17.
 */
public class NewTripSingleton {

    private boolean editTripFlag;
    public String id;
    public String name;
    public String description;
    public Integer day;
    public Integer month;
    public Integer year;
    public String date;
    public String cost;
    public String size;
    public String ownerId;
    public ArrayList<Location> locations;
    public ArrayList<Place> places;
    public ArrayList<String> usernames;
    public ArrayList userIds;

    public NewTripSingleton() {
        clearTrip();
    }

    void submitTrip(BaseActivity activity) {

        //Check if creating trip and need new arrays
        if (editTripFlag == false){
            usernames = new ArrayList<>();
             userIds = new ArrayList<>();
            usernames.add(BaseActivity.getCurrentUser().getUsername());
            userIds.add(BaseActivity.getCurrentUser().getId());
        }

        locations = Location.newLocationArrayFromPlaceArray(places);

        if(isValid()) {
            makeRequest(activity);
        } else {
            Toast.makeText(activity, "Please fill out form correctly", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeRequest(BaseActivity activity) {

        Trip newTrip = new Trip(id, name, description, getDateString(), cost, size, ownerId, locations, usernames, userIds, "");
        Toast.makeText(activity, newTrip.getUserids().toString(), Toast.LENGTH_LONG).show();
        if (editTripFlag) {
            //new CreateTripPostRequest(activity, newTrip);
            new EditTripPutRequest(activity, newTrip);
        } else {
            new CreateTripPostRequest(activity, newTrip);
        }

        clearTrip();
    }

    private boolean isValid() {
        boolean valid = name != null && description != null && cost != null && size != null;
        return valid;
    }

    private String getDateString() {

        if (date != null) {
            return date;
        } else if (day == null || month == null || year == null) {
            return "No date";
        } else {
            return day.toString() + " / " + month.toString() + " / " + year.toString();
        }
    }

    public boolean getEditTripFlag() {
        return editTripFlag;
    }

    public void editExistingTrip(Trip trip) {

        clearTrip();

        editTripFlag = true;

        id = trip.getId();
        name = trip.getName();
        description = trip.getDescription();
        date = trip.getDate();
        cost = trip.getCost();
        size = trip.getSize();
        ownerId = trip.getOwner();
        locations = trip.getLocations();
        usernames = trip.getUsernames();
        userIds = trip.getUserids();

        places = new ArrayList<>();
        for(Location loc: locations) {
            places.add(loc);
        }
    }

    public void clearTrip() {
        editTripFlag = false;
        id = null;
        name = null;
        description = null;
        day = null;
        month = null;
        year = null;
        cost = null;
        size = null;
        ownerId = null;
        locations = null;
        places = null;
        usernames = null;
        userIds = null;
    }


}
