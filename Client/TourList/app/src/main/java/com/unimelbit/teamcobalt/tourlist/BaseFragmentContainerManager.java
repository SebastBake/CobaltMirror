package com.unimelbit.teamcobalt.tourlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.Chat.ChatListRoomFragment;
import com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip.TabbedCreateOrEditTripFragment;
import com.unimelbit.teamcobalt.tourlist.ErrorOrSuccess.ErrorActivity;
import com.unimelbit.teamcobalt.tourlist.Home.HomeFragment;
import com.unimelbit.teamcobalt.tourlist.Home.LoginFragment;
import com.unimelbit.teamcobalt.tourlist.Home.LoginOrRegisterFragment;
import com.unimelbit.teamcobalt.tourlist.Home.ProfileFragment;
import com.unimelbit.teamcobalt.tourlist.Home.ProfileTripsGetRequest;
import com.unimelbit.teamcobalt.tourlist.Home.RegisterFragment;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.ServerRequester.LoadingFragment;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TabbedTripFragment;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequest;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchFragment;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchGetRequest;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchResultFragment;

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
     * Takes the user to the register screen
     */
    public void gotoRegisterFragment() {

        RegisterFragment fragment = new RegisterFragment();
        gotoFragmentUsingBackstack(fragment);
    }

    /**
     * Takes the user to the register screen with network error message
     */
    public void gotoRegisterFragmentWithMessage(String msg) {

        RegisterFragment fragment = new RegisterFragment();
        Toast.makeText(baseActivity, msg, Toast.LENGTH_LONG).show();
        gotoFragmentUsingBackstack(fragment);
    }

    /**
     * Takes the user to the register screen
     */
    public void gotoLoginFragment() {

        LoginFragment fragment = new LoginFragment();
        gotoFragmentUsingBackstack(fragment);
    }

    /**
     * Takes the user to the login screen with a incorrect login message
     */
    public void gotoLoginFragmentWithMessage(String msg) {

        LoginFragment fragment = LoginFragment.newInstance();
        Toast.makeText(baseActivity, msg, Toast.LENGTH_LONG).show();
        gotoFragmentUsingBackstack(fragment);
    }

    /**
     * Takes the user to the profile screen
     */
    public void gotoProfileFragment(ProfileTripsGetRequest request) {

        ProfileFragment fragment = new ProfileFragment();
        fragment.setOnCreatedListener(request);
        gotoFragmentUsingBackstack(fragment);
    }

    /**
     * Takes the user to the home screen
     */
    public void gotoLoginOrRegisterFragment() {

        LoginOrRegisterFragment fragment = LoginOrRegisterFragment.newInstance();
        gotoFragmentUsingBackstack(fragment);
    }

    /**
     * Takes the user to the home screen
     */
    public void gotoLoginOrRegisterFragmentWithMessage(String msg) {

        LoginOrRegisterFragment fragment = LoginOrRegisterFragment.newInstance();
        Toast.makeText(baseActivity, msg, Toast.LENGTH_LONG).show();
        gotoFragmentUsingBackstack(fragment);
    }

    /**
     * Takes the user to the home screen
     */
    public void gotoHomeFragmentWithMessage(String msg) {

        HomeFragment fragment = HomeFragment.newInstance();
        Toast.makeText(baseActivity, msg, Toast.LENGTH_LONG).show();
        gotoFragmentUsingBackstack(fragment);
    }

    /**
     * Takes the user to the home screen
     */
    public void gotoHomeFragment() {

        HomeFragment fragment = HomeFragment.newInstance();
        gotoFragmentUsingBackstack(fragment);
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

        BaseActivity.setCurrentTrip(trip);
        TabbedTripFragment fragment = TabbedTripFragment.newInstance();
        gotoFragmentUsingBackstack(fragment);
    }

    /**
     * Takes the user to the search trips screen
     */
    public void gotoTripSearchFragment() {

        TripSearchFragment fragment = TripSearchFragment.newInstance();
        gotoFragmentUsingBackstack(fragment);
    }


    /**
     * Takes the user to the start search result fragment and sends text over
     */
    public void gotoTripSearchResultFragment(String text, TripSearchGetRequest request) {

        TripSearchResultFragment fragment = TripSearchResultFragment.newInstance(text);
        fragment.setOnCreatedListener(request);
        gotoFragmentUsingBackstack(fragment);
    }

    /**
     * Takes the user to the create trips screen
     */
    public void gotoCreateTrip() {

        try {
            AppServicesFactory.getServicesFactory().getNewTrip().clearTrip();
            TabbedCreateOrEditTripFragment fragment = TabbedCreateOrEditTripFragment.newInstance();
            gotoFragmentUsingBackstack(fragment);
        } catch (Exception e) {
            ErrorActivity.newError(baseActivity,e,"Failed to go to create trip.");
        }
    }

    /**
     * Takes the user to the edit trips screen
     */
    public void gotoEditTrip(Trip trip) {

        try {
            AppServicesFactory.getServicesFactory().getNewTrip().editExistingTrip(trip);
            TabbedCreateOrEditTripFragment fragment = TabbedCreateOrEditTripFragment.newInstance();
            gotoFragmentUsingBackstack(fragment);
        } catch (Exception e) {
            ErrorActivity.newError(baseActivity,e,"Failed to go to create trip.");
        }
    }

    /**
     * Start loading screen
     */
    public void gotoLoadingFragment(String loadingMsg) {

        LoadingFragment fragment = LoadingFragment.newInstance();
        LoadingFragment.setLoadingMsg(loadingMsg);
        gotoFragmentUsingBackstack(fragment);
    }


    public void goToChatRooms() {

        ChatListRoomFragment fragment = new ChatListRoomFragment();
        gotoFragmentUsingBackstack(fragment);
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
    private void gotoFragmentUsingBackstack(Fragment fragment) {
        replaceFragment(fragment);
    }


    /**
     * The bread and butter for transactions and backstacks
     */
    private void replaceFragment (Fragment fragment) {

        String backStateName = fragment.getClass().getName();

        FragmentManager manager = baseActivity.getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            manager.beginTransaction()
                    .replace(containerId, fragment)
                    .addToBackStack(backStateName)
                    .commit();
        }
    }

    public BaseActivity getBaseActivity(){
        return baseActivity;
    }
}
