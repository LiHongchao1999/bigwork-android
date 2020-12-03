package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.homeworkcorrect.adapter.CustomAdapterResult;
import com.example.homeworkcorrect.adapter.CustomSendImgAdapter;
import com.example.homeworkcorrect.entity.Homework;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeWorkCorrectDetail extends AppCompatActivity {
    private ScrollableGridView gridView;//图片列表
    private TextView comment;//评语
    private Homework homework = new Homework();
    private CustomAdapterResult adapter;
    private List<String> result;//老师发送的图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_correct_detail);
        //获取控件
        getViews();
        //获取数据
        Intent intent = getIntent();
        String jsonStr = intent.getStringExtra("homework");
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            homework.setId(jsonObject.getInt("id"));
            homework.setSubmitTime(jsonObject.getString("submitTime"));
            homework.setDeadline(jsonObject.getString("deadLine"));
            homework.setHomeworkType(jsonObject.getString("type"));
            homework.setTag(jsonObject.getString("tag"));
            Gson gson = new GsonBuilder()
                    .serializeNulls()//允许导出null值
                    .setPrettyPrinting() //格式化输出
                    .create();
            Type collectionType = new TypeToken<List<String>>(){}.getType();
            result = gson.fromJson(jsonObject.getString("resultImg"),collectionType);
            homework.setResult_image(result);
            homework.setTeacher_id(jsonObject.getInt("teacher_id"));
            homework.setResult_text(jsonObject.getString("resultText"));
            homework.setMoney(jsonObject.getDouble("money"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new CustomAdapterResult(this,result,R.layout.send_img_list_item);
        gridView.setAdapter(adapter);
        comment.setText(homework.getResult_text());
    }
    private void getViews() {
        gridView = findViewById(R.id.home_image);
        comment = findViewById(R.id.teacher_comment);
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.detail_return:
                finish();
                break;
            case R.id.btn_add_error://加入做题本
                Intent intent = new Intent(this,AddErrorBookActivity.class);
                intent.putExtra("id",homework.getId());
                Gson gson = new Gson();
                String str = gson.toJson(homework.getResult_image());
                intent.putExtra("type",homework.getHomeworkType());
                intent.putExtra("submitTime",homework.getSubmitTime());
                intent.putExtra("teacher_result",homework.getResult_text());
                intent.putExtra("result_img",str);
                startActivity(intent);
                break;
            case R.id.user_comment://进行评价
                break;
        }
    }
}