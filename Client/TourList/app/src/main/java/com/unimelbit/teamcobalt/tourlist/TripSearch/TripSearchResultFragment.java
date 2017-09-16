package com.unimelbit.teamcobalt.tourlist.TripSearch;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.BaseFragmentContainerManager;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by spike on 8/9/2017.
 */
public class TripSearchResultFragment extends Fragment {

    public static String ARG_SEARCH_QUERY = "ARG_SEARCH_QUERY";

    private String searchQuery;



    private onFragmentCreatedListener listener;
    private SimpleAdapter adapter;

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

        try {
            listener.onCreatedView (this, rootView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }




    public void showResultsList(ArrayList<Trip> trips, View rootView) throws JSONException {

        TextView textview = (TextView) rootView.findViewById(R.id.result_text);

        String header = trips.size() + " " + getResources().getString(R.string.fragment_searchresults_header) + " " + searchQuery;

        new TripSearchUserRequest(((BaseActivity)getActivity()));

        textview.setText(header);
        getActivity().setTitle(R.string.title_fragment_searchresults);
        final EditText filter = (EditText) rootView.findViewById(R.id.searchFilter);
        final ListView resultsList = (ListView)rootView.findViewById(R.id.results_list);

        ArrayList<Map<String, String>> tripMaps = new ArrayList<>();
        for(Trip trip: trips) {
            tripMaps.add(trip.toMap());
        }

        adapter = new SimpleAdapter(
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

                final Button saveButton = (Button) v.findViewById(R.id.Save_trip);


                //Log.i("User",UserRequest.user.getSavedtrips().toString());
              // for ( int i =0; i < ((BaseActivity)getActivity()).getCurrentUser().getSavedtrips().size();i++){
                 //   if (((BaseActivity)getActivity()).getCurrentUser().getSavedtrips().get(i) == Trip.JSON_NAME ){
                    //    saveButton.getBackground().setColorFilter(Color.parseColor("#5BBC93"), PorterDuff.Mode.MULTIPLY);
                    //   saveButton.setText("Saved");
                   // }

            //}

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
                saveButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        String saveText = (String) saveButton.getText();
                        RelativeLayout rl = (RelativeLayout)arg0.getParent();
                        TextView tv = (TextView)rl.findViewById(R.id.name);
                        String text = tv.getText().toString();
                        if (saveText == "Saved") {
                            saveButton.getBackground().setColorFilter(Color.parseColor("#0E4375"), PorterDuff.Mode.MULTIPLY);
                            saveButton.setText("Save");
                            Toast.makeText(getContext(), "Removed From Saved", Toast.LENGTH_SHORT).show();
                            try {
                                new TripSearchRemoveTripRequest(text);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            saveButton.getBackground().setColorFilter(Color.parseColor("#5BBC93"), PorterDuff.Mode.MULTIPLY);
                            saveButton.setText("Saved");
                            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                            try {
                                new TripSearchSaveTripRequest(text);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                });
                return v;

            }

        };

        resultsList.setAdapter(adapter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((SimpleAdapter)TripSearchResultFragment.this.adapter).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setOnCreatedListener(TripSearchGetRequest request) {
        listener = request;
    }

    public interface onFragmentCreatedListener {
        void onCreatedView(TripSearchResultFragment fragment, View rootView) throws JSONException;
    }
}
