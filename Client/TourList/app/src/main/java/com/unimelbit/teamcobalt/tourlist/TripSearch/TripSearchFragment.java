package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.unimelbit.teamcobalt.tourlist.BackButtonInterface;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

/**
 * Trip search fragment
 */
public class TripSearchFragment extends Fragment implements View.OnClickListener, BackButtonInterface {

    /**
     * Required public empty constructor
     */
    public TripSearchFragment() {
    }

    /**
     * Required factory method
     */
    public static TripSearchFragment newInstance() {
        TripSearchFragment fragment = new TripSearchFragment();
        return fragment;
    }

    /**
     * Inflates the fragment layout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        Button search = (Button) v.findViewById(R.id.Search_button);
        Button random = (Button) v.findViewById(R.id.Random_button);
        random.setOnClickListener(this);
        search.setOnClickListener(this);
        getActivity().setTitle(R.string.title_fragment_search);
        return v;
    }

    /**
     * Handle button click events
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            // Search database based on text entered
            case R.id.Search_button:
                EditText searchText = (EditText) getView().findViewById(R.id.Search_input);
                String text = searchText.getText().toString();
                new TripSearchGetRequest(text, ((BaseActivity) getActivity()).getMainContainerManager());
                break;

            // Search database for 10 random trips
            case R.id.Random_button:
                String randomtext = "Random_trips";
                new TripSearchGetRequest(randomtext, ((BaseActivity) getActivity()).getMainContainerManager());
                break;

            default:
                break;
        }
    }
}

