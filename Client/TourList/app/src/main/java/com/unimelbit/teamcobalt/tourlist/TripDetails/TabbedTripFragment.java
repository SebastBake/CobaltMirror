package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.ARActivity;
import com.unimelbit.teamcobalt.tourlist.BackButtonInterface;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.GPSLocation.GoogleGpsProvider;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;

/**
 * Fragment that contains 2 tabbed fragments for trip details and chat
 */
public class TabbedTripFragment extends Fragment implements BackButtonInterface{

    public static final int NUM_TABS = 2;

    public static final String INTENT_TRIPID = "com.example.spike.uitest.MESSAGE";

    public TabbedTripFragment() {
    }

    public static TabbedTripFragment newInstance() {
        return new TabbedTripFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tabbed_trip, container, false);

        Trip currentTrip = BaseActivity.getCurrentTrip();
        getActivity().setTitle(currentTrip.getName() );
        initTabs(rootView);

        return rootView;
    }

    /**
     * Initates the tabs for the tabbed view
     * @param rootView
     */
    private void initTabs(View rootView) {

        TabbedTripFragmentButtonHandler buttonHandler = new TabbedTripFragmentButtonHandler(rootView,
                (BaseActivity) getActivity(), this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        TripPagerAdapter mSectionsPagerAdapter = new TripPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter. (View which holds the fragments)
        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.trip_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Link the pager to the tabs
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.trip_tabs);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(buttonHandler);
    }


    /**
     * This adapter will have the trip fragments
     */
    public class TripPagerAdapter extends FragmentPagerAdapter {

        public TripPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case TripDetailsFragment.TRIP_SECTION_INDEX:
                    return TripDetailsFragment.newInstance();
                case ChatFragment.TRIP_SECTION_INDEX:
                    return ChatFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_TABS;
        }

        // titles of the tabs
        @Override
        public CharSequence getPageTitle(int position) {

            Activity hostActivity = getActivity();

            if (hostActivity != null) {
                switch (position) {
                    case TripDetailsFragment.TRIP_SECTION_INDEX:
                        return hostActivity.getString(R.string.title_fragment_trip_details);
                    case ChatFragment.TRIP_SECTION_INDEX:
                        return hostActivity.getString(R.string.title_fragment_chat);
                }
            }
            return null;
        }
    }

    /**
     * Start AR Activity after checking if GPS is enabled. Display a message to enable the GPS if
     * it is not on and take user to location services settings page.
     */
    public void startAR() {

        GoogleGpsProvider gpsTool = AppServicesFactory.getServicesFactory().getFirebaseGpsProvider(getActivity());

        //Proceed and open activity if GPS is on
        if(gpsTool.isGPSEnable(getActivity())){

            Intent intent = new Intent(getActivity(), ARActivity.class);
            String id = BaseActivity.getCurrentTrip().getId();

            intent.putExtra(INTENT_TRIPID, id);

            startActivity(intent);

            //Notify the user to turn on the GPS settings
        } else {

            //Intent for directing user to the location settings activity
            final Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            //Dialogue to display
            final String message = "Please enable GPS before using AR functionality";

            //Direct user to location settings if they press OK, otherwise dismiss the display box
            builder.setMessage(message)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    startActivity(settingsIntent);
                                    d.dismiss();
                                }
                            })
                    //Do no nothing if user presses 'Cancel' and close dialogue
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    d.cancel();
                                }
                            });
            builder.create().show();

        }
    }

    @Override
    public void onDestroyView() {
        getActivity().findViewById(R.id.trip_tabs).setVisibility(View.GONE);
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        getActivity().findViewById(R.id.trip_tabs).setVisibility(View.GONE);
        super.onDetach();
    }
}