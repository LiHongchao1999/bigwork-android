package com.example.homeworkcorrect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.chat.CircleImageView;
import com.example.homeworkcorrect.filter.GifSizeFilter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.List;

public class SelfInformationActivity extends AppCompatActivity {
    private PopupWindow popupWindow=null;
    private String path;//头像路径
    private CircleImageView headImg;
    private String imgUrl;//拍照后图片地址
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_information);
        headImg = findViewById(R.id.tv_head_img);
        if(UserCache.userImg.equals("") || UserCache.userImg==null){
            headImg.setImageDrawable(getResources().getDrawable(R.drawable.head));
        }else{
            Bitmap bitmap = BitmapFactory.decodeFile(UserCache.userImg);
            headImg.setImageBitmap(bitmap);
}

    }

    public void onClicked(View view){
        switch (view.getId()){
            case R.id.self_return:
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
            case R.id.change_real_name://点击修改真实姓名
                Intent intent1 = new Intent();
                intent1.setClass(SelfInformationActivity.this,RealNameActivity.class);
                startActivityForResult(intent1,1);
                break;
            case R.id.change_sex://点击修改性别
                Intent intent2 = new Intent();
                intent2.setClass(SelfInformationActivity.this,SexActivity.class);
                startActivityForResult(intent2,1);
                break;
            case R.id.change_id_card://点击修改身份证号
                Intent intent3 = new Intent();
                intent3.setClass(SelfInformationActivity.this,IdentityNumberActivity.class);
                startActivityForResult(intent3,1);
                break;
            case R.id.change_phone://点击修改电话
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
        RelativeLayout root = findViewById(R.id.root);
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
        if (requestCode==10 &&resultCode==RESULT_OK){
            //获取到图片
            List<Uri> mSelected = Matisse.obtainResult(data);
            path = ImageTool.getRealPathFromUri(this,mSelected.get(0));
            Log.e("图片地址",path);
            UserCache.userImg = path;
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
            UserCache.userImg = imgUrl;
            Log.e("获取到的图片地址",imgUrl);
            //修改头像
            headImg.setImageBitmap(bitmap);
        }
    }
}