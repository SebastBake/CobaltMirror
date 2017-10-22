package com.unimelbit.teamcobalt.tourlist.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Simple class to hold trip data, can be constructed using JSON from the server
 * Also is parcelable\
 */
public class Trip implements Parcelable {

    /**
     * required parcel creator class
     */
    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public static final String JSON_ID = "_id";
    public static final String JSON_NAME = "name";
    public static final String JSON_COST = "cost";
    public static final String JSON_SIZE = "size";
    public static final String JSON_DATE = "date";
    public static final String JSON_LOC = "locations";
    public static final String JSON_DESC = "description";
    public static final String JSON_OWNER = "owner";
    public static final String JSON_USERS_NAMES = "usernames";
    public static final String JSON_USERS_IDS = "userids";
    public static final String USERLIST_TRIPS = "users_trip";

    private String id;
    private String name;
    private String date;
    private String description;
    private String cost;
    private String size;
    private String owner;
    private ArrayList<Location> locations;
    private ArrayList<String> usernames;
    private ArrayList<String> userids;
    private String url;

    /**
     * Simple constructor
     */
    public Trip(
            String id,
            String name,
            String description,
            String date,
            String cost,
            String size,
            String owner,
            ArrayList<Location> locations,
            ArrayList<String> usernames,
            ArrayList<String> userids,
            String url
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.cost = cost;
        this.size = size;
        this.owner = owner;
        this.locations = locations;
        this.usernames = usernames;
        this.userids = userids;
        this.url = url;
    }

    /**
     * Construct from parcel
     */
    protected Trip(Parcel in) {
        id = in.readString();
        name = in.readString();
        date = in.readString();
        description = in.readString();
        cost = in.readString();
        size = in.readString();
        owner = in.readString();
        locations = in.createTypedArrayList(Location.CREATOR);
        usernames = in.createStringArrayList();
        userids = in.createStringArrayList();
        url = in.readString();
    }

    /**
     * Factory method to make a Trip from JSON
     */
    public static Trip newTripFromJSON(String result, String url) throws JSONException {

        JSONObject tripJSON = new JSONObject(result);

        String id = "";
        String name = "";
        String cost = "";
        String size = "";
        String date = "";
        String description = "";
        String owner = "";
        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> userids = new ArrayList<>();

        try {
            id = tripJSON.getString(JSON_ID);
        } catch (JSONException e) {
        }
        try {
            name = tripJSON.getString(JSON_NAME);
        } catch (JSONException e) {
        }

        try {
            date = tripJSON.getString(JSON_DATE);
        } catch (JSONException e) {
        }

        try {
            cost = tripJSON.getString(JSON_COST);
        } catch (JSONException e) {
        }

        try {
            size = tripJSON.getString(JSON_SIZE);
        } catch (JSONException e) {
        }

        try {
            description = tripJSON.getString(JSON_DESC);
        } catch (JSONException e) {
        }

        try {
            owner = tripJSON.getString(JSON_OWNER);
        } catch (JSONException e) {
        }

        try {
            JSONArray jsonLocations = tripJSON.getJSONArray(JSON_LOC);
            locations = Location.newLocationArrayFromJSON(jsonLocations);
        } catch (JSONException e) {
        }
        try {
            JSONArray jsonUsernames = tripJSON.getJSONArray(JSON_USERS_NAMES);
            for (int j = 0; j < jsonUsernames.length(); j++) {
                usernames.add(jsonUsernames.get(j).toString());
            }
        } catch (JSONException e) {
        }
        try {
            JSONArray jsonUserids = tripJSON.getJSONArray(JSON_USERS_IDS);
            for (int j = 0; j < jsonUserids.length(); j++) {
                userids.add(jsonUserids.get(j).toString());
            }
        } catch (JSONException e) {
        }

        return new Trip(id, name, description, date, cost, size, owner, locations, usernames, userids, url);
    }

    /**
     * Factory method to make a Trip array from JSON
     */
    public static ArrayList<Trip> newTripArrayFromJSON(String result, String url) throws JSONException {

        ArrayList<Trip> trips = new ArrayList<>();
        JSONArray tripJSONArray = new JSONArray(result);

        for (int i = 0; i < tripJSONArray.length(); i++) {
            JSONObject tripJSON = tripJSONArray.getJSONObject(i);
            trips.add(newTripFromJSON(tripJSON.toString(), url));
        }

        return trips;
    }

    /**
     * Make a parcel
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(cost);
        dest.writeString(size);
        dest.writeString(owner);
        dest.writeTypedList(locations);
        dest.writeStringList(usernames);
        dest.writeStringList(userids);
        dest.writeString(url);
    }

    /**
     * Required parcel method
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Convert to hashmap
     */
    public HashMap<String, String> toMap() {

        HashMap<String, String> map = new HashMap<>();

        String locationString = "";
        for (Location location : locations) {
            locationString += location.getTitle() + "\n";
        }

        String userString = "";
        for (String user : usernames) {
            userString += user + "\n";
        }

        String useridString = "";
        for (String user : userids) {
            useridString += user + "\n";
        }

        map.put(JSON_ID, id);
        map.put(JSON_NAME, name);
        map.put(JSON_DATE, date);
        map.put(JSON_COST, cost);
        map.put(JSON_SIZE, size);
        map.put(JSON_DESC, description);
        map.put(JSON_OWNER, owner);
        map.put(JSON_USERS_NAMES, userString);
        map.put(JSON_USERS_IDS, useridString);
        map.put(JSON_LOC, locationString);

        return map;
    }

    /**
     * Convert to JSONObject
     */
    public JSONObject toJSON() throws JSONException {

        JSONObject trip = new JSONObject();
        trip.put(JSON_ID, id);
        trip.put(JSON_NAME, name);
        trip.put(JSON_COST, cost);
        trip.put(JSON_DATE, date);
        trip.put(JSON_SIZE, size);
        trip.put(JSON_DESC, description);
        trip.put(JSON_OWNER, owner);

        JSONArray locationJSONArray = new JSONArray();

        for (Location loc : locations) {
            locationJSONArray.put(loc.toJSON());
        }

        JSONArray userJSONArray = new JSONArray();
        for (String user : usernames) {
            userJSONArray.put(user);
        }

        JSONArray useridJSONArray = new JSONArray();
        for (String userid : userids) {
            useridJSONArray.put(userid);
        }

        trip.put(JSON_USERS_NAMES, userJSONArray);
        trip.put(JSON_LOC, locationJSONArray);
        trip.put(JSON_USERS_IDS, useridJSONArray);

        return trip;
    }

    /**
     * Simple getter
     */
    public String getId() {
        return id;
    }

    /**
     * Simple getter
     */
    public ArrayList<Location> getLocations() {
        return locations;
    }

    /**
     * Simple getter
     */
    public String getDescription() {
        return description;
    }

    /**
     * Simple getter
     */
    public String getSize() {
        return size;
    }

    /**
     * Simple getter
     */
    public String getName() {
        return name;
    }

    /**
     * Simple getter
     */
    public String getDate() {
        return date;
    }

    /**
     * Simple getter
     */
    public String getCost() {
        return cost;
    }

    /**
     * Simple getter
     */
    public String getUrl() {
        return url;
    }

    /**
     * Simple getter
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Simple getter
     */
    public ArrayList<String> getUsernames() {
        return usernames;
    }

    /**
     * Simple getter
     */
    public ArrayList<String> getUserids() {
        return userids;
    }
}
