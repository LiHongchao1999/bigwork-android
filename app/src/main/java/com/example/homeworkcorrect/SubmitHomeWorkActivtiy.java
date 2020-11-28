package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeworkcorrect.adapter.CustomImgListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubmitHomeWorkActivtiy extends AppCompatActivity {
    private Spinner spinner;//选择科目
    private TextView tvName;
    private Button btnEndDate;//结束日期
    private TextView tvDate;//开始日期
    private Button btnSubmit;//提交按钮
    private List<String> photoList = new ArrayList<>();
    private Button again;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_home_work_activtiy);
        getViews();
        String subject=spinner.getSelectedItem().toString();
        String currentdate=tvDate.getText().toString();
        Intent intent = getIntent();
        Bundle photoBundle = intent.getBundleExtra("photoList");
        ArrayList<String> photoList = photoBundle.getStringArrayList("photoList");
        CustomImgListAdapter adapter = new CustomImgListAdapter(this,photoList,R.layout.img_list_item);
        GridView gridView = findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
    }

    private void getViews() {
        spinner=findViewById(R.id.sp_subject);
        tvDate=findViewById(R.id.tv_date);
        btnEndDate=findViewById(R.id.btn_choose_date);
        btnSubmit=findViewById(R.id.btn_submit);
        again = findViewById(R.id.again);
        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoDialog infoDialog = new InfoDialog.Builder(SubmitHomeWorkActivtiy.this,R.layout.info_dialog_green)
                        .setTitle("Done")
                        .setMessage("提交成功")
                        .setButton("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(SubmitHomeWorkActivtiy.this,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                        ).create();
                infoDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_white);
                infoDialog.show();
            }
        });
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        tvDate.setText(formatter.format(date));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}