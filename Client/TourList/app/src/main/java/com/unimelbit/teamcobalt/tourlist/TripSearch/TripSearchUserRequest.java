package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.util.Log;


import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;

import java.util.ArrayList;

/**
 * Created by spike on 16/9/2017.
 */

public class TripSearchUserRequest implements GetRequest {
    private static String LOADING_MSG = "Loading user...";
    private static String URL_USER_BASE = "https://cobaltwebserver.herokuapp.com/api/user/59bb1e098ea417001f71abaf";
    private String url;

    private BaseActivity activity;
    public User user;
    ArrayList<User> users;

    public TripSearchUserRequest(BaseActivity activity) {

        this.url = URL_USER_BASE;

        this.activity = activity;
        // Start get request
        new GetRequester(this).execute(url);
    }

    @Override
    public void processResult(String result) {

        try {
            users = User.newUserArrayFromJSON(result);
            user = users.get(0);
            activity.setCurrentUser(user);
            Log.i("User",activity.getCurrentUser().getUsername());
        } catch (Exception e) {
            requestFailed("Something failed for url: " + url + " and result: " + result, e);
        }

    }

    @Override
    public void requestFailed(String msg, Exception e) {

        Log.e("User Request failed",msg);
        e.printStackTrace();
    }



}
