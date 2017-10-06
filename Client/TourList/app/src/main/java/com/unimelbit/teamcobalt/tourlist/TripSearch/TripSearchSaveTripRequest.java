package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.util.Log;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequester;
;import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by spike on 16/9/2017.
 */

public class TripSearchSaveTripRequest implements PutRequest {

    private static String LOADING_MSG = "Loading trips...";
    private static String URL_SEARCH_BASE = "https://cobaltwebserver.herokuapp.com/api/user/addtrip/";
    private String searchQuery;
    private String url;



    TripSearchSaveTripRequest(String searchQuery, String username,String userid) throws JSONException {

        this.searchQuery = searchQuery;
        this.url = URL_SEARCH_BASE;
        JSONObject object = new JSONObject();
        object.put("tripid", searchQuery);
        object.put("userid",userid);
        object.put("username",username);
        BaseActivity.setPutObject(object);
        // Start get request
        new PutRequester(this).execute(url);
    }

    @Override
    public void processResult(String result) {
        try {
        } catch (Exception e) {
            requestFailed("Something failed for url: " + url + " and result: " + result, e);
        }
    }

    @Override
    public void requestFailed(String msg, Exception e) {

        Log.e("Save failed",msg);
        e.printStackTrace();
    }
}
