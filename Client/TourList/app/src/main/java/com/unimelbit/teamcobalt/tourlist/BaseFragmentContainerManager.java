package com.unimelbit.teamcobalt.tourlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.unimelbit.teamcobalt.tourlist.CreateTrips.CreateTripFragment;
import com.unimelbit.teamcobalt.tourlist.Search.SearchFragment;
import com.unimelbit.teamcobalt.tourlist.Search.SearchResultFragment;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.LoadingFragment;
import com.unimelbit.teamcobalt.tourlist.Trip.TabbedTripFragment;
import com.unimelbit.teamcobalt.tourlist.Trip.TripDetails;
import com.unimelbit.teamcobalt.tourlist.Trip.TripGetRequest;

/**
 * Created by Sebastian on 14/9/17.
 * A class to help prevent bloating of the BaseActivity
 * Manages the main fragment container of the base activity
 * Any fragment transaction on the main fragment container should be placed in this class.
 * It can then be called using baseActivity.getBaseFragmentContainerManager().gotoDemoMethod()
 */
public class BaseFragmentContainerManager {

    private BaseActivity baseActivity;
    private int containerId;

    BaseFragmentContainerManager(BaseActivity b, int id) {
        baseActivity = b;
        containerId = id;
    }

    public int getContainerId() {
        return containerId;
    }

    /**
     * Takes the user to trip details screen using a url
     */
    public void gotoTabbedTripFragment(String tripURL) {
        new TripGetRequest(tripURL, this);
    }

    /**
     * Takes the user to trip details screen using a trip object
     */
    public void gotoTabbedTripFragment(TripDetails trip) {
        baseActivity.setCurrentTrip(trip);

        TabbedTripFragment fragment = TabbedTripFragment.newInstance();
        gotoFragment(fragment);
    }

    /**
     * Takes the user to the search trips screen
     */
    public void gotoSearchFragment() {

        SearchFragment fragment = new SearchFragment();
        gotoFragmentUsingBackstack(fragment, null);
    }

    /**
     * Takes the user to the create trips screen
     */
    public void gotoCreateFragment() {
        CreateTripFragment fragment = new CreateTripFragment();
        gotoFragmentUsingBackstack(fragment, null);
    }

    /**
     * Takes the user to the start search result fragment and sends text over
     */
    public void gotoSearchResultFragment(String text) {

        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString(SearchResultFragment.ARG_TEXT, text);
        fragment.setArguments(args);
        gotoFragmentUsingBackstack(fragment, null);
    }

    /**
     * Start loading screen
     */
    public void gotoLoadingScreen(String loadingMsg) {
        LoadingFragment fragment = LoadingFragment.newInstance(loadingMsg);
        gotoFragment(fragment);
    }

    /**
     * Clears the main fragment container
     */
    public void clearFragmentContainer() {
        Fragment f = baseActivity.getSupportFragmentManager().findFragmentById(containerId);
        if (f!=null) {
            baseActivity.getSupportFragmentManager().beginTransaction().remove(f).commit();
        }
    }

    /**
     * Does a fragment transaction replace the current fragment, adds it to the backstack
     * @param fragment the fragment to replace the current fragment
     */
    private void gotoFragmentUsingBackstack(Fragment fragment, String backStackTag) {
        baseActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId,fragment)
                .addToBackStack(backStackTag)
                .commit();
    }

    /**
     * Does a fragment transaction replace the current fragment, doesn't add it to backstack
     * @param fragment the fragment to replace the current fragment
     */
    private void gotoFragment(Fragment fragment) {

        baseActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }
}
