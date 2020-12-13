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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginWithPasswordActivity extends AppCompatActivity {
    private EditText etphone; //手机号
    private EditText etpassword; //密码
    private OkHttpClient okHttpClient;
    private TextView tvnickname;
    private Button login;//登录
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    String str = msg.obj.toString();
                    UserCache.user = new Gson().fromJson(str,User.class);
                    //跳转到个人页面
                    Intent intent = new Intent(LoginWithPasswordActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("mine",1+"");
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_password);

        //1.创建OkHttpClient对象
        okHttpClient = new OkHttpClient();
        getViews();
        login.setBackground(getResources().getDrawable(R.drawable.button_style_invalid));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postLogin();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    /*
    * 获取控件引用
    * */
    private void getViews() {
        etphone = findViewById(R.id.et_numberwithpassword);
        etpassword = findViewById(R.id.et_password);
        login = findViewById(R.id.bt_loginwithpass);
    }

    private Handler mainHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            int i=msg.what;
            switch(i){
                case 0:
                    Toast.makeText(getApplicationContext(), "用户名或密码错误",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    //登录逻辑
    public void postLogin() throws JSONException {
        //2.创建Request请求对象
        //请求体是字符串
        User user = new User();
        user.setPhoneNumber(etphone.getText().toString());
        user.setPassword(etpassword.getText().toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),new Gson().toJson(user));
        //3.创建Call对象
        Request request = new Request.Builder()
                .post(requestBody)//请求方式为POST
                .url(IP.CONSTANT+"UserLoginServlet")
                .build();
        final Call call = okHttpClient.newCall(request);
        //4.提交请求并返回响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时回调
                Log.e("登录请求结果","失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功时回调
                Log.e("登录请求结果","成功");
                //从服务器端获取到JSON格式字符串
                String str = response.body().string();
                Log.e("LoginWithPassword",str);
                Message msg = new Message();
                msg.what=1;
                msg.obj=str;
                handler.sendMessage(msg);
            }
        });
    }
}
