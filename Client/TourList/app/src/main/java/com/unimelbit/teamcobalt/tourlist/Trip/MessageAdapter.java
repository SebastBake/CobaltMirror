package com.unimelbit.teamcobalt.tourlist.Trip;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unimelbit.teamcobalt.tourlist.R;

import java.util.List;

/**
 * Created by awhite on 13/09/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<Message> messageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView msg, sender, timeSent;

        public MyViewHolder(View itemView) {
            super(itemView);
            msg = (TextView) itemView.findViewById(R.id.msg);
            sender = (TextView) itemView.findViewById(R.id.sender);
            timeSent = (TextView) itemView.findViewById(R.id.time_sent);
        }
    }

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.msg.setText(message.getMessage());
        holder.sender.setText(message.getUsername());
        holder.timeSent.setText(message.getTime());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
