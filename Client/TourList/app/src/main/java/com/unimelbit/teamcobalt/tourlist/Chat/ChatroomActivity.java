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
import com.google.firebase.database.FirebaseDatabase;

import com.unimelbit.teamcobalt.tourlist.BaseActivity;
import com.unimelbit.teamcobalt.tourlist.R;


public class ChatroomActivity extends AppCompatActivity {

    private FirebaseListAdapter<Chat> adapter;

    private String userName;

    private String roomName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        //mAuth = FirebaseAuth.getInstance();

        userName = getIntent().getExtras().getString("Name");

        if(userName.isEmpty() || userName == null){

            userName = "Didn't login properly";

        }

        roomName = getIntent().getExtras().getString("Room_name");

        displayChatMessages();

        setTitle(roomName+" Chat");

        EditText input = (EditText) findViewById(R.id.input);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);


        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                if(!input.getText().toString().isEmpty()) {
                    // Read the input field and push a new instance
                    // of ChatMessage to the Firebase database
                    FirebaseDatabase.getInstance()
                            .getReference().child(roomName)
                            .push()
                            .setValue(new Chat(input.getText().toString(),
                                    userName)
                            );

                    // Clear the input
                    input.setText("");
                }
            }
        });

    }


    private void displayChatMessages() {

        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<Chat>(this, Chat.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference().child(roomName)) {
            @Override
            protected void populateView(View v, Chat model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }



}
