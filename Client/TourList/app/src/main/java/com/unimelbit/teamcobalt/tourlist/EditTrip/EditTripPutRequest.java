package com.unimelbit.teamcobalt.tourlist.EditTrip;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.CreateTrips.AddLocationsToTripActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequester;

import org.json.JSONException;

/**
 * Created by Sebast on 18/9/17.
 */
public class EditTripPutRequest implements PostRequest {

    private static final String CREATE_TRIP_URL = "https://cobaltwebserver.herokuapp.com/api/trips/create";

    EditTripLocationsActivity activity;
    Trip trip;

    EditTripPutRequest(EditTripLocationsActivity activity, Trip trip){

        this.activity = activity;
        this.trip = trip;

        new PostRequester(this).execute(CREATE_TRIP_URL);
    }

    @Override
    public void processResult(String result) {
        Toast.makeText(activity,"Result: " + result, Toast.LENGTH_SHORT).show();
        Log.e("Result:", result);
        Intent intent = new Intent(activity, BaseActivity.class);
        activity.startActivity(intent);
        activity.getSupportFragmentManager().popBackStack();
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
    }
}
