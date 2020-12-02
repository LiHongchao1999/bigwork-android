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
    private EditText etidentity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_number);
        //获取控件
        etidentity = findViewById(R.id.et_identitynumber);
    }
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.iv_return3:
                finish();
                break;
            case R.id.bt_finish3:
                String identity = etidentity.getText().toString();
                Log.e("identity",identity);
                Intent intent = new Intent();
                intent.putExtra("identity",identity);
                setResult(5,intent);
                finish();
                break;
        }
    }
}
