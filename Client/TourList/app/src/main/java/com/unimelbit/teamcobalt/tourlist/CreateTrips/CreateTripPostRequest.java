package com.unimelbit.teamcobalt.tourlist.CreateTrips;

import android.util.Log;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.SuccessActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequester;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequestByID;

import org.json.JSONException;

import static com.unimelbit.teamcobalt.tourlist.Model.Trip.newTripFromJSON;

/**
 * Created by Sebastian on 18/9/17.
 */
class CreateTripPostRequest implements PostRequest {

    private static final String LOADING_MSG = "Creating trip ...";
    private static final String CREATE_TRIP_URL = "https://cobaltwebserver.herokuapp.com/api/trips/create";

    BaseActivity activity;
    Trip trip;

    CreateTripPostRequest(BaseActivity activity, Trip trip){

        this.activity = activity;
        this.trip = trip;

        // Start loading fragment
        activity.getMainContainerManager().gotoLoadingFragment(LOADING_MSG);

        new PostRequester(this).execute(CREATE_TRIP_URL);
    }

    @Override
    public void processResult(String result) {

        try {
            Trip trip = newTripFromJSON(result, "");
            SuccessActivity.newSuccess(activity, "Successfully created new trip:" + trip.getName());
        } catch (Exception e) {
            requestFailed("Something failed for url: " + CREATE_TRIP_URL + " and result: " + result, e);
        }

        Log.e("Result:", result);
    }

    @Override
    public String getDataToSend() {

        String out = "";

        try {
            out = trip.toJSON().toString();
        } catch (JSONException e) {
            ErrorActivity.newError(activity, e, "Failed to convert trip into JSON");
        }
        return out;
    }

    @Override
    public void requestFailed(String msg, Exception e) {
        Log.e("CreateTripPost failed", msg);
        e.printStackTrace();
        ErrorActivity.newError(activity,e, "create trip failed: " + msg + "\n Here's the exception: " + e.toString());
    }
}
