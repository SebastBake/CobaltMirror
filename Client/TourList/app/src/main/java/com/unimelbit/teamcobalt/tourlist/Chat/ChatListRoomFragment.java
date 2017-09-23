package com.unimelbit.teamcobalt.tourlist.Chat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;


public class ChatListRoomFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BaseActivity base;

    private Button genChatButton, randChatButton;

    private ChatRoomController chatController;

    public ChatListRoomFragment() {
        // Required empty public constructor
    }

    public static ChatListRoomFragment newInstance(String param1, String param2) {
        ChatListRoomFragment fragment = new ChatListRoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_chat_list_room, container, false);

        base = (BaseActivity) getActivity();

        genChatButton = (Button) v.findViewById(R.id.gen_chat_button);

        genChatButton.setOnClickListener(this);

        randChatButton = (Button) v.findViewById(R.id.rand_chat_button);

        randChatButton.setOnClickListener(this);

        chatController = new ChatRoomController(base);

        base.setTitle("Chat Rooms");

        return v;
    }



    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.gen_chat_button){

            chatController.enterRoom(base.getUserName(), "General");

        }
        else if(id == R.id.rand_chat_button){

            chatController.enterRoom(base.getUserName(), "Random");
        }

    }





}
