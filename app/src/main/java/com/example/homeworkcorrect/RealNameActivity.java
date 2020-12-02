package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class RealNameActivity extends AppCompatActivity {
    private EditText etrealName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name);

        //获取控件
        etrealName = findViewById(R.id.et_realname);
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.iv_return1:
                finish();
                break;
            case R.id.bt_finish1:
                String realname = etrealName.getText().toString();
                Log.e("realname",realname);
                Intent intent = new Intent();
                intent.putExtra("realname",realname);
                setResult(3,intent);
                finish();
                break;
        }
    }
}
