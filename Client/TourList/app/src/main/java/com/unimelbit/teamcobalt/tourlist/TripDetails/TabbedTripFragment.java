package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.sip.SipAudioCall;
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
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class TabbedTripFragment extends Fragment implements BackButtonInterface, TabLayout.OnTabSelectedListener {

    public static final int NUM_TABS = 2;

    private TripPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private ARTools arTool;
    private FloatingActionButton augmentedRealityButton;
    private FloatingActionButton locButton;
    private FloatingActionButton mapButton;
    private FloatingActionButton mainButton;
    private boolean isMainFabActivated;

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
        initTabs(rootView);
        initLocSharingButton(rootView);
        initAugmentedRealityButton(rootView);
        initMapButton(rootView);
        initMainButton(rootView);
        setIsMainFabActivated(false);

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
        tabLayout.addOnTabSelectedListener(this);
    }

    public void setIsMainFabActivated(boolean isMainFabActivated) {
        this.isMainFabActivated = isMainFabActivated;
        if(isMainFabActivated) {
            showSmallButtons();
        } else {
            hideSmallButtons();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getPosition() == TripDetailsFragment.TRIP_SECTION_INDEX) {
            showAllButtons();
            setIsMainFabActivated(false);
        } else {
            hideAllButtons();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
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

    private void initLocSharingButton(View rootView) {

        locButton = (FloatingActionButton) rootView.findViewById(R.id.loc_button);
        resetLocSharingColor();
        final TabbedTripFragment thisFragment = this;

        FloatingActionButton.OnClickListener listener = new View.OnClickListener() {

            final BaseActivity baseActivity = (BaseActivity)getActivity();
            final TabbedTripFragment from = thisFragment;

            @Override
            public void onClick(View view) {
                baseActivity.toggleLocationSharing();
                from.resetLocSharingColor();
            }
        };

        locButton.setOnClickListener(listener);
    }

    public void resetLocSharingColor() {
        if(((BaseActivity)getActivity()).isLocationSharingOn()) {

            ColorStateList greenColour = ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.scheme1_green, null));
            locButton.setBackgroundTintList(greenColour);

        } else {

            ColorStateList redColour = ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.scheme1_red, null));
            locButton.setBackgroundTintList(redColour);
        }
    }


    private void initMapButton(View rootView) {

        mapButton = (FloatingActionButton) rootView.findViewById(R.id.map_button);

        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MapActivity.class);
                ArrayList<Location> locations = ((BaseActivity)getActivity()).getCurrentTrip().getLocations();
                intent.putParcelableArrayListExtra(Location.LOC_DEFAULT_PARCEL_KEY, locations);
                startActivity(intent);
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

    private void initMainButton(View rootView) {
        mainButton = (FloatingActionButton) rootView.findViewById(R.id.main_button);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMainFabActivated) {
                    setIsMainFabActivated(!isMainFabActivated);
                } else {
                    setIsMainFabActivated(!isMainFabActivated);
                }
            }
        });
    }

    private void hideAllButtons() {
        locButton.setVisibility(View.GONE);
        augmentedRealityButton.setVisibility(View.GONE);
        mapButton.setVisibility(View.GONE);
        mainButton.setVisibility(View.GONE);
    }

    private void showAllButtons() {
        locButton.setVisibility(View.VISIBLE);
        augmentedRealityButton.setVisibility(View.VISIBLE);
        mainButton.setVisibility(View.VISIBLE);
        mapButton.setVisibility(View.VISIBLE);
    }

    private void hideSmallButtons() {

        locButton.setVisibility(View.GONE);
        augmentedRealityButton.setVisibility(View.GONE);
        mapButton.setVisibility(View.GONE);
    }

    private void showSmallButtons() {
        locButton.setVisibility(View.VISIBLE);
        augmentedRealityButton.setVisibility(View.VISIBLE);
        mapButton.setVisibility(View.VISIBLE);
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
