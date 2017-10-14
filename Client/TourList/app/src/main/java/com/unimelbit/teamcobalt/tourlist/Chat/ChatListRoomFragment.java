package com.unimelbit.teamcobalt.tourlist.Chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;

/**
 * General chat room activity to show 2 chat rooms that users publicly join
 */
public class ChatListRoomFragment extends Fragment implements View.OnClickListener {

    private static final String LOGGED_OUT_CHAT_USERNAME = "anon";
    private static final String LOGGED_OUT_CHAT_USERID = "anonId";

    //Chatroom adaptor
    private ChatAdaptor chatServices;
    private BaseActivity base;
    private Button genChatButton, randChatButton;
    private String username;
    private String userId;

    public ChatListRoomFragment() {
        // Required empty public constructor
    }

    public static ChatListRoomFragment newInstance(String param1, String param2) {
        ChatListRoomFragment fragment = new ChatListRoomFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_chat_list_room, container, false);

        base = (BaseActivity) getActivity();

        if (BaseActivity.getCurrentUser() == null) {
            username = LOGGED_OUT_CHAT_USERNAME;
            userId = LOGGED_OUT_CHAT_USERID;
        } else {
            username = BaseActivity.getCurrentUser().getUsername();
            userId = BaseActivity.getCurrentUser().getId();
        }

        genChatButton = (Button) v.findViewById(R.id.gen_chat_button);
        genChatButton.setOnClickListener(this);
        randChatButton = (Button) v.findViewById(R.id.rand_chat_button);
        randChatButton.setOnClickListener(this);
        base.setTitle("Chat Rooms");

        TextView userText = (TextView) v.findViewById(R.id.chatroom_username_text);

        //Set user name
        userText.setText(username);

        //Initiate the handler
        chatServices = AppServicesFactory.getServicesFactory().getFirebaseChatService(getActivity());

        //Check if rooms are present
        chatServices.checkRoom("General");
        chatServices.checkRoom("Random");

        return v;
    }


    /**
     * Simply takes user to relevant chat room depending on the button they select
     * @param view
     */
    @Override
    public void onClick(View view) {

        int id = view.getId();
        ArrayList<String> users= new ArrayList<>();

        if(id == R.id.gen_chat_button){

            chatServices.enterChatRoom(userId, "General", "General",users,username);

        }

        else if(id == R.id.rand_chat_button){

            chatServices.enterChatRoom(userId, "Random", "Random",users,username);
        }
    }
}
