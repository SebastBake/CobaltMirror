package com.unimelbit.teamcobalt.tourlist.TripDetails;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;
import java.util.HashMap;


public class TripDetailsFragment extends Fragment {

    public static final int TRIP_SECTION_INDEX = 0;

    private boolean imageLoaded;

    private ImageView imageDetail;

    private PlaceImageLoader pILoader;

    public TripDetailsFragment() {
    }

    public static TripDetailsFragment newInstance() {
        TripDetailsFragment fragment = new TripDetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trip_details, container, false);

        imageDetail = (ImageView) rootView.findViewById(R.id.imageView2);

        pILoader = new PlaceImageLoader(getActivity());

        if (((BaseActivity) getActivity()).getCurrentTrip() != null) {

            Trip currentTrip = ((BaseActivity) getActivity()).getCurrentTrip();
            initTextBoxes(rootView, currentTrip);
            initLocationsList(rootView, currentTrip);
            imageLoaded = false;


        } else {
            ((BaseActivity) getActivity()).getMainContainerManager().gotoErrorFragment("No current trip!");
        }

        return rootView;
    }


    private void initTextBoxes(View rootView, Trip trip) {

        TextView tripDescription = (TextView) rootView.findViewById(R.id.trip_details_description);
        tripDescription.setText(trip.getDescription());

        TextView tripCost = (TextView) rootView.findViewById(R.id.trip_details_cost);
        tripCost.setText("Cost: " + trip.getCost());

        TextView tripSize = (TextView) rootView.findViewById(R.id.trip_details_size);
        tripSize.setText("Max Size: " + trip.getSize());
    }

    private void initLocationsList(View rootView, Trip trip) {

        ListView listView = (ListView) rootView.findViewById(R.id.locations_list_view);

        ArrayList<HashMap<String, String>> locationsList = new ArrayList<>();
        for (Location loc : trip.getLocations()) {
            locationsList.add(loc.toMap());

            if (!imageLoaded) {

                System.out.println("long: " + loc.getLongitude().toString());
                System.out.println("lat: " + loc.getLatitude().toString());


                pILoader.setMapImage(loc.getLatitude().toString(), loc.getLongitude().toString(), imageDetail);

                imageLoaded = true;

            }
        }


        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                locationsList,
                R.layout.trip_details_locations_list_row,
                new String[]{Location.JSON_TITLE},
                new int[]{R.id.location_name});

        listView.setAdapter(adapter);
    }
}



