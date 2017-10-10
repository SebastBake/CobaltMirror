package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequest;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequestByID;

import java.util.ArrayList;

/**
 * Created by Sebastian on 10/10/17.
 */
class NewTripSingleton {

    static NewTripSingleton instance;

    public String id;
    public String name;
    public String description;
    public Integer day;
    public Integer month;
    public Integer year;
    public String cost;
    public String size;
    public String ownerId;
    public ArrayList<Location> locations;
    public ArrayList<Place> places;
    public ArrayList<String> usernames;
    public ArrayList userIds;

    NewTripSingleton() {
    }

    static NewTripSingleton getInstance() {
        if (instance == null) {
            instance = new NewTripSingleton();
        }
        return instance;
    }

    void submitNewTrip(BaseActivity activity) {

        if(isValid()) {
            Trip newTrip = new Trip(id, name, description, getDateString(), cost, size, ownerId, locations, usernames, userIds, "");
            new CreateTripPostRequest(activity, newTrip);
        } else {
            Toast.makeText(activity, "Please fill out form correctly", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid() {

        boolean valid = name != null && description != null && cost != null && size != null;

        return valid;
    }

    public String getDateString() {

        if (day == null || month == null || year == null) {
            return "No date";
        } else {
            return day.toString() + " / " + month.toString() + " / " + year.toString();
        }
    }
}
