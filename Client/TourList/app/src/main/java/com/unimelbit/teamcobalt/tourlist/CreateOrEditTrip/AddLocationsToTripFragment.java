package com.unimelbit.teamcobalt.tourlist.CreateOrEditTrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 *
 */
public class AddLocationsToTripFragment extends Fragment {

    public static final int TAB_INDEX = 1;
    public static final String TAB_TITLE = "Locations";
    private static final int PLACE_PICKER_REQUEST = 1;
    private ArrayList<Place> placeArray;
    private CreateOrEditTripLocationListAdapter listAdapter;

    public AddLocationsToTripFragment() {
    }

    public static AddLocationsToTripFragment newInstance() {
        AddLocationsToTripFragment fragment = new AddLocationsToTripFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_locations_to_trip, container, false);

        NewTripSingleton newTrip = AppServicesFactory.getServicesFactory().getNewTrip();
        if (newTrip.getEditTripFlag()) {
            placeArray = newTrip.places;
        } else {
            placeArray = new ArrayList<>();
            newTrip.places = placeArray;
        }

        initButtons(rootView);
        initLocationsList(rootView);
        return rootView;
    }

    /**
     * Floating button that starts the Places intent to get locations to add
     */
    private void initButtons(View rootView) {

        Button addLocationButton = (Button) rootView.findViewById(R.id.add_location_button);
        addLocationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent = builder.build(getActivity());

                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initLocationsList(View rootView) {

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listAdapter = new CreateOrEditTripLocationListAdapter(getActivity(), R.layout.list_row_create_trip_location, placeArray);
        listView.setAdapter(listAdapter);
    }

    /**
     * Handles the Place objects returned from the Place intent and adds them into the location array
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

                // Add new place into place list
                Place location = PlacePicker.getPlace(getContext(), data);
                placeArray.add(location);
                listAdapter.notifyDataSetChanged();
            }
        }
    }
}
