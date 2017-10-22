package com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip;

import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;

import java.util.ArrayList;

/**
 * Singleton to hold the data for a new or edited trip, then submits it via a post/put request
 */
public class NewTripSingleton {

    private boolean editTripFlag; // If a trip is being edited, this flag should be true
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

    /**
     * Initialises a new trip singleton
     */
    public NewTripSingleton() {
        clearTrip();
    }

    /**
     * Generates a request to update an edited trip, or create a trip
     */
    void submitTrip(BaseActivity activity) {

        //Check if creating trip and need new arrays
        if (!editTripFlag){
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

    /**
     * Starts the edit or create trip request
     */
    private void makeRequest(BaseActivity activity) {

        Trip newTrip = new Trip(id, name, description, getDateString(), cost, size, ownerId, locations, usernames, userIds, "");
        if (editTripFlag) {
            new EditTripPutRequest(activity, newTrip);
        } else {
            new CreateTripPostRequest(activity, newTrip);
        }

        clearTrip();
    }

    /**
     * Checks whether a request can be made
     */
    private boolean isValid() {
        boolean valid = name != null && description != null && cost != null && size != null;
        return valid;
    }

    /**
     * Converts the date into a string for a request to be made
     *
     * @return date string
     */
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

    /**
     * Sets a trip to be edited
     */
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

    /**
     * clear the data from the singleton
     */
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
