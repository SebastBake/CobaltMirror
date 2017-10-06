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
    public static final String JSON_USERS_NAMES = "usernames";
    public static final String JSON_USERS_IDS = "userids";

    private String id;
    private String name;
    private String date;
    private String description;
    private String cost;
    private String size;
    private ArrayList<Location> locations;
    private ArrayList<String> usernames;
    private ArrayList<String> userids;

    private String url;

    public Trip(
            String id,
            String name,
            String description,
            String date,
            String cost,
            String size,
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
        this.locations = locations;
        this.usernames = usernames;
        this.userids = userids;
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
            ArrayList<String> usernames = new ArrayList<>();
            ArrayList<String> userids = new ArrayList<>();

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
            try {
                JSONArray jsonUsernames = tripJSON.getJSONArray(JSON_USERS_NAMES);
                for (int j=0; i<jsonUsernames.length();j++){
                    usernames.add(jsonUsernames.get(j).toString());
                }
            } catch(JSONException e) {}
            try {
                JSONArray jsonUserids = tripJSON.getJSONArray(JSON_USERS_IDS);
                for (int j=0; i<jsonUserids.length();j++){
                    userids.add(jsonUserids.get(j).toString());
                }
            } catch(JSONException e) {}

            trips.add(new Trip(id,name, description, date, cost, size, locations,usernames,userids, url));
        }

        return trips;
    }

    public HashMap<String, String> toMap() {

        HashMap<String, String> map = new HashMap<>();

        String locationString = "";
        for(Location location: locations) { locationString += location.getTitle() + "\n"; }
        String userString = "";
        for (String user : usernames){
            userString += user + "\n";
        }
        String useridString = "";
        for (String user : userids){
            userString += user + "\n";
        }



        map.put(JSON_ID,id);
        map.put(JSON_NAME, name);
        map.put(JSON_DATE, date);
        map.put(JSON_COST, cost);
        map.put(JSON_SIZE, size);
        map.put(JSON_DESC, description);
        map.put(JSON_USERS_NAMES,userString);
        map.put(JSON_USERS_IDS,useridString);
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

        JSONArray userJSONArray = new JSONArray();
        for(String user: usernames) {
            userJSONArray.put(user);
        }

        JSONArray useridJSONArray = new JSONArray();
        for(String user: usernames) {
            useridJSONArray.put(user);
        }

        trip.put(JSON_USERS_NAMES,userJSONArray);
        trip.put(JSON_LOC, locationJSONArray);
        trip.put(JSON_USERS_IDS,useridJSONArray);

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

    public ArrayList<String> getUsernames() {return usernames;}
    public ArrayList<String> getUserids() {return userids;}
}
