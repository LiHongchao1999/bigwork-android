package com.example.homeworkcorrect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MoneyActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    private ImageView back1;//返回
    private Button m1;
    private Button m2;
    private Button m3;
    private Button m4;
    private Button m5;
    private Button m6;
    private Button pay;
    private LinearLayout zf1;
    private LinearLayout zf2;
    private int clapMoneyNum;//充值的拍拍币数量
    private String payMethod;//支付方式
    private TextView account;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    String str = msg.obj.toString();
                    Log.e("修改拍拍币返回的结果",str);
                    Toast.makeText(MoneyActivity.this,str,Toast.LENGTH_LONG).show();
                    //修改拍拍币数量
                    UserCache.user.setClapping_money(clapMoneyNum+UserCache.user.getClapping_money());
                    //跳转到个人界面
                    Intent intent = new Intent();
                    setResult(220,intent);
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        okHttpClient = new OkHttpClient();
        getViews();
        Log.e("acount",account.toString());
        account.setText(UserCache.user.getClapping_money()+"");
    }
    /*获取控件引用*/
    private void getViews() {
        back1 = findViewById(R.id.money_return);
        m1=findViewById(R.id.m1);
        m2=findViewById(R.id.m2);
        m3=findViewById(R.id.m3);
        m4=findViewById(R.id.m4);
        m5=findViewById(R.id.m5);
        m6=findViewById(R.id.m6);
        pay = findViewById(R.id.immediate_pay);
        zf1 = findViewById(R.id.zf1);
        zf2 = findViewById(R.id.zf2);
        account = findViewById(R.id.account_user);
    }

    public void onClicked(View view){
        switch (view.getId()){
            case R.id.m1:
                clapMoneyNum = 1;
                m1.setTextColor(getResources().getColor(R.color.btn_select));
                m2.setTextColor(getResources().getColor(R.color.blackhao));
                m3.setTextColor(getResources().getColor(R.color.blackhao));
                m4.setTextColor(getResources().getColor(R.color.blackhao));
                m5.setTextColor(getResources().getColor(R.color.blackhao));
                m6.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.m2:
                clapMoneyNum = 10;
                m2.setTextColor(getResources().getColor(R.color.btn_select));
                m1.setTextColor(getResources().getColor(R.color.blackhao));
                m3.setTextColor(getResources().getColor(R.color.blackhao));
                m4.setTextColor(getResources().getColor(R.color.blackhao));
                m5.setTextColor(getResources().getColor(R.color.blackhao));
                m6.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.m3:
                clapMoneyNum = 50;
                m3.setTextColor(getResources().getColor(R.color.btn_select));
                m2.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setTextColor(getResources().getColor(R.color.blackhao));
                m4.setTextColor(getResources().getColor(R.color.blackhao));
                m5.setTextColor(getResources().getColor(R.color.blackhao));
                m6.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.m4:
                clapMoneyNum = 100;
                m4.setTextColor(getResources().getColor(R.color.btn_select));
                m2.setTextColor(getResources().getColor(R.color.blackhao));
                m3.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setTextColor(getResources().getColor(R.color.blackhao));
                m5.setTextColor(getResources().getColor(R.color.blackhao));
                m6.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.m5:
                clapMoneyNum = 500;
                m5.setTextColor(getResources().getColor(R.color.btn_select));
                m2.setTextColor(getResources().getColor(R.color.blackhao));
                m3.setTextColor(getResources().getColor(R.color.blackhao));
                m4.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setTextColor(getResources().getColor(R.color.blackhao));
                m6.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.m6:
                clapMoneyNum = 1000;
                m6.setTextColor(getResources().getColor(R.color.btn_select));
                m2.setTextColor(getResources().getColor(R.color.blackhao));
                m3.setTextColor(getResources().getColor(R.color.blackhao));
                m4.setTextColor(getResources().getColor(R.color.blackhao));
                m5.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                break;
            case R.id.zf1:
                payMethod = "微信支付";
                zf1.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                zf2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.zf2:
                payMethod = "支付宝支付";
                zf1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                zf2.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                break;
            case R.id.immediate_pay:
                InfoDialog infoDialog = new InfoDialog.Builder(MoneyActivity.this,R.layout.info_dialog_green)
                        .setTitle("Done")
                        .setMessage("支付成功")
                        .setButton("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //先让服务端进行修改
                                        try {
                                            updateMoney();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        ).create();
                infoDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_white);
                infoDialog.show();
                break;
            case R.id.money_return:
                finish();
                break;
        }
    }
    //修改逻辑
    public void updateMoney() throws JSONException {
        //2.创建Request请求对象
        //请求体是字符串
        User user = new User();
        user.setId(UserCache.user.getId());
        user.setClapping_money(clapMoneyNum+UserCache.user.getClapping_money());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),new Gson().toJson(user));
        //3.创建Call对象
        Request request = new Request.Builder()
                .post(requestBody)//请求方式为POST
                .url(IP.CONSTANT+"user/money")
                .build();
        final Call call = okHttpClient.newCall(request);
        //4.提交请求并返回响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时回调
                Log.e("修改请求结果","失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //从服务器端获取到JSON格式字符串
                String str = response.body().string();
                Log.e("Money",str);
                Message msg = new Message();
                msg.what=1;
                msg.obj=str;
                handler.sendMessage(msg);
            }
        });
    }
}