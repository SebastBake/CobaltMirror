package com.unimelbit.teamcobalt.tourlist.TripDetails;


import android.util.Log;

import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;

import java.util.ArrayList;

/**
 * Initiates a get request to retrieve a trip from the server, and takes the user to the trip
 * details screen once that trip result is retrieved
 */
public class TripGetRequestByID implements GetRequest {

    public static final String DEFAULT_URL = "https://cobaltwebserver.herokuapp.com/api/trips/findbyid/";
    private static final String LOADING_MSG = "Loading trip...";
    private static final String REQUEST_FAILED_MESSAGE = "TripGetRequest failed\n";

    private String url;
    private BaseFragmentContainerManager containerManager;

    /**
     * Initiates a trip get request
     */
    public TripGetRequestByID(String query, BaseFragmentContainerManager containerManager) {

        this.url = DEFAULT_URL + query;
        this.containerManager = containerManager;

        // Start loading fragment
        containerManager.gotoLoadingFragment(LOADING_MSG);

        // Start get request
        new GetRequester(this).execute(url);
    }

    /**
     * Processes the result of the request
     */
    @Override
    public void processResult(String result) {

        try {
            ArrayList<Trip> trip = Trip.newTripArrayFromJSON(result, url);
            containerManager.gotoTabbedTripFragment(trip.get(0));
        } catch (Exception e) {
            requestFailed(result, e);
        }
    }

    /**
     * Handles request failure
     */
    @Override
    public void requestFailed(String msg,Exception e) {

        Log.e(REQUEST_FAILED_MESSAGE,msg);
        e.printStackTrace();
        ErrorActivity.newError(containerManager.getBaseActivity(),e,REQUEST_FAILED_MESSAGE + msg);
    }
}
