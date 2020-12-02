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
    private EditText etnickName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        //获取控件
        etnickName = findViewById(R.id.et_nickname);
    }
    /*
    * 单机事件
    * */
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.iv_return:
                finish();
                break;
            case R.id.bt_finish:
                String nickname = etnickName.getText().toString();
                Log.e("nickname",nickname);
                Intent intent = new Intent();
                intent.putExtra("nickname",nickname);
                setResult(2,intent);
                finish();
                break;
        }
    }
}
