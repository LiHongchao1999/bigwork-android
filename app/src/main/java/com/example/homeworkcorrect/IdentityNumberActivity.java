package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class IdentityNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_number);
        //获取控件
        ImageView ivreturn = findViewById(R.id.iv_return);
        Button btfinish = findViewById(R.id.bt_finish);
        final EditText etidentity = findViewById(R.id.et_identitynumber);
        ivreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String identity = etidentity.getText().toString();
                Log.e("identity",identity);
                Intent intent = new Intent();
                intent.putExtra("identity",identity);
                setResult(5,intent);
                finish();
            }
        });
    }
}
