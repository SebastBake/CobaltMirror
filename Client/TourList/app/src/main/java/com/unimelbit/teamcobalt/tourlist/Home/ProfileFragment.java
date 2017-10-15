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




}
