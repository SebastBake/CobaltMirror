package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequest;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by spike on 8/9/2017.
 */
public class TripSearchResultFragment extends Fragment {

    public static String ARG_SEARCH_QUERY = "ARG_SEARCH_QUERY";

    private String searchQuery;

    private onFragmentCreatedListener listener;

    public TripSearchResultFragment() {
    }

    public static TripSearchResultFragment newInstance(String text) {

        TripSearchResultFragment fragment = new TripSearchResultFragment();

        Bundle args = new Bundle();
        args.putString(TripSearchResultFragment.ARG_SEARCH_QUERY, text);
        fragment.setArguments(args);

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

        listener.onCreatedView(this, rootView);

        return rootView;
    }




    public void showResultsList(ArrayList<Trip> trips, View rootView) {

        TextView textview = (TextView) rootView.findViewById(R.id.result_text);

        String header = trips.size() + " " + getResources().getString(R.string.fragment_searchresults_header) + " " + searchQuery;

        textview.setText(header);
        getActivity().setTitle(R.string.title_fragment_searchresults);

        ListView resultsList = (ListView)rootView.findViewById(R.id.results_list);

        ArrayList<Map<String, String>> tripMaps = new ArrayList<>();
        for(Trip trip: trips) {
            tripMaps.add(trip.toMap());
        }

        ListAdapter adapter = new SimpleAdapter(
                getContext(),
                tripMaps,
                R.layout.fragment_search_results_items,
                new String[]{Trip.JSON_NAME, Trip.JSON_SIZE, Trip.JSON_COST, Trip.JSON_LOC},
                new int[]{R.id.name, R.id.size, R.id.cost, R.id.locations}
        ){
            @Override
            public View getView (int position, final View convertView, ViewGroup parent)
            {
                View v = super.getView(position, convertView, parent);

                Button b=(Button)v.findViewById(R.id.Go_to_trip);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        RelativeLayout rl = (RelativeLayout)arg0.getParent();
                        TextView tv = (TextView)rl.findViewById(R.id.name);
                        String text = tv.getText().toString();
                        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                        String url = "https://cobaltwebserver.herokuapp.com/api/trips/"+text;
                        new TripGetRequest(url, ((BaseActivity)getActivity()).getMainContainerManager());
                    }
                });
                return v;
            }


        };

        resultsList.setAdapter(adapter);
    }

    public void setOnCreatedListener(TripSearchGetRequest request) {
        listener = request;
    }

    public interface onFragmentCreatedListener {
        void onCreatedView(TripSearchResultFragment fragment, View rootView);
    }
}
