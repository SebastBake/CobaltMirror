package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by spike on 8/9/2017.
 */
public class TripSearchResultFragment extends Fragment {

    public static String ARG_SEARCH_QUERY = "ARG_SEARCH_QUERY";
    private String searchQuery;

    private BaseFragmentContainerManager containerManager;

    public TripSearchResultFragment() {
    }

    public static TripSearchResultFragment newInstance(String text, BaseFragmentContainerManager containerManager) {

        TripSearchResultFragment fragment = new TripSearchResultFragment();

        Bundle args = new Bundle();
        args.putString(TripSearchResultFragment.ARG_SEARCH_QUERY, text);
        fragment.setArguments(args);
        fragment.setListener(containerManager);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            searchQuery = getArguments().getString(ARG_SEARCH_QUERY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_result, container, false);

        TextView textview = (TextView) rootView.findViewById(R.id.result_text);

        String header = getResources().getString(R.string.fragment_searchresults_header) + searchQuery;

        textview.setText(header);
        getActivity().setTitle(R.string.title_fragment_searchresults);

        containerManager.onCreatedView(this, rootView);

        return rootView;
    }

    public void showResultsList(ArrayList<Trip> trips, View rootView) {

        ListView resultsList = (ListView)rootView.findViewById(R.id.results_list);

        ArrayList<Map<String, String>> tripMaps = new ArrayList<>();
        for(Trip trip: trips) {
            tripMaps.add(trip.toMap());
        }

        ListAdapter adapter = new SimpleAdapter(
                getContext(),
                tripMaps,
                R.layout.fragment_search_results_items,
                new String[]{"name", "size", "cost", "locations"},
                new int[]{R.id.name, R.id.size, R.id.cost, R.id.locations}
        );

        resultsList.setAdapter(adapter);
    }

    private void setListener(BaseFragmentContainerManager containerManager) {
        this.containerManager = containerManager;
    }

    public interface onFragmentCreatedListener {
        void onCreatedView(TripSearchResultFragment fragment, View rootView);
    }
}
