package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequester;

import org.json.JSONException;

/**
 * Created by Sebast on 18/9/17.
 */

public class CreateTripPostRequest implements PostRequest {

    private static final String LOADING_MSG = "Creating trip ...";
    private static final String CREATE_TRIP_URL = "https://cobaltwebserver.herokuapp.com/api/trips/create";

    AddLocationsToTripActivity activity;
    Trip trip;

    CreateTripPostRequest(AddLocationsToTripActivity activity, Trip trip){

        this.activity = activity;
        this.trip = trip;

        // display loading message
        Toast.makeText(activity, LOADING_MSG, Toast.LENGTH_SHORT).show();
        try {
            Toast.makeText(activity, trip.toJSON().toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {}


        new PostRequester(this).execute(CREATE_TRIP_URL);
    }

    @Override
    public void processResult(String result) {
        Toast.makeText(activity,"Result: " + result, Toast.LENGTH_SHORT).show();
        Log.e("Result:", result);
        Intent intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
        activity.getSupportFragmentManager().popBackStack();

        //manager.gotoTripSearchFragment();
    }

    @Override
    public String getDataToSend() {

        String out = "";

        try {
            out = trip.toJSON().toString();
        } catch (JSONException e) {
            requestFailed("Failed to convert trip into JSON", e);
        }
        return out;
    }

    @Override
    public void requestFailed(String msg, Exception e) {
        Log.e("CreateTripPost failed", msg);
        e.printStackTrace();
        //manager.gotoErrorFragment("create trip failed: " + msg + "\n Here's the exception: " + e.toString());
    }
}
