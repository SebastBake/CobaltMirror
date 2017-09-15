package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.util.Log;
import android.view.View;

import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;

import java.util.ArrayList;

/**
 * Created by Sebastian on 14/9/17.
 */
public class TripSearchGetRequest implements GetRequest, TripSearchResultFragment.onFragmentCreatedListener {

    private static String LOADING_MSG = "Loading trips...";
    private static String URL_SEARCH_BASE = "https://cobaltwebserver.herokuapp.com/api/locations/search?searchcontent=";
    private String searchQuery;
    private String url;
    private BaseFragmentContainerManager containerManager;
    ArrayList<Trip> trips;

    TripSearchGetRequest(String searchQuery, BaseFragmentContainerManager containerManager) {

        this.searchQuery = searchQuery;
        this.url = URL_SEARCH_BASE + searchQuery;
        this.containerManager = containerManager;

        // Start loading fragment
        containerManager.gotoLoadingFragment(LOADING_MSG);

        // Start get request
        new GetRequester(this).execute(url);
    }

    @Override
    public void processResult(String result) {

        try {
            trips = Trip.newTripArrayFromJSON(result, url);
            containerManager.gotoTripSearchResultFragment(searchQuery, this);
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

    public void onCreatedView(TripSearchResultFragment fragment,View rootView) {
        fragment.showResultsList(trips, rootView);
    }
}
