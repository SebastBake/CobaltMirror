package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;

/**
 * Created by Sebastian on 18/9/17.
 * Handler for the fabs on the tabbed trip fragment
 */
public class TabbedTripFragmentButtonHandler implements TabLayout.OnTabSelectedListener  {

    private BaseActivity activity;
    private TabbedTripFragment fragment;

    private FloatingActionButton augmentedRealityButton;
    private FloatingActionButton locButton;
    private FloatingActionButton mapButton;
    private FloatingActionButton mainButton;
    private boolean isMainFabActivated;

    public TabbedTripFragmentButtonHandler(View rootView, BaseActivity activity, TabbedTripFragment fragment) {

        this.activity = activity;
        this.fragment = fragment;

        initLocSharingButton(rootView);
        initAugmentedRealityButton(rootView);
        initMapButton(rootView);
        initMainButton(rootView);
        setIsMainFabActivated(false);
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


    public void setIsMainFabActivated(boolean isMainFabActivated) {
        this.isMainFabActivated = isMainFabActivated;
        if(isMainFabActivated) {
            showSmallButtons();
        } else {
            hideSmallButtons();
        }
    }

    private void initLocSharingButton(View rootView) {

        locButton = (FloatingActionButton) rootView.findViewById(R.id.loc_button);
        resetLocSharingColor();
        final TabbedTripFragmentButtonHandler handeler = this;

        FloatingActionButton.OnClickListener listener = new View.OnClickListener() {

            final BaseActivity baseActivity = activity;
            final TabbedTripFragmentButtonHandler from = handeler;

            @Override
            public void onClick(View view) {
                baseActivity.toggleLocationSharing();
                from.resetLocSharingColor();
            }
        };

        locButton.setOnClickListener(listener);
    }

    public void resetLocSharingColor() {
        if(activity.isLocationSharingOn()) {

            ColorStateList greenColour = ColorStateList.valueOf(ResourcesCompat.getColor(fragment.getResources(), R.color.scheme1_green, null));
            locButton.setBackgroundTintList(greenColour);

        } else {

            ColorStateList redColour = ColorStateList.valueOf(ResourcesCompat.getColor(fragment.getResources(), R.color.scheme1_red, null));
            locButton.setBackgroundTintList(redColour);
        }
    }


    private void initMapButton(View rootView) {

        mapButton = (FloatingActionButton) rootView.findViewById(R.id.map_button);

        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, MapActivity.class);

                ArrayList<Location> locations = BaseActivity.getCurrentTrip().getLocations();
                ArrayList<User> user = BaseActivity.getCurrentTrip().getUsers();

                intent.putParcelableArrayListExtra(Location.LOC_DEFAULT_PARCEL_KEY, locations);
                intent.putParcelableArrayListExtra(Trip.USERLIST_TRIPS, user);
                fragment.startActivity(intent);
            }
        });
    }

    private void initAugmentedRealityButton(View rootView) {

        augmentedRealityButton = (FloatingActionButton) rootView.findViewById(R.id.ar_button);

        augmentedRealityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.startAR();
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
}
