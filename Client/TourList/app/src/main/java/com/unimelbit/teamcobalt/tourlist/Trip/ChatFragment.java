package com.unimelbit.teamcobalt.tourlist.Trip;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.unimelbit.teamcobalt.tourlist.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {

    public static final int TRIP_SECTION_INDEX = 1;

    private List<Message> messageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;
    private static final int SERVERPORT = 5001;
    private static final String SERVER_IP = "10.0.2.2";

    private Socket socket;
    Button sendBtn;
    EditText editText;


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

        View layout = inflater.inflate(R.layout.fragment_chat, container, false);
        ClientThread ct = new ClientThread();
        new Thread(ct).start();


        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);

        mAdapter = new MessageAdapter(messageList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        prepareMessageData();

        sendBtn = (Button) layout.findViewById(R.id.button_chatbox_send);
        editText = (EditText) layout.findViewById(R.id.EditText01);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString();
                Message message = new Message(str, "Alex");
                messageList.add(message);
                mAdapter.notifyDataSetChanged();
                new Thread(new SendMsg(str)).start();
                editText.setText("");
            }
        });
        return layout;
    }

    class SendMsg implements Runnable {
        String msg;

        public SendMsg(String msg){
            this.msg = msg;
        }

        public void run() {
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(msg);
                out.flush();
                //out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while(true){
                    String message;
                    Message msg = null;
//                    GsonBuilder builder = new GsonBuilder();
//                    Gson gson = builder.create();

                    if ((message = in.readLine()) != null) {
                        msg = new Message(message, "Alex");
                    }
                    if (msg != null) {
                        //System.out.println(msg.getMessage());
                        messageList.add(msg);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    private void prepareMessageData() {
        Message message = new Message("Hey mate", "Alex");
        messageList.add(message);

        message = new Message("Sup bro?", "Spike");
        messageList.add(message);

        message = new Message("Hey mate", "Alex");
        messageList.add(message);

        message = new Message("Sup bro?", "Spike");
        messageList.add(message);

        message = new Message("Hey mate", "Alex");
        messageList.add(message);

        message = new Message("Sup bro?", "Spike");
        messageList.add(message);

        message = new Message("Hey mate", "Alex");
        messageList.add(message);

        message = new Message("Sup bro?", "Spike");
        messageList.add(message);

        message = new Message("Hey mate", "Alex");
        messageList.add(message);

        message = new Message("Sup bro?", "Spike");
        messageList.add(message);
        message = new Message("Hey mate", "Alex");
        messageList.add(message);

        message = new Message("Sup bro?", "Spike");
        messageList.add(message);

        message = new Message("Hey mate", "Alex");
        messageList.add(message);

        message = new Message("Sup bro?", "Spike");
        messageList.add(message);


        mAdapter.notifyDataSetChanged();

    }
}
