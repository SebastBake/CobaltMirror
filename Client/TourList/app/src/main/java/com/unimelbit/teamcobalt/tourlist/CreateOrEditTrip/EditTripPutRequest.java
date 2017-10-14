package com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip;

import android.util.Log;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequester;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequester;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequest;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequestByID;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sebastian on 18/9/17.
 */
class EditTripPutRequest implements PutRequest {

    private static final String LOADING_MSG = "Editing trip ...";
    private static final String EDIT_TRIP_URL = "https://cobaltwebserver.herokuapp.com/api/trips/edit/";
    private static final int HTTP_ERROR_CODE = 404;

    BaseActivity activity;
    Trip trip;


    EditTripPutRequest(BaseActivity activity, Trip trip) {

        this.activity = activity;
        this.trip = trip;

        try {
            BaseActivity.setPutObject(new JSONObject(getDataToSend()));
        } catch (Exception e) {
            requestFailed("failed to set put object", e);
        }

        // Start loading fragment
        activity.getMainContainerManager().gotoLoadingFragment(LOADING_MSG);

        new PutRequester(this).execute(EDIT_TRIP_URL);
    }

    @Override
    public void processResult(String result,int status) {

        try {
            if (status == HTTP_ERROR_CODE) {
                throw new Exception();
            }
            new TripGetRequestByID(trip.getId(), activity.getMainContainerManager());
        } catch (Exception e) {
            requestFailed("Something failed for url: " + EDIT_TRIP_URL + " and result: " + result, e);
        }

        Log.e("Result:", result);
    }


    private String getDataToSend() {

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
        Log.e("EditTripPost failed", msg);
        e.printStackTrace();
        ErrorActivity.newError(activity,e, "Edit trip failed: " + msg + "\n Here's the exception: " + e.toString());
    }
}
