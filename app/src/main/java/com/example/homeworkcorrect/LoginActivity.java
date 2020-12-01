package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeworkcorrect.fragment.MyFragmentMainContent;

public class LoginActivity extends AppCompatActivity {
    private Button login;

    String APPKEY = "319a190fd6280";
    String APPSECRET = "54f8a204b312c31507ed4533104f51ed";
    //获取用到的控件
    EditText etphone;
    EditText etCode;
    TextView tvlogin;
    Button btCode;

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText editText=findViewById(R.id.et_phone);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
