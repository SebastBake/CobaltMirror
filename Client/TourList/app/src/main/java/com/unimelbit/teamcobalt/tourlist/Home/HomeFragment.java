package com.unimelbit.teamcobalt.tourlist.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private BaseActivity base;

    private Button searchB, createB, myTripB, chatRoomB;


    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");

        base = (BaseActivity)getActivity();

        searchB = (Button) rootView.findViewById(R.id.searchButtonMain);

        searchB.setOnClickListener(this);

        myTripB = (Button) rootView.findViewById(R.id.myTripButton);

        myTripB.setOnClickListener(this);

        createB = (Button) rootView.findViewById(R.id.createButtonMain);

        createB.setOnClickListener(this);

        chatRoomB = (Button) rootView.findViewById(R.id.generalChat);

        chatRoomB.setOnClickListener(this);

        FirebaseMessaging.getInstance().subscribeToTopic("user_"+base.getCurrentUser().getId());

        return rootView;
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.createButtonMain) {
            base.getMainContainer().gotoCreateTrip();

        } else if (id == R.id.searchButtonMain) {
            base.getMainContainer().gotoTripSearchFragment();

        } else if (id == R.id.myTripButton) {
               new MyTripsGetRequest(base.getMainContainer());

        } else if (id == R.id.generalChat){

            base.getMainContainer().goToChatRooms();

        }

        else{

            base.getMainContainer().gotoTabbedTripFragment(BaseActivity.DEMOTRIP_NAME);


        }
    }
}
