package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
                String phonenum = phonenumber.getText().toString();
                Log.e("phonenum",phonenum);
                Intent intent = new Intent();
                intent.putExtra("phonenum",phonenum);
                setResult(6,intent);
                finish();
                break;
        }
    }
}
