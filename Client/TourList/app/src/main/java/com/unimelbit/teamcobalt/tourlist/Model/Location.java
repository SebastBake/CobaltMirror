package com.unimelbit.teamcobalt.tourlist.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sebastian on 14/9/17.
 * Simple class to hold trip locations, can be constructed using JSON from the server
 */
public class Location {

    public static final String JSON_TITLE = "title";
    public static final String JSON_DESC = "Description";
    public static final String JSON_LAT = "latitude";
    public static final String JSON_LON = "longitude";
    public static final String JSON_ALT = "altitude";

    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private Double altitude;

    Location(
            String title,
            String description,
            Double latitude,
            Double longitude,
            Double altitude
            ) {

        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;

    }

    public static ArrayList<Location> newLocationArrayFromJSON(JSONArray jsonArray) {

        ArrayList<Location> locations = new ArrayList<>();

        try {

            for (int i=0; i<jsonArray.length();i++){

                JSONObject jsonLocation = jsonArray.getJSONObject(i);
                String title = "";
                String description = "";
                Double latitude = 0.0;
                Double longitude = 0.0;
                Double altitude = 0.0;

                try {
                    title = jsonLocation.getString(JSON_TITLE);
                } catch (JSONException e) {}
                try {
                    description = jsonLocation.getString(JSON_DESC);
                } catch (JSONException e) {}
                try {
                    latitude = jsonLocation.getDouble(JSON_LAT);
                } catch (JSONException e) {}
                try {
                    longitude = jsonLocation.getDouble(JSON_LON);
                } catch (JSONException e) {}
                try {
                    altitude = jsonLocation.getDouble(JSON_ALT);
                } catch (JSONException e) {}

                locations.add(new Location(title, description, latitude, longitude, altitude));
            }

        } catch(JSONException e) {}

        return locations;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getAltitude() {
        return altitude;
    }


}
