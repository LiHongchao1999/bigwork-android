package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.adapter.CircleImgListAdapter;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.chat.CircleImageView;
import com.example.homeworkcorrect.entity.Circle;
import com.google.gson.Gson;

public class CircleDetailActivity extends AppCompatActivity {
    private MyListView commentContent;//评论内容
    private CircleImageView headImg;//头像
    private TextView nickName;//昵称
    private TextView sendTime;//发布时间
    private TextView sendContent;//发布内容
    private ScrollableGridView sendImg;//发布图片
    private TextView forwardSize; //转发数
    private TextView commentSize; //评论数
    private TextView likedSize;  //点赞数
    private Circle circle;
    private CircleImgListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_detail);
        //获取控件
        getViews();
        //获取数据
        Intent intent = getIntent();
        String str = intent.getStringExtra("circle");
        circle = new Gson().fromJson(str,Circle.class);
        //赋值
        Glide.with(this).load(IP.CONSTANT+"userImage/"+circle.getUserImg()).into(headImg);
        adapter = new CircleImgListAdapter(this,circle.getSendImg(),R.layout.send_img_list_item);
        sendImg.setAdapter(adapter);
        nickName.setText(circle.getUserName());
        sendTime.setText(circle.getTime());
        sendContent.setText(circle.getContent());
        forwardSize.setText(circle.getForwardSize()+"");
        commentSize.setText(circle.getCommentSize()+"");
        likedSize.setText(circle.getLikeSize()+"");
    }
    /*
    * 获取控件
    * */
    private void getViews() {
        commentContent = findViewById(R.id.all_comment);
        headImg = findViewById(R.id.detail_img);
        nickName = findViewById(R.id.detail_nickname);
        sendTime = findViewById(R.id.detail_send_time);
        sendContent = findViewById(R.id.detail_send_content);
        sendImg = findViewById(R.id.deatil_send_img);
        forwardSize = findViewById(R.id.fre_forward_size);
        commentSize = findViewById(R.id.fre_comment_size);
        likedSize = findViewById(R.id.fre_like_size);

    }
}