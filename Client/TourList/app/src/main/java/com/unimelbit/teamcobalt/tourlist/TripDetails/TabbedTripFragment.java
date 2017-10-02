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

import com.unimelbit.teamcobalt.tourlist.AugmentedReality.ARActivity;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.ARTools;
import com.unimelbit.teamcobalt.tourlist.BackButtonInterface;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;

public class TabbedTripFragment extends Fragment implements BackButtonInterface{

    public static final int NUM_TABS = 2;

    public static final String INTENT_TRIPID = "com.example.spike.uitest.MESSAGE";

    private TripPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabbedTripFragmentButtonHandler buttonHandeler;

    private ARTools arTool;

    public TabbedTripFragment() {
    }

    public static TabbedTripFragment newInstance() {
        TabbedTripFragment fragment = new TabbedTripFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tabbed_trip, container, false);

        Trip currentTrip = ((BaseActivity)getActivity()).getCurrentTrip();
        String screenTitle = getResources().getText(R.string.title_fragment_current_trip) + ": ";
        getActivity().setTitle(screenTitle + currentTrip.getName() );

        // Initialise views
        buttonHandeler = new TabbedTripFragmentButtonHandler(rootView, (BaseActivity) getActivity(), this);
        initTabs(rootView);

        return rootView;
    }

    private void initTabs(View rootView) {

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new TripPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter. (View which holds the fragments)
        mViewPager = (ViewPager) rootView.findViewById(R.id.trip_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Link the pager to the tabs
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.trip_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(buttonHandeler);
    }


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

        arTool = new ARTools(getActivity());

        //Proceed and open activity if GPS is on
        if(arTool.isGPSEnable(getActivity())){

            Intent intent = new Intent(getActivity(), ARActivity.class);
            String id = ((BaseActivity)getActivity()).getCurrentTrip().getId();
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
}
