package com.unimelbit.teamcobalt.tourlist.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Simple class to hold user data, can be constructed using JSON from the server
 * Also parcelable
 */
public class User implements Parcelable {


    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static final String JSON_ID = "_id";
    public static final String JSON_USERNAME = "username";
    public static final String JSON_PASSWORD = "password";
    public static final String JSON_EMAIL = "email";
    public static final String JSON_SAVEDTRIPS = "savedtrips";
    public static final String JSON_FAVOURITETRIPS = "favouritetrips";

    private String id;
    private String username;
    private String password;
    private String email;
    private ArrayList<String> savedtrips;
    private ArrayList<String> favouritetrips;

    /**
     * Simple constructor
     */
    public User(
            String id,
            String username,
            String password,
            String email,
            ArrayList<String> savedtrips,
            ArrayList<String> favouritetrips
    ) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.savedtrips = savedtrips;
        this.favouritetrips = favouritetrips;
    }

    /**
     * Construct from parcel
     */
    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        password = in.readString();
        savedtrips = in.createStringArrayList();
        favouritetrips = in.createStringArrayList();
    }

    /**
     * Factory method to make a user from JSON
     */
    public static User newUserFromJSON(JSONObject userJSON) throws JSONException {

        String id = "";
        String username = "";
        String password = "";
        String email = "";
        ArrayList<String> savedtrips = new ArrayList<>();
        ArrayList<String> favouritetrips = new ArrayList<>();

        try {
            id = userJSON.getString(JSON_ID);
        } catch (JSONException e) {
        }
        try {
            username = userJSON.getString(JSON_USERNAME);
        } catch (JSONException e) {
        }

        try {
            password = userJSON.getString(JSON_PASSWORD);
        } catch (JSONException e) {
        }

        try {
            email = userJSON.getString(JSON_EMAIL);
        } catch (JSONException e) {
        }

        try {
            JSONArray jsonSavedTrips = userJSON.getJSONArray(JSON_SAVEDTRIPS);

            for (int j = 0; j < jsonSavedTrips.length(); j++) {
                savedtrips.add(jsonSavedTrips.getString(j));
            }

        } catch (JSONException e) {
        }

        try {
            JSONArray jsonFavTrips = userJSON.getJSONArray(JSON_FAVOURITETRIPS);

            for (int j = 0; j < jsonFavTrips.length(); j++) {
                favouritetrips.add(jsonFavTrips.getString(j));
            }

        } catch (JSONException e) {
        }

        return new User(id, username, password, email, savedtrips, favouritetrips);
    }

    /**
     * Factory method to make a user array from JSON
     */
    public static ArrayList<User> newUserArrayFromJSON(String result) throws Exception {

        ArrayList<User> users = new ArrayList<>();
        JSONArray userJSONArray = new JSONArray(result);

        for (int i = 0; i < userJSONArray.length(); i++) {
            try {
                users.add(newUserFromJSON(userJSONArray.getJSONObject(i)));
            } catch (Exception e) {
                throw e;
            }
        }

        return users;
    }

    /**
     * Convert to parcel
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeStringArray((String[]) savedtrips.toArray());
        parcel.writeStringArray((String[]) favouritetrips.toArray());
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
    public String getUsername() {
        return username;
    }

    /**
     * Simple getter
     */
    public String getPassword() {
        return password;
    }

    /**
     * Simple getter
     */
    public String getEmail() {
        return email;
    }

    /**
     * Simple getter
     */
    public ArrayList<String> getSavedtrips() {
        return savedtrips;
    }

    /**
     * Simple getter
     */
    public ArrayList<String> getFavouritetrips() {
        return favouritetrips;
    }

    /**
     * Required parcel method
     */
    @Override
    public int describeContents() {
        return 0;
    }
}