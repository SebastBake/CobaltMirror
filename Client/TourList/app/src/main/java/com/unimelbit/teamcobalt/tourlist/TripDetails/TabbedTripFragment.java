package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.ARActivity;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.ARTools;
import com.unimelbit.teamcobalt.tourlist.BackButtonInterface;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;

import static android.app.Activity.RESULT_OK;

public class TabbedTripFragment extends Fragment implements BackButtonInterface {

    private TripPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private int PLACE_PICKER_REQUEST = 1;

    private ARTools arTool;

    private FloatingActionButton augmentedRealityButton;
    private FloatingActionButton locButton;

    public TabbedTripFragment() {
    }

    public static TabbedTripFragment newInstance() {
        TabbedTripFragment fragment = new TabbedTripFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tabbed_trip, container, false);
        initTabs(rootView);

        Trip currentTrip = ((BaseActivity)getActivity()).getCurrentTrip();
        String screenTitle = getResources().getText(R.string.title_fragment_current_trip) + ": ";
        getActivity().setTitle(screenTitle + currentTrip.getName() );

        initAugmentedRealityButton(rootView);

        initLocButton(rootView);

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
            // Show 3 total pages on the tab bar
            return 2;
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


    private void initLocButton(View rootView) {

        locButton = (FloatingActionButton) rootView.findViewById(R.id.loc_button);

        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent = builder.build(getActivity());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initAugmentedRealityButton(View rootView) {
        augmentedRealityButton = (FloatingActionButton) rootView.findViewById(R.id.ar_button);
        augmentedRealityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAR();
            }
        });
    }


    // Not sure what this does?
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == PLACE_PICKER_REQUEST){

            if(resultCode == RESULT_OK){
                //Place place = PlacePicker.getPlace(getActivity(), data);
            }
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

            startActivity(new Intent(getActivity(), ARActivity.class));

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
