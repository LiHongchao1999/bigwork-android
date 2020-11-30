package com.example.homeworkcorrect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import com.example.homeworkcorrect.adapter.CustomMsgAdapter;
import com.example.homeworkcorrect.chat.ChatActivity;
import com.example.homeworkcorrect.entity.Information;

import java.util.ArrayList;
import java.util.List;

public class InformationActivity extends AppCompatActivity {
    private ImageView back; //返回按钮
    private MyListView listView; //消息列表
    private ImageView addFre;//添加好友
    private CustomMsgAdapter adapter;
    private List<Information> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getViews();
        //准备假数据
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.my1);
        Information information = new Information("清风","新朋友你好","2020-11-30 5:14",bitmap);
        list.add(information);
        adapter = new CustomMsgAdapter(this,list,R.layout.infomation_list_item_layout);
        listView.setAdapter(adapter);
        //给每个item绑定监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InformationActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getViews() {
        listView = findViewById(R.id.info_list);
        back = findViewById(R.id.info_back);
        addFre = findViewById(R.id.info_add_fre);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /*
    * 单机事件
    * */
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.info_back://点击返回
                finish();
                break;
            case R.id.info_add_fre://点击添加
                break;
        }
    }
}