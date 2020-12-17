package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeworkcorrect.adapter.CustomAdapterResult;
import com.example.homeworkcorrect.entity.WrongQuestion;
import com.google.gson.Gson;

public class WrongQuestionDetailActivity extends AppCompatActivity {
    private WrongQuestion question;
    private ScrollableGridView gridView;//错题
    private ScrollableGridView gridView1;//订正
    private ScrollableGridView gridView2;//老师解析
    private EditText comment;//备注
    private CustomAdapterResult result;//老师的
    private CustomAdapterResult result1;//个人的
    private CustomAdapterResult result2;//老师的解析
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_question_detail);
        getViews();
        //获取数据
        Intent intent = getIntent();
        String str = intent.getStringExtra("question");
        Gson gson = new Gson();
        question = gson.fromJson(str,WrongQuestion.class);
        //赋值
        comment.setText(question.getResult_text_student());
        result = new CustomAdapterResult(this,question.getHomework_image(),R.layout.send_img_list_item);
        gridView.setAdapter(result);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new ShowImagesDialog(WrongQuestionDetailActivity.this,question.getHomework_image(),i).show();
            }
        });
        result1 = new CustomAdapterResult(this,question.getResult_image(),R.layout.send_img_list_item);
        gridView1.setAdapter(result1);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new ShowImagesDialog(WrongQuestionDetailActivity.this,question.getResult_image(),i).show();
            }
        });
        Log.e("老师解析",question.getResult_image_teacher().toString());
        result2 = new CustomAdapterResult(this,question.getResult_image_teacher(),R.layout.send_img_list_item);
        gridView2.setAdapter(result2);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new ShowImagesDialog(WrongQuestionDetailActivity.this,question.getResult_image_teacher(),position).show();
            }
        });

    }

    private void getViews() {
        gridView = findViewById(R.id.detail_result_img);
        gridView1 = findViewById(R.id.detail_self_result_img);
        gridView2 = findViewById(R.id.detail_teacher_img);
        comment = findViewById(R.id.detail_correct_self_text);
    }
}