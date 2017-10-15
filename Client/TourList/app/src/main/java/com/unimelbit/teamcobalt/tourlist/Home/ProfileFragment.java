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

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Model.Trip;
import com.unimelbit.teamcobalt.tourlist.Model.User;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.TripDetails.TripGetRequestByID;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private onFragmentCreatedListener listener;

    private ListAdapter adapter;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");

        initProfileTextBoxes(rootView);

        try {
            listener.onCreatedView(this, rootView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    /*
      Sets up the text boxes on the profile page
     */
    private void initProfileTextBoxes(View rootView) {

        User user = ((BaseActivity)getActivity()).getCurrentUser();

        TextView welcomeMsg = (TextView) rootView.findViewById(R.id.profile_you_are_signed_in_message);
        TextView detailsMsg = (TextView) rootView.findViewById(R.id.your_emial);
        TextView emailTextBox = (TextView) rootView.findViewById(R.id.profile_username);
        TextView tripsMsg = (TextView) rootView.findViewById(R.id.your_trips);

        if(user != null) {

            welcomeMsg.setText("Hello " + user.getUsername());
            detailsMsg.setText("Email:");
            emailTextBox.setText(user.getEmail());
        } else {
            welcomeMsg.setText("You aren't logged in right now...");
            detailsMsg.setText("Login to see your details");
            tripsMsg.setText("");
        }
    }

    /*
      Creates the list of saved trips
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

        adapter = new SimpleAdapter(
                getContext(),
                tripMaps,
                R.layout.fragment_search_results_items,
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
                        RelativeLayout rl = (RelativeLayout)arg0.getParent();
                        TextView tv = (TextView)rl.findViewById(R.id.name);
                        TextView tripID = (TextView)rl.findViewById(R.id.ID);
                        String nameText = tv.getText().toString();
                        String idText = tripID.getText().toString();
                        Toast.makeText(getContext(), nameText, Toast.LENGTH_SHORT).show();
                        new TripGetRequestByID(idText, ((BaseActivity)getActivity()).getMainContainerManager());
                    }
                });

                return v;

            }
        };

        resultsList.setAdapter(adapter);
    }

    public void setOnCreatedListener(ProfileTripsGetRequest request) {
        listener = request;
    }

    public interface onFragmentCreatedListener {
        void onCreatedView(ProfileFragment fragment, View rootView) throws JSONException;
    }
}
