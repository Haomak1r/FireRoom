package com.example.fireroom;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    //private FirebaseUser mUser;
    private String mUser;
    private EditText mMessageEditText;
    private Button mSendButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private List<ChatMessage> mMessageList;
    private ChatAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        FirebaseApp.initializeApp(this);
        //mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser = getSharedPreferences("UserPreferences", MODE_PRIVATE).getString("username", "DefaultUsername");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("message");

        mMessageEditText = findViewById(R.id.messageTextView);
        mSendButton = findViewById(R.id.sendButton);
        mMessageRecyclerView = findViewById(R.id.messageRecyclerView);


        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(false);
        mMessageRecyclerView.setLayoutManager(mLayoutManager);

        mMessageList = new ArrayList<>();

        mAdapter = new ChatAdapter(mMessageList);
        mMessageRecyclerView.setAdapter(mAdapter);

        mSendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String messageText = mMessageEditText.getText().toString();
                if(!TextUtils.isEmpty(messageText)){
                    ChatMessage message = new ChatMessage(messageText, mUser);
                    mDatabase.push().setValue(message);
                    mMessageEditText.setText("");

                    // 滚动到最新消息
                    scrollToBottom();
                }
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMessageList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ChatMessage message = snapshot.getValue(ChatMessage.class);
                    mMessageList.add(message);
                }
                mAdapter.notifyDataSetChanged();

                // 滚动到最新消息
                scrollToBottom();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ChatActivity", "onCancelled: " + error.getMessage());
            }
        });

    }
    private void scrollToBottom() {
        // 确保列表已完全加载
        if (!mMessageList.isEmpty()) {
            int lastPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastPosition == -1 || lastPosition < mMessageList.size() - 1) {
                mMessageRecyclerView.scrollToPosition(mMessageList.size() - 1);
            }
        }
    }
}
