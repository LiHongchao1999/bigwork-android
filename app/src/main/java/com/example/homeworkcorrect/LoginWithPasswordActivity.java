package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoginWithPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_password);

        final ImageView ivprotocol = findViewById(R.id.iv_protocol);
        final Button btpassword = findViewById(R.id.bt_loginwithpass);
        ImageView ivclose = findViewById(R.id.iv_close);
        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivprotocol.setImageDrawable(getResources().getDrawable(R.drawable.circle));
        btpassword.setBackground(getResources().getDrawable(R.drawable.button_style_invalid));
        btpassword.setEnabled(false);

        ivprotocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("a","aaaa");
                if(ivprotocol.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.circle).getConstantState())){
                    Log.e("get","aaaa");
                    ivprotocol.setImageDrawable(getResources().getDrawable(R.drawable.checked));
                    btpassword.setBackground(getResources().getDrawable(R.drawable.button_style_normal));
                    btpassword.setEnabled(true);
                }else if(ivprotocol.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.checked).getConstantState())){
                    ivprotocol.setImageDrawable(getResources().getDrawable(R.drawable.circle));
                    btpassword.setBackground(getResources().getDrawable(R.drawable.button_style_invalid));
                    btpassword.setEnabled(false);
                }
            }
        });
    }
}
