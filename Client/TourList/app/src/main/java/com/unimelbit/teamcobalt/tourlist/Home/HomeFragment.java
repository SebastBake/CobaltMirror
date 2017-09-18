package com.unimelbit.teamcobalt.tourlist.Home;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.CreateTrips.CreateTripFragment;
import com.unimelbit.teamcobalt.tourlist.R;
import com.unimelbit.teamcobalt.tourlist.TripSearch.TripSearchFragment;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private BaseActivity base;

    private Button searchB, createB, currTripB;


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

        currTripB = (Button) rootView.findViewById(R.id.curTripButton);

        currTripB.setOnClickListener(this);

        createB = (Button) rootView.findViewById(R.id.createButtonMain);

        createB.setOnClickListener(this);

        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linear);
        for (int i = 0; i < 20; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setId(i);
            imageView.setPadding(50, 50, 50, 50);
            imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), R.mipmap.umaru));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            layout.addView(imageView);
        }

        return rootView;
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.createButtonMain) {
            base.getMainContainer().gotoCreateFragment();

        } else if (id == R.id.searchButtonMain) {
            base.getMainContainer().gotoTripSearchFragment();

        } else if (id == R.id.curTripButton) {
            if (base.getCurrentTrip() != null) {
                base.getMainContainer().gotoTabbedTripFragment(base.getCurrentTrip());
            } else {
                base.getMainContainer().gotoTabbedTripFragment(BaseActivity.DEMOTRIP_URL);
            }
        }
    }
}
