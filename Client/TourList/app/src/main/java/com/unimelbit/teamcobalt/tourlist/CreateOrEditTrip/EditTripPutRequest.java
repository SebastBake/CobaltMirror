package com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip;

import android.util.Log;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PutRequester;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequestByID;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A http request to edit a trip
 */
class EditTripPutRequest implements PutRequest {

    private static final String LOADING_MSG = "Editing trip ...";
    private static final String PUT_OBJECT_FAILED = "failed to set put object";
    private static final String REQUEST_FAIL = "Edit trip failed\n";
    private static final String JSON_FAIL = "Failed to convert trip into JSON";
    private static final String EDIT_TRIP_URL = "https://cobaltwebserver.herokuapp.com/api/trips/edit/";
    private static final int HTTP_SUCCESS_CODE = 200;

    private BaseActivity activity;
    private Trip trip;

    /**
     * Start a new edit trip request
     */
    EditTripPutRequest(BaseActivity activity, Trip trip) {

        this.activity = activity;
        this.trip = trip;

        try {
            BaseActivity.setPutObject(new JSONObject(getDataToSend()));
        } catch (Exception e) {
            requestFailed(PUT_OBJECT_FAILED, e);
        }

        // Start loading fragment
        activity.getMainContainerManager().gotoLoadingFragment(LOADING_MSG);

        // start the request
        new PutRequester(this).execute(EDIT_TRIP_URL);
    }

    /**
     * Process the result of the request
     */
    @Override
    public void processResult(String result, int status) {

        try {

            // detect request failure
            if (status != HTTP_SUCCESS_CODE) {
                throw new Exception();
            }

            // go to edited trip
            new TripGetRequestByID(trip.getId(), activity.getMainContainerManager());

        } catch (Exception e) {
            requestFailed(result, e);
        }

        Log.e("Result:", result);
    }

    /**
     * Process the result of the request
     */
    private String getDataToSend() {

        String out = "";

        try {
            out = trip.toJSON().toString();
        } catch (JSONException e) {
            requestFailed(JSON_FAIL, e);
        }
        return out;
    }

    /**
     * Handle request failure
     */
    @Override
    public void requestFailed(String msg, Exception e) {
        Log.e(REQUEST_FAIL, msg);
        e.printStackTrace();
        ErrorActivity.newError(activity, e, REQUEST_FAIL + msg);
    }
}
