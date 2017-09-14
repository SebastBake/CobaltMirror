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

    public static String JSON_NAME = "name";
    public static String JSON_COST = "name";
    public static String JSON_SIZE = "size";
    public static String JSON_LOC = "locations";
    public static String JSON_LOC_TITLE = "title";

    private String name;
    private String cost;
    private String size;
    private String locations;

    private String url;

    public Trip(String name, String cost, String size, String locations, String url) {
        this.name = name;
        this.cost = cost;
        this.size = size;
        this.locations = locations;
        this.url = url;
    }

    public static ArrayList<Trip> newTripArray(String result, String url) throws JSONException {

        ArrayList<Trip> trips = new ArrayList<>();
        JSONArray tripJSONArray = new JSONArray(result);

        for (int i=0; i < tripJSONArray.length(); i++) {

            JSONObject tripJSON = tripJSONArray.getJSONObject(i);
            String name = "";
            String cost = "";
            String size = "";
            String locations_titles = "";

            try {
                name =  tripJSON.getString(JSON_NAME);
            } catch (JSONException e) { }

            try {
                cost =  tripJSON.getString(JSON_COST);
            } catch (JSONException e) {}

            try {
                size =  tripJSON.getString(JSON_SIZE);
            } catch (JSONException e) {}

            try {
                JSONArray locations = tripJSON.getJSONArray(JSON_LOC);

                for (int j=0; j<locations.length();j++){

                    String title = "";

                    try {
                        JSONObject location = locations.getJSONObject(j);
                        title = location.getString(JSON_LOC_TITLE);
                    } catch (JSONException e) {}

                    locations_titles = locations_titles + title + "\n";
                }

            } catch(JSONException e) {}

            trips.add(new Trip(name, cost, size, locations_titles, url));
        }

        return trips;
    }

    public HashMap<String, String> toMap() {

        HashMap<String, String> map = new HashMap<>();

        map.put(JSON_NAME, name);
        map.put(JSON_COST, cost);
        map.put(JSON_SIZE, size);
        map.put(JSON_LOC, locations);

        return map;
    }

    public String getLocations() {
        return locations;
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
