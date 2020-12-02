package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SexActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        radioGroup = findViewById(R.id.RG_sex);
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.iv_return2:
                break;
            case R.id.bt_finish2:
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                String sex = radioButton.getText().toString();
                Log.e("sex",sex);
                Intent intent = new Intent();
                intent.putExtra("sex",sex);
                setResult(4,intent);
                finish();
                break;
        }
    }
}
