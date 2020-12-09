package com.example.homeworkcorrect;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.homeworkcorrect.adapter.CustomAdapterImageList;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.entity.Circle;
import com.example.homeworkcorrect.filter.GifSizeFilter;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PublishCircleActivity extends AppCompatActivity {
    private List<String> imgUrls = new ArrayList<>(); //存放选择的图片
    private static final String IMG_ADD= "add"; //添加图片
    private TextView cancel;//取消
    private TextView send; //发表
    private ScrollableGridView gridView; //图片
    private EditText content;//输入的内容
    private CustomAdapterImageList customAdapter;
    private OkHttpClient okHttpClient;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_circle);
        //获取控件
        getViews();
        okHttpClient=new OkHttpClient();
        imgUrls.add(IMG_ADD);
        customAdapter = new CustomAdapterImageList(PublishCircleActivity.this,imgUrls,R.layout.img_list_item);
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (imgUrls.get(position).equals("add")) {//进行选择图片
                    if (imgUrls.size() < 9) {
                        //动态申请权限
                        ActivityCompat.requestPermissions(PublishCircleActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                200);
                    } else {
                        new Toast(PublishCircleActivity.this).makeText(PublishCircleActivity.this, "最多只能选择9张照片！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==200){
            Matisse.from(PublishCircleActivity.this)
                    .choose(MimeType.ofImage()) //只显示图片
                    .countable(true) //显示选择的数量
                    .maxSelectable(9) //最多可选择的数量
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(350) //图片显示在列表中的大小
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.8f)//缩放比例
                    .imageEngine(new GlideEngine()) //使用图片加载引擎
                    .theme(R.style.Matisse_Dracula) //主题
                    .capture(false)//是否提供拍照功能
                    .forResult(100);//设置作为标记的请求码
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK){
            List<Uri> mSelected = Matisse.obtainResult(data);
            removeItem();
            for(Uri uri : mSelected){
                String path = ImageTool.getRealPathFromUri(this,uri);
                Log.e("图片地址",path);
                imgUrls.add(path);
            }
            imgUrls.add(IMG_ADD);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i!=imgUrls.size()-1){
                        List<String> urls = new ArrayList<String>();
                        for(int j=0;j<imgUrls.size()-1;j++){
                            urls.add(imgUrls.get(j));
                        }
                        new ShowLocalImageDialog(PublishCircleActivity.this,urls,i).show();
                    }else{
                        Matisse.from(PublishCircleActivity.this)
                                .choose(MimeType.ofImage()) //只显示图片
                                .countable(true) //显示选择的数量
                                .maxSelectable(9) //最多可选择的数量
                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .gridExpectedSize(350) //图片显示在列表中的大小
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .thumbnailScale(0.8f)//缩放比例
                                .imageEngine(new GlideEngine()) //使用图片加载引擎
                                .theme(R.style.Matisse_Dracula) //主题
                                .capture(false)//是否提供拍照功能
                                .forResult(100);//设置作为标记的请求码
                    }

                }
            });
            new ShowLocalImageDialog(this,imgUrls,1);
            Log.e("所有图片地址",imgUrls.toString());
            customAdapter.notifyDataSetChanged();
        }
    }
    private void removeItem() {
        if (imgUrls.size() != 9) {
            if (imgUrls.size() != 0) {
                imgUrls.remove(imgUrls.size() - 1);
            }
        }
    }
    //获取控件
    private void getViews() {
        cancel = findViewById(R.id.ss_cancel);
        send = findViewById(R.id.ss_send);
        gridView = findViewById(R.id.img_list);
        content = findViewById(R.id.ss_edit_content);
    }
    /*
    * 单击事件
    * */
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.ss_cancel://点击取消
                finish();
                break;
            case R.id.ss_send://点击发表
                imgUrls.remove(imgUrls.size()-1);
                for(int i=0;i<imgUrls.size();i++){
                    uploadImagesOfPublicCircle(i);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("执行了上传图片的方法","i="+i);
                }
                Intent intent = new Intent();
                Circle circle = new Circle();
                //设置返回数据
                setResult(150,intent);
                finish();
                break;
        }
    }
    public void submitPublishCircle(){
        Circle circle=new Circle();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        circle.setTime(formatter.format(date));
        circle.setCommentSize(10);
        circle.setContent(content.getText().toString());
        circle.setForwardSize(10);
        circle.setLikeSize(10);

        circle.setSendImg(imgUrls);
        RequestBody requestBody=RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),new Gson().toJson(circle));
        Request request=new Request.Builder().post(requestBody).url(IP.CONSTANT+"AddCircleServlet").build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("异步请求的结果",response.body().string());
            }
        });
    }
    private void uploadImagesOfPublicCircle(int i) {
        long time = Calendar.getInstance().getTimeInMillis();
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"),new File(imgUrls.get(0)));
        Log.e("list的内容",imgUrls.get(0)+"");
        Request request = new Request.Builder().post(body).url(IP.CONSTANT+"UploadCircleImageServlet?imgName="+time+".jpg").build();
        imgUrls.remove(0);
        imgUrls.add(time+".jpg");
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e("i的值",i+"");
                Log.e("size的值",imgUrls.size()+"");

                if(i==imgUrls.size()-1){
                    submitPublishCircle();
                }
            }
        });
    }
}