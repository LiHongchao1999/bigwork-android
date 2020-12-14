package com.example.homeworkcorrect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.chat.CircleImageView;
import com.example.homeworkcorrect.entity.Circle;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;

import java.io.IOException;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HisselfInfoActivity extends AppCompatActivity {
    private Gson gson;
    private Circle circle;
    private User user;
    private CircleImageView headImg;//头像
    private TextView nickName;//昵称
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    String str = msg.obj.toString();
                    user = gson.fromJson(str,User.class);
                    //给控件赋值
                    Glide.with(HisselfInfoActivity.this).load(IP.CONSTANT+"userImage/"+user.getImage()).into(headImg);
                    nickName.setText(user.getNickname());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisself_info);
        //获取控件引用
        getViews();
        gson = new Gson();
        //获取信息
        Intent intent = getIntent();
        String str = intent.getStringExtra("self");
        circle = gson.fromJson(str,Circle.class);
        //从服务端获取其信息
        getUserInfo();
    }

    private void getViews() {
        headImg = findViewById(R.id.his_img);
        nickName = findViewById(R.id.nick_name);
    }
    /*
     * 访问服务端获取信息
     * */
    private void getUserInfo() {
        //请求体是普通的字符串
        //3、创建请求对象
        Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .url(IP.CONSTANT+"GetUserInfoServlet?id="+ circle.getUserId())
                .build();
        //4、创建Call对象，发送请求，并接受响应
        Call call = new OkHttpClient().newCall(request);
        //如果使用异步请求，不需要手动使用子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时候回调
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功以后回调
                String str = response.body().string();//字符串数据
                Log.e("用户的个人信息",str);
                Message msg = new Message();
                msg.what=1;
                msg.obj= str;
                handler.sendMessage(msg);
            }
        });
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.info_back://点击返回
                finish();
                break;
            case R.id.start_chat://点击聊天
                UserInfo info= new UserInfo(user.getChat_id(),user.getNickname(), Uri.parse(IP.CONSTANT+"userImage/"+user.getImage()));
                RongIM.getInstance().refreshUserInfoCache(info);
                Conversation.ConversationType conversationType  = Conversation.ConversationType.PRIVATE;;
                RongIM.getInstance().startConversation(this, conversationType, info.getUserId(), info.getName(), null);
                break;
        }
    }
}