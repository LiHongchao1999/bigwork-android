package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.entity.School;

public class ShowSchoolInfoActivity extends AppCompatActivity {
    private ImageView sch_img;
    private TextView sch_name;
    private TextView sch_content;
    private TextView sch_address;
    private TextView sch_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_school_info);
        //接收传递的数据
        Intent request = getIntent();
        final School school = (School) request.getSerializableExtra("school");

        sch_img = findViewById(R.id.sch_img);
        sch_name = findViewById(R.id.sch_name);
        sch_content = findViewById(R.id.sch_content);
        sch_address = findViewById(R.id.sch_address);
        sch_phone = findViewById(R.id.sch_phone);

        Glide.with(this)
                .load("https://ftp.bmp.ovh/imgs/2020/11/7d02f8162e4c204e.png")
                .into(sch_img);

        sch_name.setText(school.getName());
        sch_content.setText(school.getContent());
        sch_address.setText(school.getAddress());
        sch_phone.setText(school.getPhone());
    }
}