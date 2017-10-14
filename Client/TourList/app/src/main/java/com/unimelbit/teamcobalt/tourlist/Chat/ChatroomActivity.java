package com.unimelbit.teamcobalt.tourlist.Chat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;


/**
 *
 * Chat activity to display messages from other users. This will utilise firebase as a database
 * to share messages.
 *
 * Tutorial from: Ashraff Hathibelagal
 * @https://code.tutsplus.com/tutorials/how-to-create-an-android-chat-app-using-firebase--cms-27397
 */

public class ChatroomActivity extends AppCompatActivity {


    private FirebaseListAdapter<Chat> adapter;
    private String userName,userId;
    private String roomName, roomId;
    private ArrayList<String> users;
    private FirebaseChatRoomHandler chatRoomHandler;
    private ListView listOfMessages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);


        users = getIntent().getExtras().getStringArrayList("users");
        Toast.makeText(this,"Result: " + users, Toast.LENGTH_SHORT).show();

        userName = getIntent().getExtras().getString("Username");
        userId = getIntent().getExtras().getString("UserId");

        listOfMessages = (ListView)findViewById(R.id.list_of_messages);



        //Handler to be responsible for handling messaging functions
        chatRoomHandler = (FirebaseChatRoomHandler) AppServicesFactory.getServicesFactory()
                .getFirebaseChatService(this);

        roomName = getIntent().getExtras().getString("Room_name");

        roomId = getIntent().getExtras().getString("Id");

        displayChatMessages();

        setTitle(roomName+" Chat");

        //Capitalise words at the start of sentences
        EditText input = (EditText) findViewById(R.id.input);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);


        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText input = (EditText)findViewById(R.id.input);

                String message = input.getText().toString();

                if(!message.isEmpty()) {
                    // Send message if it isn't empty
                    chatRoomHandler.sendMessage(message, userName, roomId);

                   if (!users.isEmpty() || users != null){

                       chatRoomHandler.sendNotification(users, message, userName);

                   }

                    input.setText("");
                }
            }
        });



    }


    /**
     * Displays the messages received from Firebase for the relevant chat room
     */
    private void displayChatMessages() {

        adapter = new FirebaseListAdapter<Chat>(this, Chat.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference().child(roomId)) {
            //Uses adapter to update and display values from the Chat model class
            @Override
            protected void populateView(View v, Chat chat, int position) {

                TextView messageText = (TextView) v.findViewById(R.id.message_text);
                TextView userNameText = (TextView) v.findViewById(R.id.message_user);
                TextView timeText = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(chat.getMessage());
                userNameText.setText(chat.getUserName());
                timeText.setText(DateFormat.format("EEE, d MMM yyyy (h:mm a)",
                        chat.getTime()));
            }
        };

        listOfMessages.setAdapter(adapter);

    }


}
