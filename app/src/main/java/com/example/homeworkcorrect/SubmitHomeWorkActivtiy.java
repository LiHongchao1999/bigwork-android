package com.example.homeworkcorrect;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SubmitHomeWorkActivtiy extends AppCompatActivity {
    private Spinner spinner;//选择科目
    private TextView tvName;
    private Button btnEndDate;//结束日期
    private TextView tvDate;//开始日期
    private Button btnSubmit;//提交按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_home_work_activtiy);
        getViews();
        String subject=spinner.getSelectedItem().toString();
        String currentdate=tvDate.getText().toString();

    }

    private void getViews() {
        spinner=findViewById(R.id.sp_subject);
        tvDate=findViewById(R.id.tv_date);
        btnEndDate=findViewById(R.id.btn_choose_date);
        btnSubmit=findViewById(R.id.btn_submit);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        tvDate.setText(formatter.format(date));
    }

}