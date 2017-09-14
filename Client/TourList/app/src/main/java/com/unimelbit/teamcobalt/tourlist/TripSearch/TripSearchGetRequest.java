package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.util.Log;

import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;

import java.util.ArrayList;

/**
 * Created by Sebastian on 14/9/17.
 */
public class TripSearchGetRequest implements GetRequest {

    private static String LOADING_MSG = "Loading trips...";
    private static String URL_SEARCH_BASE = "https://cobaltwebserver.herokuapp.com/api/locations/search?searchcontent=";
    private String searchQuery;
    private String url;
    private BaseFragmentContainerManager containerManager;

    TripSearchGetRequest(String searchQuery, BaseFragmentContainerManager containerManager) {
        this.searchQuery = searchQuery;
        this.url = URL_SEARCH_BASE + searchQuery;
        this.containerManager = containerManager;

        containerManager.gotoLoadingFragment(LOADING_MSG);
        new GetRequester(this).execute(url);
    }

    @Override
    public void processResult(String result) {

        try {
            ArrayList<Trip> trips = Trip.newTripArray(result, url);
            containerManager.setTmpTrips(trips);
            containerManager.gotoTripSearchResultFragment(searchQuery);
        } catch (Exception e) {
            requestFailed("Something failed for url: " + url + " and result: " + result, e);
        }
    }

    @Override
    public void requestFailed(String msg, Exception e) {

        Log.e("TripGetRequest failed",msg);
        e.printStackTrace();
        containerManager.clearFragmentContainer();
        containerManager.gotoErrorFragment("TripGetRequest failed: " + msg);
    }
}
