package com.example.fireroom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ChatRegist extends AppCompatActivity {
    private EditText usernameEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);

        usernameEditText = findViewById(R.id.usernameEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUsername();
                finish();
            }
        });
    }

    private void saveUsername(){
        String username = usernameEditText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();

        // 保存后跳转到ChatActivity
        Intent intent = new Intent(ChatRegist.this, ChatActivity.class);
        startActivity(intent);
        finish(); // 结束当前活动，这样用户就不能通过后退按钮返回
    }
}
