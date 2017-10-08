package com.unimelbit.teamcobalt.tourlist.Chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.unimelbit.teamcobalt.tourlist.AppServicesFactory;
import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ChatroomActivity extends AppCompatActivity {

    private FirebaseListAdapter<Chat> adapter;
    private String userName;
    private String roomName, roomId;
    private ArrayList<String> users;
    private FirebaseChatRoomHandler chatRoomHandler;
    private ListView listOfMessages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        //mAuth = FirebaseAuth.getInstance();

        users = getIntent().getExtras().getStringArrayList("users");
        Toast.makeText(this,"Result: " + users, Toast.LENGTH_SHORT).show();

        userName = getIntent().getExtras().getString("Name");

        listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        FirebaseMessaging.getInstance().subscribeToTopic("user_"+userName);

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
                    // Read the input field and push a new instance
                    // of ChatMessage to the Firebase database
                    chatRoomHandler.sendMessage(message, userName, roomId);


                   if (!users.isEmpty() || users != null){

                       chatRoomHandler.sendNotification(users, message, userName);

                   }


                    // Clear the input
                    input.setText("");
                }
            }
        });



    }


    private void displayChatMessages() {

        adapter = new FirebaseListAdapter<Chat>(this, Chat.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference().child(roomId)) {
            @Override
            protected void populateView(View v, Chat chat, int position) {

                TextView messageText = (TextView) v.findViewById(R.id.message_text);
                TextView userNameText = (TextView) v.findViewById(R.id.message_user);
                TextView timeText = (TextView) v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(chat.getMessage());
                userNameText.setText(chat.getUserName());

                // Format the date before showing it
                timeText.setText(DateFormat.format("EEE, d MMM yyyy (h:mm a)",
                        chat.getTime()));
            }
        };

        listOfMessages.setAdapter(adapter);

    }


}
