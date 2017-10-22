package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequestByID;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

/**
 * Displays the search results for trips as a list of trips
 */
public class TripSearchResultFragment extends Fragment{

    public static String ARG_SEARCH_QUERY = "ARG_SEARCH_QUERY";
    private String searchQuery;
    private onFragmentCreatedListener listener;
    private ListAdapter adapter;

    /**
     * Required public empty constructor
     */
    public TripSearchResultFragment() {
    }

    /**
     * Required factory method
     */
    public static TripSearchResultFragment newInstance(String text) {

        TripSearchResultFragment fragment = new TripSearchResultFragment();

        Bundle args = new Bundle();
        args.putString(TripSearchResultFragment.ARG_SEARCH_QUERY, text);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Required onCreate method
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            searchQuery = getArguments().getString(ARG_SEARCH_QUERY);
        }
    }

    /**
     * Inflate the fragment layout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_result, container, false);

        try {
            listener.onCreatedView(this, rootView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    /**
     * Displays the trips in the fragment as a simple listview through an adapter
     * @param trips
     * @param rootView
     * @throws JSONException
     */
    public void showResultsList(ArrayList<Trip> trips, View rootView) throws JSONException {

        TextView textview = (TextView) rootView.findViewById(R.id.result_text);

        String header = trips.size() + " " + getResources().getString(R.string.fragment_searchresults_header) + " " + searchQuery;

        textview.setText(header);
        getActivity().setTitle(R.string.title_fragment_searchresults);

        final EditText filter = (EditText) rootView.findViewById(R.id.searchFilter);
        final ListView resultsList = (ListView) rootView.findViewById(R.id.results_list);

        //Initialise the list of trips
        ArrayList<Map<String, String>> tripMaps = new ArrayList<>();
        for (Trip trip : trips) {
            tripMaps.add(trip.toMap());
        }

        //Set up the adapter to contain the results
        adapter = new SimpleAdapter(
                getContext(),
                tripMaps,
                R.layout.fragment_search_results_items,
                new String[]{Trip.JSON_NAME, Trip.JSON_ID},
                new int[]{R.id.name, R.id.ID}) {

            @Override
            public View getView(int position, final View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                Button b = (Button) v.findViewById(R.id.Go_to_trip);


                // On button click, go to trip details fragment.
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        RelativeLayout rl = (RelativeLayout) arg0.getParent();
                        TextView tv = (TextView) rl.findViewById(R.id.name);
                        TextView tripID = (TextView) rl.findViewById(R.id.ID);
                        String nameText = tv.getText().toString();
                        String idText = tripID.getText().toString();
                        Toast.makeText(getContext(), nameText, Toast.LENGTH_SHORT).show();
                        new TripGetRequestByID(idText, ((BaseActivity) getActivity()).getMainContainerManager());
                    }
                });

                return v;

            }
        };

        resultsList.setAdapter(adapter);

        // Text Filter for search results
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((SimpleAdapter) TripSearchResultFragment.this.adapter).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Uses the requester to set the trip search result list
     */
    public void setOnCreatedListener(TripSearchGetRequest request) {
        listener = request;
    }

    /**
     * Uses the requester to set the trip search result list
     */
    public interface onFragmentCreatedListener {
        void onCreatedView(TripSearchResultFragment fragment, View rootView) throws JSONException;
    }
}
