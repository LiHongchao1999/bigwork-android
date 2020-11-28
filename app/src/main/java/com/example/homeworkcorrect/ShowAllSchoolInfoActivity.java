package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.homeworkcorrect.adapter.CustomSchoolAdapter;
import com.example.homeworkcorrect.entity.School;

import java.util.ArrayList;
import java.util.List;

public class ShowAllSchoolInfoActivity extends AppCompatActivity {
    private CustomSchoolAdapter customSchoolAdapter;
    private List<School> schools = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_school_info);
        getSchoolList();

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 处理消息
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    //准备数据
                    initViewForSchool();
                    break;
            }
        }

        ;

    };

    private void initViewForSchool() {
        customSchoolAdapter = new CustomSchoolAdapter(this, schools,
                R.layout.school_list_item);
        ScrollableGridView schoolListView = findViewById(R.id.gv_all_school);
        schoolListView.setAdapter(customSchoolAdapter);

        schoolListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //打印
                Log.e("选中的蛋糕名称----------------", "" + schools.get(position).getName());
                int menu_id = schools.get(position).getId();
                //判断用户是否登录
//                if (user_Name.length() <= 0){
//                    Toast.makeText(getActivity(), "请先登录",
//                            Toast.LENGTH_SHORT).show();
//                }
//                else {
//
                //跳转到蛋糕详细信息的Activity
                Intent intent = new Intent();
                //设置目的地Activity类
                intent.setClass(ShowAllSchoolInfoActivity.this,
                        ShowSchoolInfoActivity.class);
                //传递自定义类型的对象
                //携带用户选中的菜谱id
                intent.putExtra("school", schools.get(position));
                startActivity(intent);
//                }
            }
        });

    }


    private void getSchoolList() {
        School school1 = new School("音悦家艺术教育培训学校1", "河北省石家庄市裕华区南二环东路",
                "数学、英语", "", "18811111111");
        School school2 = new School("音悦家艺术教育培训学校2", "河北省石家庄市裕华区南二环东路",
                "语文、数学、英语", "", "18811111111");
        School school3 = new School("音悦家艺术教育培训学校3", "河北省石家庄市裕华区南二环东路",
                "数学", "", "18811111111");
        schools.add(school1);
        schools.add(school2);
        schools.add(school3);
        Log.e("school", schools.toString());
        //资源下载完成，返回消息
        Message msg = handler.obtainMessage();
        msg.what = 2;
        handler.sendMessage(msg);

    }


}