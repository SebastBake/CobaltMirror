package com.unimelbit.teamcobalt.tourlist.Trip;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.ARActivity;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.ARTools;
import com.unimelbit.teamcobalt.tourlist.AugmentedReality.AugmentedRealityActivity;
import com.unimelbit.teamcobalt.tourlist.CreateTrips.createActivity;
import com.unimelbit.teamcobalt.tourlist.R;

import static android.app.Activity.RESULT_OK;


public class TripDetailsFragment extends Fragment {

    public static final int TRIP_SECTION_INDEX = 0;
    private FloatingActionButton augmentedRealityButton;

    private FloatingActionButton locButton;

    private int PLACE_PICKER_REQUEST = 1;

    private ARTools arTool;


    public TripDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TripDetailsFragment newInstance() {
        TripDetailsFragment fragment = new TripDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trip_details, container, false);

        initAugmentedRealityButton(rootView);

        initLocButton(rootView);


        return rootView;
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
                Intent create = new Intent(getActivity(), createActivity.class);
                startActivity(create);
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


    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
        }else {

            //Intent for directing user to the location settings activity
            final Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;

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
