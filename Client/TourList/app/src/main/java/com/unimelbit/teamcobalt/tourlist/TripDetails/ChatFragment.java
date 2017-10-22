package com.unimelbit.teamcobalt.tourlist.TripDetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Chat.ChatAdaptor;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;

/**
 * Chat fragment that shows user who is in the trip and a button to join the chat room
 */
public class ChatFragment extends Fragment implements View.OnClickListener{
    public static final int TRIP_SECTION_INDEX = 1;

    private Button chatButton;
    private TextView title, info;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> userList;
    private BaseActivity base;
    private String username;
    private boolean isUserInTrip;

    public ChatFragment() {
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
        title = (TextView) v.findViewById(R.id.chatFragmentTitle);
        info = (TextView) v.findViewById(R.id.chatFragmentDesc);
        ListView listV = (ListView) v.findViewById(R.id.userListChat);

        userList = BaseActivity.getCurrentTrip().getUsernames();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, userList);

        //Check if user is in trip
        isUserInTrip = base.getCurrentTrip().getUserids().contains(base.getCurrentUser().getId());


        // Here, you set the data in your ListView
        listV.setAdapter(adapter);

        if (BaseActivity.getCurrentUser() == null) {
            username = "anonymous user";
        } else {
            username = BaseActivity.getCurrentUser().getUsername();
        }

        setTextView(title, BaseActivity.getCurrentTrip().getName()+" Chat");
        setTextView(info, "You will be signed in Chat as: "+ username);

        return v;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.button_chat){
            //If user in trip, initialise the parameters to enter the chat room
            if (checkUserInTrip()) {

                String roomName = BaseActivity.getCurrentTrip().getName();
                String roomId = BaseActivity.getCurrentTrip().getId();

                ChatAdaptor chatService = AppServicesFactory.getServicesFactory().getFirebaseChatService(getActivity());
                //Check the room exists
                chatService.checkRoom(roomId);

                //Enter the chat room
                chatService.enterChatRoom(BaseActivity.getCurrentUser().getId(), roomName, roomId,
                        base.getCurrentTrip().getUserids(), username);
            }
        }
    }

    /**
     * Only lets the user enter the chat if they are in the trip, otherwise tell them to join
     * @return
     */
    private boolean checkUserInTrip(){
        if(!isUserInTrip){
            Toast.makeText(base,"Please save trip first",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private void setTextView(TextView t, String s){
        t.setText(s);
    }

}
