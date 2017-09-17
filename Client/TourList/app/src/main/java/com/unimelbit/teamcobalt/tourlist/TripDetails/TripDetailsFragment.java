package com.unimelbit.teamcobalt.tourlist.TripDetails;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.ARActivity;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.ARTools;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;

import static android.app.Activity.RESULT_OK;


public class TripDetailsFragment extends Fragment {

    public static final int TRIP_SECTION_INDEX = 0;

    private int PLACE_PICKER_REQUEST = 1;

    private ARTools arTool;

    private FloatingActionButton augmentedRealityButton;
    private FloatingActionButton locButton;

    public TripDetailsFragment() {
    }

    public static TripDetailsFragment newInstance() {
        TripDetailsFragment fragment = new TripDetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trip_details, container, false);

        if ( ((BaseActivity)getActivity()).getCurrentTrip() != null) {
            initTextBoxes(rootView, ((BaseActivity)getActivity()).getCurrentTrip());
        } else {
            ((BaseActivity)getActivity()).getMainContainerManager().gotoErrorFragment("No current trip!");
        }

        initAugmentedRealityButton(rootView);

        initLocButton(rootView);

        return rootView;
    }

    private void initTextBoxes(View rootView, Trip trip) {

        TextView tripDescription = (TextView)rootView.findViewById(R.id.trip_details_description);
        tripDescription.setText(trip.getDescription());

        TextView tripCost = (TextView)rootView.findViewById(R.id.trip_details_cost);
        tripCost.setText("Cost:" + trip.getCost());

        TextView tripSize = (TextView)rootView.findViewById(R.id.trip_details_size);
        tripSize.setText("Max Size: " + trip.getSize());
    }

    private void initAugmentedRealityButton(View rootView) {

        augmentedRealityButton = (FloatingActionButton) rootView.findViewById(R.id.ar_button);
        augmentedRealityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAR(view);
            }
        });

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

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == PLACE_PICKER_REQUEST){

            if(resultCode == RESULT_OK){

                //Place place = PlacePicker.getPlace(getActivity(), data);

            }
        }

    }

    /*
    Start AR Activity after checking if GPS is enabled. Display a message to enable the GPS if
    it is not on and take user to location services settings page.
     */
    public void startAR(View view) {

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
