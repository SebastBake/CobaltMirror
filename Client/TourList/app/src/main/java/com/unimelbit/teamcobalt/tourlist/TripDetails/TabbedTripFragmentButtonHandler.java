package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Toast;

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
class TabbedTripFragmentButtonHandler implements TabLayout.OnTabSelectedListener  {

    private BaseActivity activity;
    private TabbedTripFragment fragment;

    private FloatingActionButton editButton;
    private FloatingActionButton augmentedRealityButton;
    private FloatingActionButton locButton;
    private FloatingActionButton mapButton;
    private FloatingActionButton mainButton;
    private boolean isMainFabActivated;

    TabbedTripFragmentButtonHandler(View rootView, BaseActivity activity, TabbedTripFragment fragment) {

        this.activity = activity;
        this.fragment = fragment;

        initButtons(rootView);
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

    private void setIsMainFabActivated(boolean isMainFabActivated) {
        this.isMainFabActivated = isMainFabActivated;
        if(isMainFabActivated) {
            showSmallButtons();
        } else {
            hideSmallButtons();
        }
    }

    private void resetLocSharingColor() {
        if(BaseActivity.isLocationSharingOn()) {

            ColorStateList greenColour = ColorStateList.valueOf(ResourcesCompat.getColor(fragment.getResources(), R.color.scheme1_green, null));
            locButton.setBackgroundTintList(greenColour);

        } else {

            ColorStateList redColour = ColorStateList.valueOf(ResourcesCompat.getColor(fragment.getResources(), R.color.scheme1_red, null));
            locButton.setBackgroundTintList(redColour);
        }
    }

    private void initButtons(View rootView) {

        editButton = (FloatingActionButton) rootView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                activity.getMainContainerManager().gotoEditTrip(BaseActivity.getCurrentTrip());
            }
        });

        locButton = (FloatingActionButton) rootView.findViewById(R.id.loc_button);
        resetLocSharingColor();

        final TabbedTripFragmentButtonHandler handler = this;

        locButton.setOnClickListener(new View.OnClickListener() {

            final BaseActivity baseActivity = activity;
            final TabbedTripFragmentButtonHandler from = handler;

            @Override
            public void onClick(View view) {
                baseActivity.toggleLocationSharing();
                from.resetLocSharingColor();
            }
        });

        mainButton = (FloatingActionButton) rootView.findViewById(R.id.main_button);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIsMainFabActivated(!isMainFabActivated);
            }
        });

        augmentedRealityButton = (FloatingActionButton) rootView.findViewById(R.id.ar_button);
        augmentedRealityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.startAR();
            }
        });

        mapButton = (FloatingActionButton) rootView.findViewById(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, MapActivity.class);
                fragment.startActivity(intent);
            }
        });
    }

    private void hideAllButtons() {
        editButton.setVisibility(View.GONE);
        locButton.setVisibility(View.GONE);
        augmentedRealityButton.setVisibility(View.GONE);
        mapButton.setVisibility(View.GONE);
        mainButton.setVisibility(View.GONE);
    }

    private void showAllButtons() {
        editButton.setVisibility(View.VISIBLE);
        locButton.setVisibility(View.VISIBLE);
        augmentedRealityButton.setVisibility(View.VISIBLE);
        mainButton.setVisibility(View.VISIBLE);
        mapButton.setVisibility(View.VISIBLE);
    }

    private void hideSmallButtons() {

        hideAllButtons();
        mainButton.setVisibility(View.VISIBLE);
    }

    private void showSmallButtons() {
        showAllButtons();
    }
}
