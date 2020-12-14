package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.homeworkcorrect.cache.UserCache;

import io.rong.imkit.RongIM;

public class SettingActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = findViewById(R.id.tool);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button logoffButton = findViewById(R.id.logoff);
        logoffButton.setOnClickListener(new View.OnClickListener() {//点击注销登录
            @Override
            public void onClick(View view) {
                //断开数据库连接
                RongIM.getInstance().disconnect();
                UserCache.user = null;
                Intent intent = new Intent();
                setResult(666,intent);
                finish();
            }
        });
    }
}