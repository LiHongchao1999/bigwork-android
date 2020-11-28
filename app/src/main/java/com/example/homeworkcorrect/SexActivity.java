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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);

        final RadioGroup radioGroup = findViewById(R.id.RG_sex);
        ImageView ivreturn = findViewById(R.id.iv_return);

        ivreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btfinish = findViewById(R.id.bt_finish);
        btfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                String sex = radioButton.getText().toString();
                Log.e("sex",sex);
                Intent intent = new Intent();
                intent.putExtra("sex",sex);
                setResult(4,intent);
                finish();
            }
        });

    }

}
