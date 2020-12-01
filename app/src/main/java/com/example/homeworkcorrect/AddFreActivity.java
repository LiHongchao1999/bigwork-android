package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.homeworkcorrect.entity.SearchFriend;

import java.util.ArrayList;
import java.util.List;

public class AddFreActivity extends AppCompatActivity {
    private ImageView imgBack;//返回
    private EditText content;//输入的内容
    private MyListView myListView;//搜索的结果展示
    private List<SearchFriend> fres = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fre);
        myListView = findViewById(R.id.add_list);
    }
    /*
    * 点击事件
    * */
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.search_btn://点击搜索
                //将内容传递给服务端，进行查询
                break;
            case R.id.add_back://点击返回
                finish();
                break;
        }
    }
}