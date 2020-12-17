package com.example.homeworkcorrect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.chat.CircleImageView;
import com.example.homeworkcorrect.entity.User;
import com.example.homeworkcorrect.filter.GifSizeFilter;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SelfInformationActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    private PopupWindow popupWindow=null;
    private String path;//头像路径
    private CircleImageView headImg; //头像
    private TextView nickName;//昵称
    private TextView sex;//性别
    private TextView phone;//手机号
    private TextView password;//密码
    private String imgUrl;//拍照后图片地址
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1://图片上传成功
                    String str = msg.obj.toString();
                    if(str.equals("true")){
                        UserCache.user.setImage(path);
                        //修改用户信息
                        try {
                            postChangeUserInfo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(SelfInformationActivity.this,"上传失败",Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2://修改成功
                    String str1 = msg.obj.toString();
                    if(str1.equals("true")){//跳转到个人页面
                        Intent intent = new Intent();
                        setResult(20,intent);
                        finish();
                    }else{
                        Toast.makeText(SelfInformationActivity.this,"修改失败",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_information);
        okHttpClient = new OkHttpClient();
        //获取控件引用
        getViews();
        //给控件赋值
        Glide.with(this).load(IP.CONSTANT+"userImage/"+UserCache.user.getImage()).into(headImg);
        if(UserCache.user.getNickname()==null || UserCache.user.getNickname().equals("")){
            nickName.setText("");
        }else{
            nickName.setText(UserCache.user.getNickname()+"");
        }
        if(UserCache.user.getSex()==null || UserCache.user.getSex().equals("")){
            sex.setText("");
        }else{
            sex.setText(UserCache.user.getSex()+"");
        }
        if(UserCache.user.getPhoneNumber()==null || UserCache.user.getPhoneNumber().equals("")){
            phone.setText("");
        }else{
            phone.setText(UserCache.user.getPhoneNumber()+"");
        }
        if(UserCache.user.getPassword()==null || UserCache.user.getPassword().equals("")){
            password.setText("");
        }else{
            String pass = "";
            for(int i=0;i<UserCache.user.getPassword().length();i++){
                pass = pass+"*";
            }
            password.setText(pass);
        }
    }
    /*
    * 获取控件引用
    * */
    private void getViews() {
        headImg = findViewById(R.id.tv_head_img);
        nickName = findViewById(R.id.tv_nickname);
        sex = findViewById(R.id.tv_sex);
        phone = findViewById(R.id.tv_phonenum);
        password = findViewById(R.id.tv_password);
    }

    public void onClicked(View view) throws JSONException {
        switch (view.getId()){
            case R.id.self_return://点击返回
                //修改用户用户名和头像
                UserInfo userInfo = new UserInfo(UserCache.user.getChat_id(), UserCache.user.getNickname(), Uri.parse(IP.CONSTANT+"userImage/"+UserCache.user.getImage()));
                RongIM.getInstance().refreshUserInfoCache(userInfo);
                if(path==null || path.equals("")){//表示没有修改头像
                    postChangeUserInfo();
                }else{
                    uploadImage();
                }
                break;
            case R.id.change_img://点击头像
                showPopupWindow();
                break;
            case R.id.change_nickname://点击修改昵称
                Intent intent =new Intent();
                intent.setClass(SelfInformationActivity.this,NickNameActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.change_sex://点击修改性别
                Intent intent2 = new Intent();
                intent2.setClass(SelfInformationActivity.this,SexActivity.class);
                startActivityForResult(intent2,1);
                break;
            case R.id.change_phone://点击修改电话
                Intent intent4 = new Intent();
                intent4.setClass(SelfInformationActivity.this,PhoneNumberActivity.class);
                startActivityForResult(intent4,1);
                break;
            case R.id.change_password://修改密码
                Intent intent5 = new Intent();
                intent5.setClass(SelfInformationActivity.this,ChangePasswordActivity.class);
                startActivityForResult(intent5,1);
                break;
        }
    }

    public void showPopupWindow(){
        //创建PopupWindow对象
        popupWindow = new PopupWindow(this);
        //设置弹出窗口的宽度
        popupWindow.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        //设置它的视图
        View view = getLayoutInflater().inflate(R.layout.popupwindow,null);
        //获取控件
        Button cancel = view.findViewById(R.id.bt_cancel); //取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        Button camera = view.findViewById(R.id.bt_camera); //拍照
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(SelfInformationActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        50);
            }
        });
        Button mapStorage = view.findViewById(R.id.bt_photo); //图库
        mapStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态申请权限
                ActivityCompat.requestPermissions(SelfInformationActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
        });
        //设置视图当中控件的属性和监听器
        popupWindow.setContentView(view);
        //显示PopupWindow（必须指定显示的位置）
        LinearLayout root = findViewById(R.id.root);
        popupWindow.showAtLocation(root,Gravity.NO_GRAVITY,0,0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100){
            Matisse.from(SelfInformationActivity.this)
                    .choose(MimeType.ofImage()) //只显示图片
                    .countable(true) //显示选择的数量
                    .maxSelectable(1) //最多可选择的数量
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(350) //图片显示在列表中的大小
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.8f)//缩放比例
                    .imageEngine(new GlideEngine()) //使用图片加载引擎
                    .theme(R.style.Matisse_Dracula) //主题
                    .capture(false)//是否提供拍照功能
                    .forResult(10);//设置作为标记的请求码
        }
        if (requestCode==50){
            //请求系统相机
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivityForResult(intent,20);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){ //修改昵称
            String nickName1 = data.getStringExtra("nickname");
            if(nickName1==null){
                UserCache.user.setNickname(null);
                nickName.setText("");
            }else if(nickName1.equals("")){
                UserCache.user.setNickname("");
                nickName.setText("");
            }else{
                UserCache.user.setNickname(nickName1);
                nickName.setText(nickName1);
            }
        }else if(requestCode==1&&resultCode==4){//修改性别
            String sex1 = data.getStringExtra("sex");
            if(sex1==null){
                UserCache.user.setSex(null);
                sex.setText("");
            }else if(sex1.equals("")){
                UserCache.user.setSex("");
                sex.setText("");
            }else{
                UserCache.user.setSex(sex1);
                sex.setText(sex1);
            }
        }else if(requestCode==1&&resultCode==6){//修改手机号
            String phone1 = data.getStringExtra("phonenum");
            UserCache.user.setPhoneNumber(phone1);
            phone.setText(phone1);
        }else if(requestCode==1&&resultCode==10){//修改密码
            String password1 = data.getStringExtra("pw");
            UserCache.user.setPassword(password1);
            String str="";
            for(int i=0;i<password1.length();i++){
                str = str+"*";
            }
            password.setText(str);
        }
        if (requestCode==10 &&resultCode==RESULT_OK){
            //获取到图片
            List<Uri> mSelected = Matisse.obtainResult(data);
            path = ImageTool.getRealPathFromUri(this,mSelected.get(0));
            Log.e("图片地址",path);
            //弹出框消失
            popupWindow.dismiss();
            //修改头像
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            headImg.setImageBitmap(bitmap);

        }
        if (requestCode==20){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            popupWindow.dismiss();
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
            imgUrl = ImageTool.getRealPathFromUri(this,uri);
            Log.e("获取到的图片地址",imgUrl);
            path = imgUrl;
            //修改头像
            headImg.setImageBitmap(bitmap);
        }
    }
    /*
    * 上传头像
    * */
    private void uploadImage() {
        long time = Calendar.getInstance().getTimeInMillis();
        Log.e("获取到的时间",time+"");
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"),new File(path));
        Log.e("path",path+"");
        Request request = new Request.Builder().post(body).url(IP.CONSTANT+"UploadUserImageServlet?imgName="+time+".jpg").build();
        path = time+".jpg";
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("返回结果",str);
                Message msg = new Message();
                msg.what=1;
                msg.obj = str;
                handler.sendMessage(msg);
            }
        });
    }
    /*
    * 向服务器发送修改的内容
    * */
    public void postChangeUserInfo() throws JSONException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),new Gson().toJson(UserCache.user));
        //3.创建Call对象
        Request request = new Request.Builder()
                .post(requestBody)//请求方式为POST
                .url(IP.CONSTANT+"UpdateUserInfoServlet")
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
                Message msg = new Message();
                msg.what=2;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //修改用户用户名和头像
            UserInfo userInfo = new UserInfo(UserCache.user.getChat_id(), UserCache.user.getNickname(), Uri.parse(IP.CONSTANT+"userImage/"+UserCache.user.getImage()));
            RongIM.getInstance().refreshUserInfoCache(userInfo);
            if(path==null || path.equals("")){//表示没有修改头像
                try {
                    postChangeUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                uploadImage();
            }
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}