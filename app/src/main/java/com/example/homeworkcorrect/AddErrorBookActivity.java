package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeworkcorrect.adapter.CustomAdapterResult;
import com.example.homeworkcorrect.adapter.CustomImgListAdapter;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.entity.WrongQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
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

public class AddErrorBookActivity extends AppCompatActivity {
    private ScrollableGridView gridView;//老师的
    private ScrollableGridView gridView1;//自己上传的
    private ScrollableGridView gridView2;//老师解析
    private CustomAdapterResult adapter;
    private CustomImgListAdapter selfResult;
    private CustomAdapterResult adapterResult;
    private List<String> selfSend;//自己上传的
    private static final String IMG_ADD= "add"; //添加图片
    private WrongQuestion question = new WrongQuestion();
    private EditText editText;//学生注释
    private OkHttpClient okHttpClient;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    String str = msg.obj.toString();
                    Toast.makeText(AddErrorBookActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                    if(str.equals("true")){//上传成功
                        finish();
                    }else{
                        Toast.makeText(AddErrorBookActivity.this,"上传失败,请重新上传",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_error_book);
        okHttpClient = new OkHttpClient();
        getViews();
        //获取数据
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        question.setUser_id(UserCache.user.getId());//用户id
        question.setWrong_id(id); //作业id
        String str = intent.getStringExtra("result_img");
        Gson gson = new GsonBuilder()
                .serializeNulls()//允许导出null值
                .setPrettyPrinting() //格式化输出
                .create();
        Type collectionType = new TypeToken<List<String>>(){}.getType();
        List<String> imgs = gson.fromJson(str,collectionType);
        question.setHomework_image(imgs);//批改后的图片
        question.setQuestion_Type(intent.getStringExtra("type")); //作业类型
        question.setResult_text_teacher(intent.getStringExtra("teacher_result"));//老师的批语
        List<String> teacher_img = gson.fromJson(intent.getStringExtra("teacher_image"),collectionType);
        question.setResult_image_teacher(teacher_img);//老师的解析
        //老师返回的
        adapter = new CustomAdapterResult(this,imgs,R.layout.send_img_list_item);
        gridView.setAdapter(adapter);
        gridView.setHorizontalSpacing(15);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new ShowImagesDialog(AddErrorBookActivity.this,imgs,i).show();
            }
        });
        //用户返回的
        selfSend = new ArrayList<>();
        selfSend.add(IMG_ADD);//添加
        selfResult = new CustomImgListAdapter(this,selfSend,R.layout.img_list_item);
        gridView1.setAdapter(selfResult);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selfSend.get(position).equals("add")){//进行选择
                    PictureSelector.create(AddErrorBookActivity.this,
                            PictureSelector.SELECT_REQUEST_CODE)
                            .selectPicture(true);
                }else{
                    List<String> urls = new ArrayList<String>();
                    for(int j=0;j<selfSend.size()-1;j++){
                        urls.add(selfSend.get(j));
                    }
                    new ShowLocalImageDialog(AddErrorBookActivity.this,urls,position).show();
                }
            }
        });
        adapterResult = new CustomAdapterResult(this,teacher_img,R.layout.send_img_list_item);
        gridView2.setAdapter(adapterResult);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new ShowImagesDialog(AddErrorBookActivity.this,teacher_img,position).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PictureSelector.SELECT_REQUEST_CODE){
            if (data!=null){
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                removeItem();
                if(pictureBean.isCut()){
                    selfSend.add(pictureBean.getPath());
                    selfSend.add(IMG_ADD);
                    selfResult.notifyDataSetChanged();
                }else {
                    String path = ImageTool.getRealPathFromUri(this,pictureBean.getUri());
                    Log.e("路径1",path);
                    selfSend.add(path);
                    selfSend.add(IMG_ADD);
                    selfResult.notifyDataSetChanged();
                }
            }else {
                Toast.makeText(this,"您没有选择图片",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void removeItem() {
        if (selfSend.size() != 9) {
            if (selfSend.size() != 0) {
                selfSend.remove(selfSend.size() - 1);
            }
        }
    }
    private void getViews() {
        gridView = findViewById(R.id.teacher_result_img);
        gridView1 = findViewById(R.id.self_result_img);
        gridView2 = findViewById(R.id.explain_image);
        editText = findViewById(R.id.correct_self_text);
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.add_error_return:
                finish();
                break;
            case R.id.btn_submit_www:
                //赋值
                if(editText.getText().toString() == null|| editText.getText().toString().equals("") ){
                    question.setResult_text_student("");
                }else{
                    question.setResult_text_student(editText.getText().toString());
                }
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date curDate1 =  new Date(System.currentTimeMillis());
                question.setUpdate_time(formatter1.format(curDate1)+"");
                Log.e("当前时间",formatter1.format(curDate1));
                //移除掉add
                removeItem();
                //上传图片到服务器
                for(int i=0;i<selfSend.size();i++){
                    uploadImagesOfHomework(i);
                }
                break;
        }
    }
    private void submitErrorInformation() {
        question.setResult_image(selfSend);
        Log.e("上传的错题内容",new Gson().toJson(question));
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),new Gson().toJson(question));
        Request request = new Request.Builder().post(requestBody).url(IP.CONSTANT+"AddWrongQuestionServlet").build();
        //3、创建Call对象，发送请求，并且接受响应数据
        final Call call = okHttpClient.newCall(request);
        //不需要手动创建多线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时回调的方法
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("返回的结果",str);
                //请求成功时回调的方法
                Message msg = new Message();
                msg.what=1;
                msg.obj =str ;
                handler.sendMessage(msg);
            }
        });
    }
    private void uploadImagesOfHomework(int i) {
        Log.e("i",i+" wwww");
        long time = Calendar.getInstance().getTimeInMillis();
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"),new File(selfSend.get(0)));
        Log.e("list的内容",selfSend.get(0)+"");
        Request request = new Request.Builder().post(body).url(IP.CONSTANT+"UploadHomeworkImageServlet?imgName="+time+".jpg").build();
        selfSend.remove(0);
        selfSend.add(time+".jpg");
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e("i的值",i+"");
                Log.e("size的值",selfSend.size()+"");

                if(i == selfSend.size()-1){
                    //上传错题本信息
                    submitErrorInformation();
                }
            }
        });
    }
}