package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PhoneNumberActivity extends AppCompatActivity {
    private EditText phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        //获取控件
        phonenumber = findViewById(R.id.et_phonenumber);
    }
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return4:
                finish();
                break;
            case R.id.bt_finish4:
                if(phonenumber.getText().equals("")){//输入的为空
                    Toast.makeText(this,"手机号不能为空",Toast.LENGTH_LONG).show();
                }else if(phonenumber.getText().toString().length()!=11){//不足11位
                    Toast.makeText(this,"手机号不正确",Toast.LENGTH_LONG).show();
                }else{
                    String phonenum = phonenumber.getText().toString();
                    Log.e("phonenum",phonenum);
                    Intent intent = new Intent();
                    intent.putExtra("phonenum",phonenum);
                    setResult(6,intent);
                    finish();
                }
                break;
        }
    }
}
