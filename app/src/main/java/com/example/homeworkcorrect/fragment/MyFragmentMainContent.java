package com.example.homeworkcorrect.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;


import com.example.homeworkcorrect.Camera2Activity;
import com.example.homeworkcorrect.ConversationListActivity;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;

import java.io.IOException;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyFragmentMainContent extends Fragment {
    private LinearLayout llcamera;
    private View view;
    private LinearLayout llrecommand;
    private ImageView ring;
    private ImageView rotate;
    private ImageView advertise;
    private TextView mUnreadNumView;//消息个数
    private ImageView sendMsg;//发送消息
    private UserInfo userInfo;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    UserInfo info = (UserInfo) msg.obj;
                    RongIM.getInstance().refreshUserInfoCache(info);
                    Conversation.ConversationType conversationType  = Conversation.ConversationType.PRIVATE;;
                    RongIM.getInstance().startConversation(getContext() , conversationType, info.getUserId(), info.getName(), null);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mymaincontent,
                container,
                false);
        //初始化融云
        initRongMessage();
        getViews();
        combineChange();
        MyListener listener=new MyListener();
        llcamera.setOnClickListener(listener);
        llrecommand.setOnClickListener(listener);
        ring.setOnClickListener(listener);
        sendMsg.setOnClickListener(listener);
        llrecommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.example);
                Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, null, null));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TITLE,"分享到");
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        });

        return view;
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
                Log.e("123",str);
                User user  = new Gson().fromJson(str,User.class);
                userInfo = new UserInfo(userId,user.getNickname(),Uri.parse(IP.CONSTANT+"images/"+user.getImage()));
                Message msg = new Message();
                msg.what=2;
                handler.sendMessage(msg);
            }
        });
    }
    /*
     * 融云消息接收，及初始化
     */
    private void initRongMessage() {
        final Conversation.ConversationType[] conversationTypes = {Conversation.ConversationType.PRIVATE, Conversation.ConversationType.DISCUSSION,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE};

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RongIM.getInstance().setOnReceiveUnreadCountChangedListener(mCountListener, conversationTypes);
            }
        }, 500);
    }
    /*
    * 设置接收到消息的个数
    * */
    public RongIM.OnReceiveUnreadCountChangedListener mCountListener = new RongIM.OnReceiveUnreadCountChangedListener() {
        @Override
        public void onMessageIncreased(int count) {
            if (count == 0) {
                mUnreadNumView.setVisibility(View.INVISIBLE);
            } else if (count > 0 && count < 100) {
                mUnreadNumView.setVisibility(View.VISIBLE);
                mUnreadNumView.setText(count + "");
            } else {
                mUnreadNumView.setVisibility(View.VISIBLE);
                mUnreadNumView.setText(R.string.no_read_message);
            }
        }
    };
    public class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.ll_camera:
                    Intent intentc=new Intent(getContext(), Camera2Activity.class);
                    startActivity(intentc);
                    break;
                case R.id.iv_ring:
                    //跳转到会话列表页面
                    Intent intent = new Intent(getContext(), ConversationListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.send_msg_www:
                    findUserById("user_1607506987137");
                    break;
            }
        }
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
                Log.e("123",str);
                User user  = new Gson().fromJson(str,User.class);
                UserInfo info= new UserInfo(userId,user.getNickname(),Uri.parse(IP.CONSTANT+"images/"+user.getImage()));
                Log.e("info",info.toString());
                Message msg = new Message();
                msg.what=1;
                msg.obj=info;
                handler.sendMessage(msg);
            }
        });
    }
    public void getViews(){
       llcamera=view.findViewById(R.id.ll_camera);
       llrecommand=view.findViewById(R.id.ll_recommand);
       rotate=view.findViewById(R.id.rotate);
       ring = view.findViewById(R.id.iv_ring);
       advertise = view.findViewById(R.id.advertise);
       RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.advertise));
       circularBitmapDrawable.setCornerRadius(200);
       advertise.setImageDrawable(circularBitmapDrawable);
       mUnreadNumView = view.findViewById(R.id.num_msg);
       sendMsg = view.findViewById(R.id.send_msg_www);
    }
    public void combineChange(){

        RotateAnimation rotateAnimation = new RotateAnimation(0.0f,360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setDuration(20000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotate.startAnimation(rotateAnimation);
    }
}
