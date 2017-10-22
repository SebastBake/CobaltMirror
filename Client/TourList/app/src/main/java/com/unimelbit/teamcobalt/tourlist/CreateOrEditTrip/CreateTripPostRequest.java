package com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip;

import android.util.Log;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.PostRequester;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequestByID;

import org.json.JSONException;

import static com.unimelbit.teamcobalt.tourlist.Model.Trip.newTripFromJSON;

/**
 * A post request to create a new trip
 */
class CreateTripPostRequest implements PostRequest {

    private static final String LOADING_MSG = "Creating trip ...";
    private static final String CREATE_FAIL = "CreateTripPost failed\n";
    private static final String JSON_FAIL = "Failed to convert trip into JSON";
    private static final String CREATE_TRIP_URL = "https://cobaltwebserver.herokuapp.com/api/trips/create";
    private static final int HTTP_SUCCESS_CODE = 200;

    private BaseActivity activity;
    private Trip trip;

    /**
     * Start a new request
     */
    CreateTripPostRequest(BaseActivity activity, Trip trip){

        this.activity = activity;
        this.trip = trip;

        // Start loading fragment
        activity.getMainContainerManager().gotoLoadingFragment(LOADING_MSG);

        // start request
        new PostRequester(this).execute(CREATE_TRIP_URL);
    }

    /**
     * Process the result of the request
     */
    @Override
    public void processResult(String result, int status) {

        try {

            // check for failure
            if (status != HTTP_SUCCESS_CODE ) {
                throw new Exception();
            }

            // If request was successful, go to the new trip
            Trip newTrip = newTripFromJSON(result, CREATE_TRIP_URL);
            new TripGetRequestByID(newTrip.getId(), activity.getMainContainerManager());

        } catch (Exception e) {
            requestFailed(result, e);
        }

        Log.e("Result:", result);
    }

    /**
     * Assemble body of post request - contains trip json
     *
     * @return the body of the post request - contains trip json
     */
    @Override
    public String getDataToSend() {

        String out = "";

        try {
            out = trip.toJSON().toString();
        } catch (JSONException e) {
            requestFailed(JSON_FAIL, e);
        }
        return out;
    }

    /**
     * Handle a failed request
     */
    @Override
    public void requestFailed(String msg, Exception e) {
        Log.e(CREATE_FAIL, msg);
        e.printStackTrace();
        ErrorActivity.newError(activity,e, CREATE_FAIL + msg);
    }
}
