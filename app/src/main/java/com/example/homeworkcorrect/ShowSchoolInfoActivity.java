package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606371190038&di=1f7e0dece43b4a4b91ae6bd7e8f479c9&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201706%2F10%2F20170610192627_yhAMN.thumb.700_0.jpeg")
                .into(sch_img);

        sch_name.setText(school.getName());
        sch_content.setText(school.getContent());
        sch_address.setText(school.getAddress());
        sch_phone.setText(school.getPhone());
    }
}