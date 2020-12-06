package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.homeworkcorrect.entity.WrongQuestion;
import com.google.gson.Gson;

public class WrongQuestionDetailActivity extends AppCompatActivity {
    private WrongQuestion question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_question_detail);
        //获取数据
        Intent intent = getIntent();
        String str = intent.getStringExtra("question");
        Gson gson = new Gson();
        question = gson.fromJson(str,WrongQuestion.class);
        Log.e("获取到的数据111",question.toString());
    }
}