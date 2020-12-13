package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText passwrod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //获取控件
        passwrod = findViewById(R.id.et_password);
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.iv_return4://点击返回
                finish();
                break;
            case R.id.bt_finish4: //点击提交
                if(passwrod.getText().equals("")){//输入的为空
                    Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show();
                }else{
                    String password = passwrod.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("pw",password);
                    setResult(10,intent);
                    finish();
                }
                break;
        }
    }
}