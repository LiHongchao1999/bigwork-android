package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelfInformationActivity extends AppCompatActivity {
    private PopupWindow popupWindow=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_information);
    }

    public void buttonClicked(View view){
        switch (view.getId()){
            case R.id.iv_head:
                showPopupWindow();
                break;
            case R.id.bt_cancel:
                popupWindow.dismiss();
                break;
            case R.id.iv_nickname:
                Intent intent =new Intent();
                intent.setClass(SelfInformationActivity.this,NickNameActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.iv_realname:
                Intent intent1 = new Intent();
                intent1.setClass(SelfInformationActivity.this,RealNameActivity.class);
                startActivityForResult(intent1,1);
                break;
            case R.id.iv_sex:
                Intent intent2 = new Intent();
                intent2.setClass(SelfInformationActivity.this,SexActivity.class);
                startActivityForResult(intent2,1);
                break;
            case R.id.iv_identitynumber:
                Intent intent3 = new Intent();
                intent3.setClass(SelfInformationActivity.this,IdentityNumberActivity.class);
                startActivityForResult(intent3,1);
                break;
            case R.id.iv_phonenumber:
                Intent intent4 = new Intent();
                intent4.setClass(SelfInformationActivity.this,PhoneNumberActivity.class);
                startActivityForResult(intent4,1);
                break;
        }
    }

    public void showPopupWindow(){
        //创建PopupWindow对象
        popupWindow = new PopupWindow(this);
        //设置弹出窗口的宽度
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置它的视图
        View view = getLayoutInflater().inflate(R.layout.popupwindow,null);
        //设置视图当中控件的属性和监听器
        popupWindow.setContentView(view);
        //显示PopupWindow（必须指定显示的位置）
        LinearLayout root = findViewById(R.id.root);
        popupWindow.showAtLocation(root, Gravity.BOTTOM,0,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            String nickname = data.getStringExtra("nickname");
            Log.e("nickname",nickname);
            TextView tvnick = findViewById(R.id.tv_nickname);
            tvnick.setText(nickname);
        }else if(requestCode==1&&resultCode==3){
            String realname = data.getStringExtra("realname");
            Log.e("realname",realname);
            TextView tvreal  = findViewById(R.id.tv_realname);
            tvreal.setText(realname);
        }else if(requestCode==1&&resultCode==4){
            String sex=data.getStringExtra("sex");
            Log.e("sex",sex);
            TextView tvsex = findViewById(R.id.tv_sex);
            tvsex.setText(sex);
        }else if(requestCode==1&&resultCode==5){
            String identity = data.getStringExtra("identity");
            Log.e("identity",identity);
            TextView tvidentity = findViewById(R.id.tv_identity);
            tvidentity.setText(identity);
        }else if(requestCode==1&&resultCode==6){
            String phonenum = data.getStringExtra("phonenum");
            Log.e("phonenum",phonenum);
            TextView tvphone = findViewById(R.id.tv_phonenum);
            tvphone.setText(phonenum);
        }
    }
}