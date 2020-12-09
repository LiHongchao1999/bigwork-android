package com.example.homeworkcorrect;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.homeworkcorrect.adapter.HomeworkAdapter;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.entity.Homework;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
* 会话列表
* */
public class ConversationListActivity extends FragmentActivity {
    private User currentUser;
    private OkHttpClient okHttpClient;
    private boolean isSelect;//判断是否查询成功
    private String path;
    private UserInfo userInfo;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    String str = msg.obj.toString();
                    Gson gson = new Gson();
                    currentUser = gson.fromJson(str,User.class);
                    isSelect = true;
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        okHttpClient = new OkHttpClient();
        String token = "uz3sI8B+jMzHJq0T9NZX3v6UeDRw2XjWvAsaXryD+a85hF2WyKf5+g==@sknu.cn.rongnav.com;sknu.cn.rongcfg.com";
              RongIMClient.connect(token, new RongIMClient.ConnectCallbackEx() {
            /**
             * 数据库回调
             * @param code 数据库打开状态. DATABASE_OPEN_SUCCESS 数据库打开成功; DATABASE_OPEN_ERROR 数据库打开失败
             */
            @Override
            public void OnDatabaseOpened(RongIMClient.DatabaseOpenStatus code) {
                Log.e("OnDatabaseOpened","数据库打开");
            }
            /**
             * token 无效
             */
            @Override
            public void onTokenIncorrect() {
                Log.e("onTokenIncorrect","无效");
            }
            /**
             * 成功回调
             * @param userId 当前用户 ID
             */
            @Override
            public void onSuccess(String userId) {
                Log.e("onSuccess",userId+"xcy");
                //获取用户token
                showConversationList();
            }
            /**
             * 错误回调
             * @param errorCode 错误码
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("onError",errorCode+"");
            }
        });

    }
    private void showConversationList() {
        // 是否缓存用户信息. true 缓存, false 不缓存
        // 1. <span style="color:red">当设置 true 后, 优先从缓存中获取用户信息.
        // 2. 更新用户信息, 需调用 RongIM.getInstance().refreshUserInfoCache(userInfo)
        boolean isCacheUserInfo = true;
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            /**
             * 获取设置用户信息. 通过返回的 userId 来封装生产用户信息.
             * @param userId 用户 ID
             */
            @Override
            public UserInfo getUserInfo(String userId) {
                //根据userId去获取服务端的用户的昵称和头像
                UserInfo info = new UserInfo();
                Thread.yield();
                return findUserById(userId);
            }

        }, isCacheUserInfo);
        //修改用户用户名和头像
        /*UserInfo userInfo = new UserInfo(2+"", "薛程元", null);
        RongIM.getInstance().refreshUserInfoCache(userInfo);*/
        ConversationListFragment conversationListFragment=new ConversationListFragment();
        // 此处设置 Uri. 通过 appendQueryParameter 去设置所要支持的会话类型. 例如
        // .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(),"false")
        // 表示支持单聊会话, false 表示不聚合显示, true 则为聚合显示
        Uri uri = Uri.parse("rong://" +
                getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                .build();
        conversationListFragment.setUri(uri);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, conversationListFragment);
        transaction.commit();
    }
    /*
    * 从服务端获取用户昵称和头像
    * */
    private UserInfo findUserById(String userId) {
        //请求体是普通的字符串
        RequestBody requestBody = RequestBody.create(MediaType.parse(
                "text/plain;charset=utf-8"),"");
        //3、创建请求对象
        Request request = new Request.Builder()
                .post(requestBody)  //调用post方法表示请求方式为post请求   put（.put）
                .url(IP.CONSTANT+"GetChatInfoServlet?chat_id=10000")
                .build();
        //4、创建Call对象，发送请求，并接受响应
        Call call = okHttpClient.newCall(request);
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
                Log.e("异步请求的结果",str);
                //不能直接修改用户界面
                //如果要修改用户界面需要使用Handler 或者使用EventBus
            }
        });
    }

    private String saveImage(Bitmap image) {
        String saveImagePath = null;
        Random random = new Random();
        String imageFileName = System.currentTimeMillis() + ".jpg";
        String dirName = "images";
        File storageDir = new File(this.getFilesDir(), dirName);
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
            Log.e("尝试创建文件夹", success + "");
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            saveImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fout = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return saveImagePath;
    }
    public void requestAllPower() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.return_list:
                finish();
                break;
        }
    }
}