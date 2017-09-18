package com.unimelbit.teamcobalt.tourlist.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sebastian on 14/9/17.
 * Simple class to hold user data, can be constructed using JSON from the server
 * TODO: Figure out what to do with passwords
 */
public class User {

    public static final String JSON_USERNAME  = "username";
    public static final String JSON_PASSWORD  = "password";
    public static final String JSON_EMAIL  = "email";
    public static final String JSON_SAVEDTRIPS  = "savedtrips";
    public static final String JSON_FAVOURITETRIPS  = "favouritetrips";

    private String username ;
    private String password ;
    private String email ;
    private ArrayList<String> savedtrips ;
    private ArrayList<String> favouritetrips ;

    public User(
            String username,
            String password,
            String email,
            ArrayList<String> savedtrips,
            ArrayList<String> favouritetrips
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.savedtrips = savedtrips;
        this.favouritetrips = favouritetrips;
    }

    public static ArrayList<User> newUserArrayFromJSON(String result) throws JSONException {

        ArrayList<User> users = new ArrayList<>();
        JSONArray userJSONArray = new JSONArray(result);

        for (int i=0; i < userJSONArray.length(); i++) {

            JSONObject userJSON = userJSONArray.getJSONObject(i);

            String username = "";
            String password = "";
            String email = "";
            ArrayList<String> savedtrips = new ArrayList<>();
            ArrayList<String> favouritetrips = new ArrayList<>();


            try {
                username = userJSON.getString(JSON_USERNAME);
            } catch (JSONException e) {}

            try {
                password = userJSON.getString(JSON_PASSWORD);
            } catch (JSONException e) {}

            try {
                email = userJSON.getString(JSON_EMAIL);
            } catch (JSONException e) {}

            try {
                JSONArray jsonSavedTrips = userJSON.getJSONArray(JSON_SAVEDTRIPS);

                for (int j=0; j<jsonSavedTrips.length(); j++) {
                    savedtrips.add( jsonSavedTrips.getString(j) );
                }

            } catch(JSONException e) {}

            try {
                JSONArray jsonFavTrips = userJSON.getJSONArray(JSON_FAVOURITETRIPS);

                for (int j=0; j<jsonFavTrips.length(); j++) {
                    savedtrips.add( jsonFavTrips.getString(j) );
                }

            } catch(JSONException e) {}

            users.add(new User(username, password, email, savedtrips, favouritetrips) );
        }

        return users;
    }

    public String getUserRegistration() throws JSONException {
        JSONObject dataToSend = new JSONObject();
        dataToSend.put(User.JSON_USERNAME, username);
        dataToSend.put(User.JSON_PASSWORD, password);
        dataToSend.put(User.JSON_EMAIL, email);
        return dataToSend.toString();
    }

    public ArrayList<String> getSavedtrips() {
        return savedtrips;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public ArrayList<String> getFavouritetrips() {
        return favouritetrips;
    }
}
