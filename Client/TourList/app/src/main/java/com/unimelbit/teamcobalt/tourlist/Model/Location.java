package com.unimelbit.teamcobalt.tourlist.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Simple class to hold trip locations, can be constructed using JSON from the server
 * Also is parcelable and implements the Place interface for use with the google Places API
 */
public class Location implements Parcelable, Place {

    /**
     * required parcelable factory class
     */
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }

        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }
    };

    public static final String JSON_TITLE = "title";
    private static final String DEFAULT_DESC = "No description";
    private static final String JSON_ID = "_id";
    private static final String JSON_DESC = "description";
    private static final String JSON_LAT = "latitude";
    private static final String JSON_LON = "longitude";
    private static final String JSON_ALT = "altitude";
    private static final double DEFAULT_ALTITUDE = 100.0;

    private String id;
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private Double altitude;

    /**
     * Constructor
     */
    Location(
            String id,
            String title,
            String description,
            Double latitude,
            Double longitude,
            Double altitude
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     * construct from parcel
     */
    Location(Parcel parcel) {
        id = parcel.readString();
        title = parcel.readString();
        description = parcel.readString();
        latitude = parcel.readDouble();
        longitude = parcel.readDouble();
        altitude = parcel.readDouble();
    }

    /**
     * Factory method to make an array of Locations using JSON
     */
    public static ArrayList<Location> newLocationArrayFromJSON(JSONArray jsonArray) {

        ArrayList<Location> locations = new ArrayList<>();

        try {

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonLocation = jsonArray.getJSONObject(i);
                String id = "";
                String title = "";
                String description = "";
                Double latitude = 0.0;
                Double longitude = 0.0;
                Double altitude = 0.0;

                try {
                    id = jsonLocation.getString(JSON_ID);
                } catch (JSONException e) {
                }
                try {
                    title = jsonLocation.getString(JSON_TITLE);
                } catch (JSONException e) {
                }
                try {
                    description = jsonLocation.getString(JSON_DESC);
                } catch (JSONException e) {
                }
                try {
                    latitude = jsonLocation.getDouble(JSON_LAT);
                } catch (JSONException e) {
                }
                try {
                    longitude = jsonLocation.getDouble(JSON_LON);
                } catch (JSONException e) {
                }
                try {
                    altitude = jsonLocation.getDouble(JSON_ALT);
                } catch (JSONException e) {
                }

                locations.add(new Location(id, title, description, latitude, longitude, altitude));
            }

        } catch (JSONException e) {
        }

        return locations;
    }

    /**
     * Factory method to make an array of locations using an array of Places
     */
    public static ArrayList<Location> newLocationArrayFromPlaceArray(ArrayList<Place> placeArray) {

        ArrayList<Location> locations = new ArrayList<>();

        for (Place place : placeArray) {
            String id = "temp id"; // Temp id to post to server
            LatLng latLng = place.getLatLng();
            String title = place.getName().toString();
            String desc = DEFAULT_DESC;
            locations.add(new Location(id, title, desc, latLng.latitude, latLng.longitude, DEFAULT_ALTITUDE));
        }
        return locations;
    }

    /**
     * Generate a parcel
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(altitude);
    }

    /**
     * convert location to hashmap
     */
    public HashMap<String, String> toMap() {

        HashMap<String, String> map = new HashMap<>();

        map.put(JSON_ID, id);
        map.put(JSON_TITLE, title);
        map.put(JSON_DESC, description);
        map.put(JSON_ALT, altitude.toString());
        map.put(JSON_LAT, latitude.toString());
        map.put(JSON_LON, longitude.toString());

        return map;
    }

    /**
     * convert location to JSON
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject loc = new JSONObject();
        loc.put(JSON_ID, id);
        loc.put(JSON_TITLE, title);
        loc.put(JSON_DESC, description);
        loc.put(JSON_ALT, altitude.toString());
        loc.put(JSON_LAT, latitude.toString());
        loc.put(JSON_LON, longitude.toString());
        return loc;
    }

    /**
     * required place interface method
     */
    @Override
    public List<Integer> getPlaceTypes() {

        List<Integer> list = new ArrayList<>();
        list.add(Place.TYPE_GEOCODE);
        return list;
    }

    /**
     * required place interface method
     */
    @Override
    public CharSequence getAddress() {
        return null;
    }

    /**
     * required place interface method
     */
    @Override
    public Locale getLocale() {
        return null;
    }

    /**
     * required place interface method
     */
    @Override
    public CharSequence getName() {
        return getTitle();
    }

    /**
     * required place interface method
     */
    @Override
    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    /**
     * required place interface method
     */
    @Override
    public LatLngBounds getViewport() {
        return null;
    }

    /**
     * required place interface method
     */
    @Override
    public Uri getWebsiteUri() {
        return null;
    }

    /**
     * required place interface method
     */
    @Override
    public CharSequence getPhoneNumber() {
        return null;
    }

    /**
     * required place interface method
     */
    @Override
    public float getRating() {
        return 0;
    }

    /**
     * required place interface method
     */
    @Override
    public int getPriceLevel() {
        return 0;
    }

    /**
     * required place interface method
     */
    @Override
    public CharSequence getAttributions() {
        return null;
    }

    /**
     * required place interface method
     */
    @Override
    public Place freeze() {
        return null;
    }

    /**
     * required place interface method
     */
    @Override
    public boolean isDataValid() {
        return false;
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
    public Double getLatitude() {
        return latitude;
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
    public String getTitle() {
        return title;
    }

    /**
     * Simple getter
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Simple getter
     */
    public Double getAltitude() {
        return altitude;
    }

    /**
     * Required parcelable method
     */
    @Override
    public int describeContents() {
        return 0;
    }
}
