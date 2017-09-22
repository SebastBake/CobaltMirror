package com.unimelbit.teamcobalt.tourlist.TripDetails;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Chat.ChatroomActivity;
import com.unimelbit.teamcobalt.tourlist.Chat.ChatroomCreator;
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

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild(base.getCurrentTrip().getName())) {

                    ChatroomCreator create = new ChatroomCreator();

                    create.generateRoom(base.getCurrentTrip().getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.button_chat){

            String name = base.getUserName();

            Intent chatIntent = new Intent(getActivity(), ChatroomActivity.class);

            if(name == null){

                name = "User did not login";

            }

            chatIntent.putExtra("Name", name);

            chatIntent.putExtra("Room_name", base.getCurrentTrip().getName());

            getActivity().startActivity(chatIntent);


        }

    }

}
