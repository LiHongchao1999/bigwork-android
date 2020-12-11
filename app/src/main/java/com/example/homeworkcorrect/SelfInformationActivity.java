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
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
    private String imgUrl;//拍照后图片地址
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_information);
        okHttpClient = new OkHttpClient();
        //从服务端获取信息
        getUserInfo();
        //获取控件引用
        getViews();
    }
    /*
    * 获取控件引用
    * */
    private void getViews() {
        headImg = findViewById(R.id.tv_head_img);
        nickName = findViewById(R.id.tv_nickname);
        sex = findViewById(R.id.tv_sex);
        phone = findViewById(R.id.tv_phonenum);
    }

    /*
    * 访问服务端获取信息
    * */
    private void getUserInfo() {
        //请求体是普通的字符串
        //3、创建请求对象
        Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .url(IP.CONSTANT+"GetUserInfoServlet?id="+UserCache.userId)
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

            }
        });
    }

    public void onClicked(View view){
        switch (view.getId()){
            case R.id.self_return://点击返回
                Intent intent8 = new Intent();
                setResult(20,intent8);
                finish();
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
            case R.id.bt_saveInfo://向服务器端提交一次性用户信息
                try {
                    postChangeUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private Handler mainHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            int i=msg.what;
            switch(i){
                case 0:
                    Toast.makeText(getApplicationContext(), "保存成功",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

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

        }else if(requestCode==1&&resultCode==4){//修改性别

        }else if(requestCode==1&&resultCode==6){//修改点好

        }else if(requestCode==1&&resultCode==7){//修改密码

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
            UserCache.userImg=path;
        }
        if (requestCode==20){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            popupWindow.dismiss();
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
            imgUrl = ImageTool.getRealPathFromUri(this,uri);
            Log.e("获取到的图片地址",imgUrl);
            //修改头像
            headImg.setImageBitmap(bitmap);
            UserCache.userImg=imgUrl;
        }
    }
    /*
    * 向服务器发送修改的内容
    * */
    public void postChangeUserInfo() throws JSONException {
        //2.创建Request请求对象
        //请求体是普通字符串

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),"");
        //3.创建Call对象
        Request request = new Request.Builder()
                .post(requestBody)//请求方式为POST
                .url(IP.CONSTANT)
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
                Message message = new Message();
                message.what=0;
                mainHandler.sendMessage(message);

            }
        });
    }
}