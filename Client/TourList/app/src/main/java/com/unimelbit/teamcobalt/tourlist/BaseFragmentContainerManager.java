package com.unimelbit.teamcobalt.tourlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.unimelbit.teamcobalt.tourlist.CreateTrips.CreateTripFragment;
import com.unimelbit.teamcobalt.tourlist.Home.HomeFragment;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchFragment;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchGetRequest;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchResultFragment;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.LoadingFragment;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TabbedTripFragment;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequest;

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
     * Takes the user to the home screen
     */
    public void gotoHomeFragment(User user) {

        baseActivity.setCurrentUser(user);
        gotoHomeFragment();
    }

    /**
     * Takes the user to the home screen
     */
    public void gotoHomeFragment() {

        HomeFragment fragment = HomeFragment.newInstance();
        gotoFragmentUsingBackstack(fragment, null);
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
    public void gotoTabbedTripFragment(Trip trip) {

        baseActivity.setCurrentTrip(trip);
        TabbedTripFragment fragment = TabbedTripFragment.newInstance();
        gotoFragmentUsingBackstack(fragment, null);
    }

    /**
     * Takes the user to the search trips screen
     */
    public void gotoTripSearchFragment() {

        TripSearchFragment fragment = TripSearchFragment.newInstance();
        gotoFragmentUsingBackstack(fragment, null);
    }

    /**
     * Takes the user to the start search result fragment and sends text over
     */
    public void gotoTripSearchResultFragment(String text, TripSearchGetRequest request) {

        TripSearchResultFragment fragment = TripSearchResultFragment.newInstance(text);
        fragment.setOnCreatedListener(request);
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
     * Start loading screen
     */
    public void gotoLoadingFragment(String loadingMsg) {

        baseActivity.setLoading(true);

        LoadingFragment fragment = LoadingFragment.newInstance(loadingMsg);
        gotoFragmentUsingBackstack(fragment, null);
    }

    /**
     * Takes the user to the error screen
     */
    public void gotoErrorFragment(String errMsg) {

        baseActivity.setLoading(true);

        ErrorFragment fragment = ErrorFragment.newInstance(errMsg);
        //gotoFragment(fragment);


        baseActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId,fragment)
                .addToBackStack(null)
                .commit();

/*
        final AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);

        //Dialogue to display
        final String message = "The Following Error has occurred:\n\n" + errMsg;

        //Direct user to location settings if they press OK, otherwise dismiss the display box
        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                //baseActivity.getSupportFragmentManager().popBackStackImmediate();
                                d.dismiss();
                            }
                        });
        builder.create().show();
        */
    }

    /**
     *
     * @return the current fragment in the main fragment container
     */
    public Fragment getCurrentFragment() {
        return baseActivity.getSupportFragmentManager().findFragmentById(containerId);
    }

    /**
     * Does a fragment transaction replace the current fragment, adds it to the backstack
     * @param fragment the fragment to replace the current fragment
     */
    private void gotoFragmentUsingBackstack(Fragment fragment, String backStackTag) {
        /*baseActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId,fragment)
                .addToBackStack(backStackTag)
                .commit();*/

        replaceFragment(fragment);
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


/*
The bread and butter for transactions and backstacks
 */
    private void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = baseActivity.getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(containerId, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public Context getBaseActivity(){
        return baseActivity;

    }

}
