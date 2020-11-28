package com.example.homeworkcorrect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworkcorrect.adapter.CustomImgListAdapter;
import com.example.homeworkcorrect.adapter.CustomVideoListAdapter;
import com.example.homeworkcorrect.filter.GifSizeFilter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.ArrayList;
import java.util.List;

public class PublishVideoActivity extends AppCompatActivity {
    private List<String> imgUrls = new ArrayList<>(); //存放选择的图片
    private TextView cancel;//取消
    private TextView send; //发表
    private ScrollableGridView gridView; //图片
    private EditText content;//输入的内容
    private CustomVideoListAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_image);
        //获取控件引用
        getViews();
        customAdapter = new CustomVideoListAdapter(this,imgUrls,R.layout.video_list_item);
        gridView.setAdapter(customAdapter);
        //图片选择
        //动态申请权限
        ActivityCompat.requestPermissions(PublishVideoActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                20);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==20){
            Matisse.from(PublishVideoActivity.this)
                    .choose(MimeType.ofVideo()) //只显示视频
                    .countable(true) //显示选择的数量
                    .maxSelectable(9)
                    .gridExpectedSize(350) //图片显示在列表中的大小
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.8f)//缩放比例
                    .imageEngine(new GlideEngine()) //使用图片加载引擎
                    .theme(R.style.Matisse_Dracula) //主题
                    .capture(false)//是否提供拍照功能
                    .forResult(50);//设置作为标记的请求码
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==50 && resultCode==RESULT_OK){
            List<Uri> mSelected = Matisse.obtainResult(data);
            for(Uri uri : mSelected){
                String path = ImageTool.getRealPathFromUri(this,uri);
                Log.e("图片地址",path);
                imgUrls.add(path);
            }
            customAdapter.notifyDataSetChanged();
        }
    }
    /*
     * 获取控件
     * */
    private void getViews() {
        cancel = findViewById(R.id.sp_cancel);
        send = findViewById(R.id.sp_send);
        gridView = findViewById(R.id.sp_img_list);
        content = findViewById(R.id.sp_edit_content);
    }
    /*
     * 单机事件
     * */
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.zp_cancel://点击取消
                finish();
                break;
            case R.id.zp_send: //点击发表
                Intent intent = new Intent();
                //设置返回数据
                setResult(350,intent);
                finish();
                break;
        }
    }
}