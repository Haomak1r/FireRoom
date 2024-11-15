package com.example.fireroom;


import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {
    private List<ChatMessage> mMessageList;

    public ChatAdapter(List<ChatMessage> messagesList){
        mMessageList = messagesList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = mMessageList.get(position);
        holder.messageTextView.setText(message.getMessageText());
        holder.nameTextView.setText(message.getMessageUser());
        holder.timeTextView.setText(DateFormat.format("dd-MM-yyyy(HH:mm:ss)", message.getMessageTime()));
    }


    @Override
    public int getItemCount(){
        return mMessageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView messageTextView;
        public TextView nameTextView;
        public TextView timeTextView;

        public MessageViewHolder(View itemView){
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
