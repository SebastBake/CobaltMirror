package com.unimelbit.teamcobalt.tourlist.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.unimelbit.teamcobalt.tourlist.BackButtonInterface;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequestByID;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

/**
 * Fragment that displays the trips users have saved
 */
public class MyTripsFragment extends Fragment implements BackButtonInterface {

    private onFragmentCreatedListener listener;

    private ListAdapter adapter;

    public MyTripsFragment() {
    }

    public static MyTripsFragment newInstance() {
        MyTripsFragment fragment = new MyTripsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_trips, container, false);
        getActivity().setTitle("Profile");



        try {
            listener.onCreatedView(this, rootView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }


    /**
     * Create a list of trips the user has saved
     * @param trips
     * @param rootView
     * @throws JSONException
     */
    public void showResultsList(ArrayList<Trip> trips, View rootView) throws JSONException {

        TextView tripsMsg = (TextView) rootView.findViewById(R.id.your_trips);
        final ListView resultsList = (ListView)rootView.findViewById(R.id.results_list);

        if (trips.isEmpty()) {
            tripsMsg.setText("You have no Trips");
        }

        ArrayList<Map<String, String>> tripMaps = new ArrayList<>();
        for(Trip trip: trips) {
            tripMaps.add(trip.toMap());
        }

        //Adapter for the list of trips user has. This will pull the trip info for the said user
        adapter = new SimpleAdapter(
                getContext(),
                tripMaps,
                R.layout.fragment_my_trips_items,
                new String[]{Trip.JSON_NAME,Trip.JSON_ID},
                new int[]{R.id.name,R.id.ID}) {

            @Override
            public View getView (int position, final View convertView, ViewGroup parent)
            {
                View v = super.getView(position, convertView, parent);

                Button b = (Button)v.findViewById(R.id.Go_to_trip);


                // On button click, go to trip details fragment.
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        //Get information about the trip
                        RelativeLayout rl = (RelativeLayout)arg0.getParent();
                        TextView tv = (TextView)rl.findViewById(R.id.name);
                        TextView tripID = (TextView)rl.findViewById(R.id.ID);
                        String nameText = tv.getText().toString();
                        String idText = tripID.getText().toString();
                        Toast.makeText(getContext(), nameText, Toast.LENGTH_SHORT).show();

                        //Send user to the trip
                        new TripGetRequestByID(idText, ((BaseActivity)getActivity()).getMainContainerManager());
                    }
                });

                return v;

            }
        };

        resultsList.setAdapter(adapter);
    }

    /**
     * Set the listener for the request
     * @param request
     */
    public void setOnCreatedListener(MyTripsGetRequest request) {
        listener = request;
    }

    public interface onFragmentCreatedListener {
        void onCreatedView(MyTripsFragment fragment, View rootView) throws JSONException;
    }
}
