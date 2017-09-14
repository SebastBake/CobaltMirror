package com.unimelbit.teamcobalt.tourlist.Trip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sebastian on 12/9/17.
 * Simple class to hold trip details, can be constructed using JSON from the server
 */
public class TripDetails {

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

    public TripDetails(String name, String cost, String size, String locations, String url) {
        this.name = name;
        this.cost = cost;
        this.size = size;
        this.locations = locations;
        this.url = url;
    }

    public TripDetails(String result, String url) throws JSONException {
        JSONArray tripJSON_array = new JSONArray(result);

        JSONObject tripJSON = tripJSON_array.getJSONObject(0);

        String name =  tripJSON.getString(JSON_NAME);
        String cost =  tripJSON.getString(JSON_COST);
        String size =  tripJSON.getString(JSON_SIZE);
        JSONArray locations = tripJSON.getJSONArray(JSON_LOC);
        String locations_titles = "";

        for (int j=0; j< locations.length();j++){
            JSONObject location = locations.getJSONObject(j);
            String title = location.getString(JSON_LOC_TITLE);
            locations_titles = locations_titles + title + "\n";
        }

        this.name = name;
        this.cost = cost;
        this.size = size;
        this.locations = locations_titles;
        this.url = url;
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
