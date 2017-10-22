package com.unimelbit.teamcobalt.tourlist.TripDetails;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

/**
 * Class to handle the trip details fragment buttons to prevent bloating of the trip details fragment
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
    private boolean isUserInTrip;

    /**
     * Simple constructor
     */
    TabbedTripFragmentButtonHandler(View rootView, BaseActivity activity, TabbedTripFragment fragment) {

        this.activity = activity;
        this.fragment = fragment;

        initButtons(rootView);
        setIsMainFabActivated(false);

        isUserInTrip = BaseActivity.getCurrentTrip().getUserids().contains(BaseActivity.getCurrentUser().getId());
    }

    /**
     * Only show floating buttons on the trip details page
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getPosition() == TripDetailsFragment.TRIP_SECTION_INDEX) {
            showAllButtons();
            setIsMainFabActivated(false);
        } else {
            hideAllButtons();
        }
    }

    /**
     * Required by the tab listener interface
     */
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    /**
     * Required by the tab listener interface
     */
    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    /**
     * Activation of the main Fab shows the smaller buttons
     * Shows the buttons only when user is on the main fragment of the tabbed fragment
     */
    private void setIsMainFabActivated(boolean isMainFabActivated) {
        this.isMainFabActivated = isMainFabActivated;
        if(isMainFabActivated) {
            showSmallButtons();
        } else {
            hideSmallButtons();
        }
    }

    /**
     * Sets the location sharing button colour
     */
    private void resetLocSharingColor() {
        if(BaseActivity.isLocationSharingOn()) {

            ColorStateList greenColour = ColorStateList.valueOf(ResourcesCompat
                    .getColor(fragment.getResources(), R.color.scheme1_green, null));
            locButton.setBackgroundTintList(greenColour);

        } else {

            ColorStateList redColour = ColorStateList.valueOf(ResourcesCompat
                    .getColor(fragment.getResources(), R.color.scheme1_red, null));
            locButton.setBackgroundTintList(redColour);
        }
    }

    /**
     * Initialise buttons
     */
    private void initButtons(View rootView) {

        editButton = (FloatingActionButton) rootView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkUserInTrip()){
                    activity.getMainContainerManager().gotoEditTrip(BaseActivity.getCurrentTrip());
                }
            }
        });

        locButton = (FloatingActionButton) rootView.findViewById(R.id.loc_button);
        resetLocSharingColor();

        final TabbedTripFragmentButtonHandler handler = this;

        locButton.setOnClickListener(new View.OnClickListener() {

                final BaseActivity baseActivity = activity;
                final TabbedTripFragmentButtonHandler from = handler;

                @Override
                public void onClick (View view){
                    if (checkUserInTrip()) {
                        baseActivity.toggleLocationSharing();
                        from.resetLocSharingColor();
                    }
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
                if (checkUserInTrip()) {
                    Intent intent = new Intent(activity, MapActivity.class);
                    fragment.startActivity(intent);
                }
            }
        });
    }

    /**
     * Prevents a non-logged in user from entering chat
     */
    private boolean checkUserInTrip(){
        if(!isUserInTrip){
            Toast.makeText(activity,"Please save trip first",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    /**
     * Hides all of the buttons (when swiping to a different tab)
     */
    private void hideAllButtons() {
        editButton.setVisibility(View.GONE);
        locButton.setVisibility(View.GONE);
        augmentedRealityButton.setVisibility(View.GONE);
        mapButton.setVisibility(View.GONE);
        mainButton.setVisibility(View.GONE);
    }

    /**
     * Makes all of the buttons visible
     */
    private void showAllButtons() {
        editButton.setVisibility(View.VISIBLE);
        locButton.setVisibility(View.VISIBLE);
        augmentedRealityButton.setVisibility(View.VISIBLE);
        mainButton.setVisibility(View.VISIBLE);
        mapButton.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the smaller buttons
     */
    private void hideSmallButtons() {

        hideAllButtons();
        mainButton.setVisibility(View.VISIBLE);
    }

    /**
     * Shows the small buttons
     */
    private void showSmallButtons() {
        showAllButtons();
    }
}

