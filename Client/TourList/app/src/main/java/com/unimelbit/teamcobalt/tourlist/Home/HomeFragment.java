package com.unimelbit.teamcobalt.tourlist.Home;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private BaseActivity base;

    private Button searchB, createB, currTripB, chatRoomB, startB;


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

        chatRoomB = (Button) rootView.findViewById(R.id.generalChat);

        chatRoomB.setOnClickListener(this);

        startB = (Button) rootView.findViewById(R.id.startButton);

        startB.setOnClickListener(this);

        TextView lat = (TextView) rootView.findViewById(R.id.latView);

        TextView lon = (TextView) rootView.findViewById(R.id.longView);

        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linear);
        for (int i = 0; i < 20; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setId(i);
            //left, top, right, bottom
            imageView.setPadding(30, 10, 30, 10);
            imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), R.mipmap.umaru));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOnClickListener(this);
            layout.addView(imageView);
        }

        base.setLatLong(lat, lon);

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
                base.getMainContainer().gotoTabbedTripFragment(BaseActivity.DEMOTRIP_NAME);
            }
        } else if (id == R.id.generalChat){

            base.getMainContainer().goToChatRooms();

        }else if (id == R.id.startButton){

            AppServicesFactory.getServicesFactory().getFirebaseChatService(getActivity()).deleteRoom("TestUser");

        }

        else{

            base.getMainContainer().gotoTabbedTripFragment(BaseActivity.DEMOTRIP_NAME);


        }
    }
}
