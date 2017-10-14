package com.unimelbit.teamcobalt.tourlist.TripDetails;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.Chat.ChatAdaptor;
import com.unimelbit.teamcobalt.tourlist.Profile.UserProfile;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;


public class ChatFragment extends Fragment implements View.OnClickListener{
    public static final int TRIP_SECTION_INDEX = 1;

    private Button chatButton;
    private TextView title, info;
    private ListView list;
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
        if (base.getCurrentTrip().getUserids().contains(base.getCurrentUser().getId()) == false){
            isUserInTrip = false;
        }else{
            isUserInTrip = true;
        }

        // Here, you set the data in your ListView
        listV.setAdapter(adapter);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long i){

                String user = userList.get(position);
                Intent intent = new Intent(getActivity(),UserProfile.class);
                startActivity(intent);
            }
        });

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
            if (checkUserInTrip()==true) {
                String roomName = BaseActivity.getCurrentTrip().getName();
                String roomId = BaseActivity.getCurrentTrip().getId();

                ChatAdaptor chatService = AppServicesFactory.getServicesFactory().getFirebaseChatService(getActivity());
                chatService.checkRoom(roomId);
                chatService.enterChatRoom(BaseActivity.getCurrentUser().getId(), roomName, roomId, base.getCurrentTrip().getUserids(), username);
            }
        }
    }

    private boolean checkUserInTrip(){
        if(isUserInTrip == false){
            Toast.makeText(base,"Please save trip first",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private void setTextView(TextView t, String s){
        t.setText(s);
    }

    public ArrayList<String> initUsers(int n){

        ArrayList<String> array = new ArrayList<String>();

        for(int i = 0; i < n; i++){
            String item = "User "+ i;
            array.add(item);
        }

        return array;
    }
}
