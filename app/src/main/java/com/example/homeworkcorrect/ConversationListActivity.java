package com.example.homeworkcorrect;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;

import java.io.IOException;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
* 会话列表
* */
public class ConversationListActivity extends FragmentActivity {
    private User currentUser;
    private OkHttpClient okHttpClient;
    private boolean isSelect;//判断是否查询成功
    private UserInfo userInfo;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    RongIM.getInstance().setCurrentUserInfo(userInfo);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        okHttpClient = new OkHttpClient();
    showConversationList();

    }
    private void showConversationList() {
        // 是否缓存用户信息. true 缓存, false 不缓存
        // 1. <span style="color:red">当设置 true 后, 优先从缓存中获取用户信息.
        // 2. 更新用户信息, 需调用 RongIM.getInstance().refreshUserInfoCache(userInfo)
        boolean isCacheUserInfo = false;
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            /**
             * 获取设置用户信息. 通过返回的 userId 来封装生产用户信息.
             * @param userId 用户 ID
             */
            @Override
            public UserInfo getUserInfo(String userId) {
                //根据userId去获取服务端的用户的昵称和头像
                Log.e("123",userId);
                findUserById(userId);
                return null;
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
    private void findUserById(String userId) {
        //请求体是普通的字符串
        //3、创建请求对象
        Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .url(IP.CONSTANT+"GetChatInfoServlet?chat_id="+userId)
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
                Log.e("123",str);
                User user  = new Gson().fromJson(str,User.class);
                UserInfo info= new UserInfo(userId,user.getNickname(),Uri.parse(IP.CONSTANT+"images/"+user.getImage()));
                Log.e("info",info.toString());
                runOnUiThread(() -> RongIM.getInstance().refreshUserInfoCache(info));
            }
        });
    }
    /*
     * 从服务端获取当前用户昵称和头像
     * */
    private void findCurrentUserById(String userId) {
        //请求体是普通的字符串
        //3、创建请求对象
        Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .url(IP.CONSTANT+"GetChatInfoServlet?chat_id="+userId)
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
                Log.e("123",str);
                User user  = new Gson().fromJson(str,User.class);
                userInfo = new UserInfo(userId,user.getNickname(),Uri.parse(IP.CONSTANT+"images/"+user.getImage()));
                Message msg = new Message();
                msg.what=1;
                handler.sendMessage(msg);
            }
        });
    }
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.return_list:
                finish();
                break;
        }
    }
}