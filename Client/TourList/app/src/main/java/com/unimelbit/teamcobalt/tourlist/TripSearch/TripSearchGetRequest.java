package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.util.Log;
import android.view.View;

import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * A get request for trip search
 */

/**
 * Get requester for the searched trips
 */
public class TripSearchGetRequest implements GetRequest, TripSearchResultFragment.onFragmentCreatedListener {

    private static String LOADING_MSG = "Loading trips...";
    private static String URL_SEARCH_BASE = "https://cobaltwebserver.herokuapp.com/api/trips/search?searchcontent=";
    private static String URL_SEARCH_RANDOM = "https://cobaltwebserver.herokuapp.com/api/trips/findrandom";
    ArrayList<Trip> trips;
    private String searchQuery;
    private String url;
    private String random_url;
    private BaseFragmentContainerManager containerManager;

    /**
     * Initiate a search get request
     */
    TripSearchGetRequest(String searchQuery, BaseFragmentContainerManager containerManager) {

        this.searchQuery = searchQuery;
        this.url = URL_SEARCH_BASE + searchQuery;
        this.containerManager = containerManager;
        this.random_url = URL_SEARCH_RANDOM;

        // Start loading fragment
        containerManager.gotoLoadingFragment(LOADING_MSG);

        // Start get request
        if (searchQuery == "Random_trips") {
            new GetRequester(this).execute(random_url);
        } else {
            new GetRequester(this).execute(url);
        }
    }

    /**
     * Process the request result
     */
    @Override
    public void processResult(String result) {

        try {
            //Randomise the trips list
            if ( searchQuery == "Random_trips"){
                trips = Trip.newTripArrayFromJSON(result, random_url);
            } else {
                //Get an actual search
                trips = Trip.newTripArrayFromJSON(result, url);
            }
            containerManager.gotoTripSearchResultFragment(searchQuery, this);
        } catch (Exception e) {
            requestFailed("Something failed for url: " + url + " and result: " + result, e);
        }
    }

    /**
     * Handle request failure
     */
    @Override
    public void requestFailed(String msg, Exception e) {

        Log.e("TripGetRequest failed", msg);
        e.printStackTrace();
        ErrorActivity.newError(containerManager.getBaseActivity(), e, "TripGetRequest failed: " + msg);
    }

    /**
     * Show result list after result is retrieved
     */
    public void onCreatedView(TripSearchResultFragment fragment, View rootView) throws JSONException {
        fragment.showResultsList(trips, rootView);
    }
}
