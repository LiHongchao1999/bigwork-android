package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.homeworkcorrect.entity.School;

public class ShowSchoolInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_school_info);
        //接收传递的数据
        Intent request = getIntent();
        final School school = (School) request.getSerializableExtra("school");

    }
}