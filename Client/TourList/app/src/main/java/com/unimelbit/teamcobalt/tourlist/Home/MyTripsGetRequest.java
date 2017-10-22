package com.unimelbit.teamcobalt.tourlist.Home;

import android.util.Log;
import android.view.View;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequest;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.GetRequester;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by awhite on 15/10/17.
 *
 * Gets the saved trips of the current user for the profile page
 */

public class MyTripsGetRequest implements GetRequest, MyTripsFragment.onFragmentCreatedListener {

    private static String LOADING_MSG = "Loading trips...";
    private static String URL_MY_TRIPS_BASE = "https://cobaltwebserver.herokuapp.com/api/user/savedtrips/";
    private String url;

    private BaseFragmentContainerManager containerManager;
    ArrayList<Trip> trips;

    public MyTripsGetRequest(BaseFragmentContainerManager containerManager) {

        this.url = URL_MY_TRIPS_BASE + BaseActivity.getCurrentUser().getId();
        this.containerManager = containerManager;

        // Start loading fragment
        containerManager.gotoLoadingFragment(LOADING_MSG);

        // Start get request
        new GetRequester(this).execute(url);
    }

    /**
     * Process the results from the request and interpret what to do with them
     * @param result
     */
    @Override
    public void processResult(String result) {

        //If the retrieval was successful, then all is fine. If not, it was a network error
        try {
            trips = Trip.newTripArrayFromJSON(result, url);
            containerManager.gotoMyTripsFragment(this);
        } catch (Exception e) {
            requestFailed("Something failed for url: " + url + " and result: " + result, e);
        }
    }

    /**
     * Have the user see an error if the trip request failed due to some network error
     * @param msg
     * @param e
     */
    @Override
    public void requestFailed(String msg, Exception e) {

        Log.e("TripGetRequest failed", msg);
        e.printStackTrace();
        ErrorActivity.newError(containerManager.getBaseActivity(),e,"TripGetRequest failed: " + msg);
    }

    @Override
    public void onCreatedView(MyTripsFragment fragment, View rootView) throws JSONException {
        fragment.showResultsList(trips, rootView);
    }

}
