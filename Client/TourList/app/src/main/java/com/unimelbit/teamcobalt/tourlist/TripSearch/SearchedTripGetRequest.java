package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.util.Log;

import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;

import java.util.ArrayList;

/**
 * Created by spike on 22/9/2017.
 */

public class SearchedTripGetRequest implements GetRequest {
    public static final String DEFAULT_URL = "https://cobaltwebserver.herokuapp.com/api/trips/findbyid/";
    private static final String LOADING_MSG = "Loading trip...";

    private String url;
    private BaseFragmentContainerManager containerManager;

    public SearchedTripGetRequest(String query, BaseFragmentContainerManager containerManager) {

        this.url = DEFAULT_URL + query;
        this.containerManager = containerManager;

        // Start loading fragment
        containerManager.gotoLoadingFragment(LOADING_MSG);

        // Start get request
        new GetRequester(this).execute(url);
    }

    @Override
    public void processResult(String result) {

        try {
            ArrayList<Trip> trip = Trip.newTripArrayFromJSON(result, url);
            containerManager.gotoSearchedTripDetailsFragment(trip.get(0));
        } catch (Exception e) {
            requestFailed("Something failed for url: " + url + " and result: " + result, e);
        }
    }

    @Override
    public void requestFailed(String msg,Exception e) {

        Log.e("GetRequest failed",msg);
        e.printStackTrace();
        ErrorActivity.newError(containerManager.getBaseActivity(),e,"SearchedTripGetRequest failed: " + msg);
    }
}


