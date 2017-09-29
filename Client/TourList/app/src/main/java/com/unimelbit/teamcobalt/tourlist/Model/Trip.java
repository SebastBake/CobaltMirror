package com.unimelbit.teamcobalt.tourlist.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sebastian on 12/9/17.
 * Simple class to hold trip details, can be constructed using JSON from the server
 */
public class Trip {
    public static final String JSON_ID = "_id";
    public static final String JSON_NAME = "name";
    public static final String JSON_COST = "cost";
    public static final String JSON_SIZE = "size";
    public static final String JSON_DATE = "date";
    public static final String JSON_LOC = "locations";
    public static final String JSON_DESC = "description";

    private String id;
    private String name;
    private String date;
    private String description;
    private String cost;
    private String size;
    private ArrayList<Location> locations;

    private String url;

    public Trip(
            String id,
            String name,
            String description,
            String date,
            String cost,
            String size,
            ArrayList<Location> locations,
            String url
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.cost = cost;
        this.size = size;
        this.locations = locations;
        this.url = url;
    }

    public static ArrayList<Trip> newTripArrayFromJSON(String result, String url) throws JSONException {

        ArrayList<Trip> trips = new ArrayList<>();
        JSONArray tripJSONArray = new JSONArray(result);

        for (int i=0; i < tripJSONArray.length(); i++) {

            JSONObject tripJSON = tripJSONArray.getJSONObject(i);
            String id = "";
            String name = "";
            String cost = "";
            String size = "";
            String date = "";
            String description = "";
            ArrayList<Location> locations = new ArrayList<>();

            try {
                id = tripJSON.getString(JSON_ID);
            } catch (JSONException e) {}
            try {
                name = tripJSON.getString(JSON_NAME);
            } catch (JSONException e) {}

            try {
                date = tripJSON.getString(JSON_DATE);
            } catch (JSONException e) {}

            try {
                cost = tripJSON.getString(JSON_COST);
            } catch (JSONException e) {}

            try {
                size = tripJSON.getString(JSON_SIZE);
            } catch (JSONException e) {}

            try {
                description = tripJSON.getString(JSON_DESC);
            } catch (JSONException e) {}

            try {
                JSONArray jsonLocations = tripJSON.getJSONArray(JSON_LOC);
                locations = Location.newLocationArrayFromJSON(jsonLocations);
            } catch(JSONException e) {}

            trips.add(new Trip(id,name, description, date, cost, size, locations, url));
        }

        return trips;
    }

    public HashMap<String, String> toMap() {

        HashMap<String, String> map = new HashMap<>();

        String locationString = "";
        for(Location location: locations) { locationString += location.getTitle() + "\n"; }

        map.put(JSON_ID,id);
        map.put(JSON_NAME, name);
        map.put(JSON_DATE, date);
        map.put(JSON_COST, cost);
        map.put(JSON_SIZE, size);
        map.put(JSON_DESC, description);
        map.put(JSON_LOC, locationString);

        return map;
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject trip = new JSONObject();
        trip.put(JSON_ID, id);
        trip.put(JSON_NAME, name);
        trip.put(JSON_COST, cost);
        trip.put(JSON_DATE, date);
        trip.put(JSON_SIZE, size);
        trip.put(JSON_DESC, description);

        JSONArray locationJSONArray = new JSONArray();

        for(Location loc: locations) {
            locationJSONArray.put(loc.toJSON());
        }

        trip.put(JSON_LOC, locationJSONArray);

        return trip;
    }

    public String getId(){
       return id;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }

    public String getUrl() {
        return url;
    }
}
