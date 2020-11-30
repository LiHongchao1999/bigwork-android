package com.example.homeworkcorrect;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class NickNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);

        //获取控件
        ImageView ivreturn = findViewById(R.id.iv_return);
        final EditText etnickname = findViewById(R.id.et_nickname);
        Button btfinish = findViewById(R.id.bt_finish);
        ivreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = etnickname.getText().toString();
                Log.e("nickname",nickname);
                Intent intent = new Intent();
                intent.putExtra("nickname",nickname);
                setResult(2,intent);
                finish();
            }
        });

    }
}
