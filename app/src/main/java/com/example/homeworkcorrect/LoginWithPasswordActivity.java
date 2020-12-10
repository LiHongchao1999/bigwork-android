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
    EditText etphone;
    EditText etpassword;
    private OkHttpClient okHttpClient;
    User temUser;
    TextView tvnickname;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_password);

        //1.创建OkHttpClient对象
        okHttpClient = new OkHttpClient();

        final ImageView ivprotocol = findViewById(R.id.iv_protocol);
        final Button btpassword = findViewById(R.id.bt_loginwithpass);
        etphone = findViewById(R.id.et_numberwithpassword);
        etpassword = findViewById(R.id.et_password);
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

        btpassword.setOnClickListener(new View.OnClickListener() {
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


    //将对象转换为JSON类型数据
    public String object2JSON(User user) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phoneNumber",user.getPhoneNumber());
        jsonObject.put("password",user.getPassword());
        return jsonObject.toString();
    }
    //把JSON格式的字符串解析成boolean
    public boolean json2Boolean(String jsonStr) throws JSONException {
        boolean b = new Gson().fromJson(jsonStr,boolean.class);
        return b;
    }
    //把JSON格式的字符串解析成用户对象
    public User json2Object(String jsonStr) throws JSONException {
        User user = new User();
        JSONObject jsonObject = new JSONObject(jsonStr);
        user.setId(jsonObject.getInt("id"));
        user.setPhoneNumber(jsonObject.getString("phoneNumber"));
        user.setPassword(jsonObject.getString("password"));
        if(jsonObject.getString("nickname")!=null){
            user.setNickname(jsonObject.getString("nickname"));
        }
        if(jsonObject.getString("image")!=null){
            user.setImage(jsonObject.getString("nickname"));
        }
        if (jsonObject.getString("qqNumber")!=null){
            user.setQqNumber(jsonObject.getString("qqNumber"));
        }
        if(jsonObject.getString("grade")!=null){
            user.setGrade(jsonObject.getString("grade"));
        }
        if(jsonObject.getString("sex")!=null){
            user.setSex(jsonObject.getString("sex"));
        }
        if(jsonObject.getString("chat_id")!=null){
            user.setChat_id(jsonObject.getString("chat_id"));
        }
        if(jsonObject.getString("chat_token")!=null){
            user.setChat_token(jsonObject.getString("chat_token"));
        }
        if(jsonObject.getString("weChatNumber")!=null){
            user.setWeChatNumber(jsonObject.getString("weChatNumber"));
        }
        return user;
    }

    //登录逻辑
    public void postLogin() throws JSONException {
        //2.创建Request请求对象
        //请求体是字符串
        User user = new User(etphone.getText().toString(),etpassword.getText().toString());
        Log.e("PhoneAndPassword",etphone.getText().toString()+etpassword.getText().toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),this.object2JSON(user));
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
                String jsonString = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    Log.e("jsonString",jsonString);
                    id = jsonObject.getInt("id");
                    if (id!=0){
                        Log.e("id",id+"");
                        temUser = json2Object(jsonString);
                        LoginActivity.user=temUser;
                        Intent intent = new Intent(LoginWithPasswordActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                    }else if (id==0){
                        Message message = new Message();
                        message.what=0;
                        mainHandler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
