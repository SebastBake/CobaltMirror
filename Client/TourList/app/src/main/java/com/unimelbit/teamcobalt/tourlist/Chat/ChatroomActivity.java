package com.unimelbit.teamcobalt.tourlist.Chat;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unimelbit.teamcobalt.tourlist.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ChatroomActivity extends AppCompatActivity {

    private FirebaseListAdapter<Chat> adapter;

    private String userName = "No name";

    private String roomName;

    private DatabaseReference root;

    private String key;

    private TextView messageText;
    private TextView messageUser;
    private TextView messageTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        userName = getIntent().getExtras().getString("Name");

        roomName = getIntent().getExtras().getString("Room_name");

        root = FirebaseDatabase.getInstance().getReference().child(roomName);

        messageText = (TextView)findViewById(R.id.message_text);
        messageUser = (TextView)findViewById(R.id.message_user);
        messageTime = (TextView)findViewById(R.id.message_time);

        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));

                Map<String, Object> map = new HashMap<String, Object>();
                key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference mRoot = root.child(key);

                Map<String, Object> messageMap = new HashMap<String, Object>();

                messageMap.put("user",userName);
                messageMap.put("message", input.getText().toString());
                messageMap.put("time", dateFormat.format(date));

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database

                mRoot.updateChildren(messageMap);

                // Clear the input
                input.setText("");
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                addToConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                addToConversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }


    public void addToConversation(DataSnapshot dataSnapshot){

        String message, user, time;

        Iterator i = dataSnapshot.getChildren().iterator();

        while(i.hasNext()){

            user = (String) ((DataSnapshot)i.next()).getValue();
            message = (String) ((DataSnapshot)i.next()).getValue();
            time = (String) ((DataSnapshot)i.next()).getValue();

            messageText.setText(message);

            messageUser.setText(user);

            messageTime.setText(time);

        }


    }


}
