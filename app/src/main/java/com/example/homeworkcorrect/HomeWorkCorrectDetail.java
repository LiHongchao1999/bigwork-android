package com.example.homeworkcorrect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeworkcorrect.adapter.CustomAdapterResult;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.entity.Homework;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeWorkCorrectDetail extends AppCompatActivity {
    private ScrollableGridView gridView;//图片列表
    private TextView comment;//评语
    private Homework homework;
    private CustomAdapterResult adapter;
    private PopupWindow popupWindow;//评分
    private int grade=0;
    private OkHttpClient okHttpClient;
    private RelativeLayout root;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String str = msg.obj.toString();
                    if(str.equals("true")){
                        Toast.makeText(HomeWorkCorrectDetail.this,"评分成功",Toast.LENGTH_LONG).show();
                        homework.setScored("true");
                    }else{
                        Toast.makeText(HomeWorkCorrectDetail.this,"评分失败",Toast.LENGTH_LONG).show();
                        homework.setGrade(0);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_correct_detail);
        okHttpClient = new OkHttpClient();
        homework = new Homework();
        //获取控件
        getViews();
        //获取数据
        Intent intent = getIntent();
        String str = intent.getStringExtra("homework");
        Gson gson = new Gson();
        homework = gson.fromJson(str,Homework.class);
        adapter = new CustomAdapterResult(this,homework.getResult_image(),R.layout.send_img_list_item);
        gridView.setAdapter(adapter);
        comment.setText(homework.getResult_text());
    }
    private void getViews() {
        gridView = findViewById(R.id.home_image);
        comment = findViewById(R.id.teacher_comment);
        root = findViewById(R.id.root_detail);
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.detail_return:
                finish();
                break;
            case R.id.btn_add_error://加入做题本
                Intent intent = new Intent(this,AddErrorBookActivity.class);
                intent.putExtra("id",homework.getId());
                Gson gson = new Gson();
                String str = gson.toJson(homework.getResult_image());
                intent.putExtra("type",homework.getHomeworkType());
                intent.putExtra("submitTime",homework.getSubmitTime());
                intent.putExtra("teacher_result",homework.getResult_text());
                intent.putExtra("result_img",str);
                startActivity(intent);
                break;
            case R.id.user_comment://进行评分
                Log.e("进行评分",true+"");
                backgroundAlpha(0.5f);
                showPopupWindow();
                break;
        }
    }
    public void showPopupWindow(){
        //创建PopupWindow对象
        popupWindow = new PopupWindow(this);
        //设置弹出窗口的宽度
        popupWindow.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置它的视图
        View view = getLayoutInflater().inflate(R.layout.raring_star_popupwindow,null);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.comment_background));
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        //设置边缘点击收起
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.comment_pop_animation);
        //获取控件
        RatingBar bar = view.findViewById(R.id.ratingBar);
        Button btn = view.findViewById(R.id.comment_commit);
        if(homework.getScored().equals("true")){//表示用户已经评分
            bar.setRating(homework.getGrade()); //设置评分结果
            btn.setText("已提交");
        }else{
            bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    Log.e("星级评分为",(int)rating+"");
                    grade = (int)rating;
                    Log.e("Grade",grade+"");
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homework.setGrade(grade);
                    uploadGradeOfHomework();
                    popupWindow.dismiss();
                }
            });
        }
        //设置popupwindow关闭监听器
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        //设置视图当中控件的属性和监听器
        popupWindow.setContentView(view);
        //显示PopupWindow（必须指定显示的位置）
        popupWindow.showAtLocation(root, Gravity.CENTER,0,0);
    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
    private void uploadGradeOfHomework() {
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"),"");
        Log.e("上传的评分",homework.getGrade()+"::"+homework.getId());
        Request request = new Request.Builder().post(body).url(IP.CONSTANT+"UpdateHomeWorkInfo?id="+homework.getId()+"&grade="+homework.getGrade()+"&tag="+0).build();
        Log.e("上传的评分",homework.getGrade()+"::"+homework.getId());
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg = new Message();
                msg.what=1;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
    }

}