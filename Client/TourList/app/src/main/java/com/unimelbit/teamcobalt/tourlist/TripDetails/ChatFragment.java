package com.unimelbit.teamcobalt.tourlist.TripDetails;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Chat.ChatActivity;
import com.unimelbit.teamcobalt.tourlist.Chat.ChatroomActivity;
import com.unimelbit.teamcobalt.tourlist.R;


public class ChatFragment extends Fragment implements View.OnClickListener{
    public static final int TRIP_SECTION_INDEX = 1;

    private Button chatButton;

    private BaseActivity base;

    public ChatFragment() {
        // Required empty public constructor
    }


    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_chat, container, false);

        base = (BaseActivity) getActivity();

        chatButton = (Button) v.findViewById(R.id.button_chat);

        chatButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.button_chat){

            System.out.println("REEEEEEEEEEEEEEEEEEEEEEEEEE: "+base.getCurrentTrip().getName() );

            Toast.makeText(getActivity(), base.getCurrentTrip().getName(), Toast.LENGTH_LONG).show();

            Intent chatIntent = new Intent(getActivity(), ChatroomActivity.class);

            chatIntent.putExtra("Name", base.getUserName());

            chatIntent.putExtra("Room_name", base.getCurrentTrip().getName());



            getActivity().startActivity(chatIntent);


        }

    }
}
