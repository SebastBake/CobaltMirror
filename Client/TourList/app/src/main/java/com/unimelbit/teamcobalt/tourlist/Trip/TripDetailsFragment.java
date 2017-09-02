package com.unimelbit.teamcobalt.tourlist.Trip;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unimelbit.teamcobalt.tourlist.AugmentedReality.AugmentedRealityActivity;
import com.unimelbit.teamcobalt.tourlist.R;


public class TripDetailsFragment extends Fragment {

    public static final int TRIP_SECTION_INDEX = 0;
    private FloatingActionButton augmentedRealityButton;


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


        return rootView;
    }

    private void initAugmentedRealityButton(View rootView) {

        augmentedRealityButton = (FloatingActionButton) rootView.findViewById(R.id.ar_button);

        augmentedRealityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AugmentedRealityActivity.class);
                startActivity(intent);
            }
        });

    }
}
