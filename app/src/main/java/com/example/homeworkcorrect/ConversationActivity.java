package com.example.homeworkcorrect;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import io.rong.imkit.fragment.ConversationFragment;

/*
* 会话页面
* */
public class ConversationActivity extends AppCompatActivity {
    private TextView userName;//好友用户名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        //用户昵称
        String title = getIntent().getData().getQueryParameter("title");
        Log.e("title",title);
        String targetId = getIntent().getData().getQueryParameter("targetId");
        Log.e("targetId",targetId);
        /*String conversation = getIntent().getData().getQueryParameter("conversation");
        Log.e("conversation",conversation);*/
        userName = findViewById(R.id.fre_user_name);
        userName.setText(title);
        ConversationFragment conversationFragment=new ConversationFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, conversationFragment);
        transaction.commit();
    }






    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.return_msg:
                finish();
                break;
        }
    }
}