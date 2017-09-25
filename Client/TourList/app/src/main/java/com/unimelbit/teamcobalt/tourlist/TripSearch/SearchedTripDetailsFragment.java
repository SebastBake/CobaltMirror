package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.unimelbit.teamcobalt.tourlist.BackButtonInterface;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Location;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.TripDetails.PlaceImageLoader;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchedTripDetailsFragment extends Fragment implements BackButtonInterface {

    public static final int TRIP_SECTION_INDEX = 0;

    private boolean imageLoaded;

    private ImageView imageDetail;

    private PlaceImageLoader pILoader;

    public SearchedTripDetailsFragment() {
    }

    public static SearchedTripDetailsFragment newInstance() {
        SearchedTripDetailsFragment fragment = new SearchedTripDetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trip_details, container, false);

        imageDetail = (ImageView) rootView.findViewById(R.id.imageView2);

        pILoader = new PlaceImageLoader(getActivity());

        if (((BaseActivity) getActivity()).getSearchedTrip() != null) {

            Trip currentTrip = ((BaseActivity) getActivity()).getSearchedTrip();
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

            // Add screenshot of the location on the map into the trip details image
            if (!imageLoaded) {

                // System.out.println("long: " + loc.getLongitude().toString());
                // System.out.println("lat: " + loc.getLatitude().toString());

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


