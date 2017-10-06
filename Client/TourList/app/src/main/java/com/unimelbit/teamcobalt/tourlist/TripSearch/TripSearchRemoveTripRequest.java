package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.util.Log;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;

import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequester;

import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;

/**
 * Created by spike on 16/9/2017.
 */

public class TripSearchRemoveTripRequest implements PutRequest {
    private static String LOADING_MSG = "Loading trips...";
    private static String URL_SEARCH_BASE = "https://cobaltwebserver.herokuapp.com/api/user/removetrip/";
    private String searchQuery;
    private String url;



    TripSearchRemoveTripRequest(String searchQuery,String username,String userid) throws JSONException {

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

        Log.e("Remove failed",msg);
        e.printStackTrace();
    }

}
