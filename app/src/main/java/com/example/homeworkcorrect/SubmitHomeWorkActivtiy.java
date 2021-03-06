package com.example.homeworkcorrect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeworkcorrect.adapter.CustomImgListAdapter;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.entity.Homework;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SubmitHomeWorkActivtiy extends AppCompatActivity {
    private Spinner spinner;//选择科目
    private TextView tvName;
    private Button btnEndDate;//结束日期
    private TextView tvDate;//开始日期
    private ArrayList<String> photoList = new ArrayList<>();
    private Button again;
    private Button submit;
    private TextView dateText;
    private TextView timeText;
    private ImageView backImg;
    private CustomDialog.Builder builder;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private OkHttpClient okHttpClient;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    String str = msg.obj.toString();
                    Log.e("修改拍拍币返回的结果",str);
                    Toast.makeText(SubmitHomeWorkActivtiy.this,str,Toast.LENGTH_SHORT).show();
                    //修改拍拍币数量
                    UserCache.user.setClapping_money(UserCache.user.getClapping_money()-10);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_home_work_activtiy);
        getViews();
        okHttpClient = new OkHttpClient();
        String subject=spinner.getSelectedItem().toString();
        String currentdate=tvDate.getText().toString();
        Intent intent = getIntent();
        Bundle photoBundle = intent.getBundleExtra("photoList");
        photoList = photoBundle.getStringArrayList("photoList");
        CustomImgListAdapter adapter = new CustomImgListAdapter(this,photoList,R.layout.img_list_item);
        GridView gridView = findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new ShowLocalImageDialog(SubmitHomeWorkActivtiy.this,photoList,i).show();
            }
        });

        gridView.setAdapter(adapter);
    }

    private void getViews() {
        spinner=findViewById(R.id.sp_subject);
        tvDate=findViewById(R.id.tv_date);
        backImg = findViewById(R.id.back2);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("photoList",photoList);
                setResult(300,intent);
                finish();
            }
        });
        btnEndDate=findViewById(R.id.btn_choose_date);
        //again = findViewById(R.id.again);
        dateText = findViewById(R.id.dateText);
        timeText = findViewById(R.id.time);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        dateText.setText(year+"年"+month+"月"+day+"日");
        String hourStr;
        String minuteStr;
        if(hour<10){
            hourStr = "0"+hour;
        }else{
            hourStr = ""+hour;
        }
        if(minute<10){
            minuteStr = "0"+minute;
        }else{
            minuteStr = ""+minute;
        }
        timeText.setText(hourStr+":"+minuteStr);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new CustomDialog.Builder(SubmitHomeWorkActivtiy.this);
                builder.setTitle("选择日期");
                DatePicker datePicker = new DatePicker(SubmitHomeWorkActivtiy.this);
                datePicker.setId(R.id.datePicker);

                builder.setContentView(datePicker);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        View view = builder.getView();
                        DatePicker datePicker = view.findViewById(R.id.datePicker);
                        year = datePicker.getYear();
                        month = datePicker.getMonth()+1;
                        day = datePicker.getDayOfMonth();
                        String date = year+"年"+month+"月"+day+"日";
                        dateText.setText(date);
                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
            }
        });
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new CustomDialog.Builder(SubmitHomeWorkActivtiy.this);
                builder.setTitle("选择时间");
                TimePicker timePicker = new TimePicker(SubmitHomeWorkActivtiy.this);
                timePicker.setId(R.id.timePicker);
                timePicker.setIs24HourView(true);
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                        hour = i;
                        minute = i1;
                    }
                });

                builder.setContentView(timePicker);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String hourStr;
                        String minuteStr;
                        if(hour<10){
                            hourStr = "0"+hour;
                        }else{
                            hourStr = ""+hour;
                        }
                        if(minute<10){
                            minuteStr = "0"+minute;
                        }else{
                            minuteStr = ""+minute;
                        }
                        timeText.setText(hourStr+":"+minuteStr);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
            }
        });
        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进行扣除拍拍币
                AlertDialog.Builder dialog = new AlertDialog.Builder(SubmitHomeWorkActivtiy.this);
                dialog.setIcon(R.drawable.work12);
                dialog.setTitle("温馨提示");
                dialog.setMessage("本次作业需要扣除10拍拍币");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //发送给服务端进行修改
                        try {
                            updateMoney();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //然后进行提交
                        InfoDialog infoDialog = new InfoDialog.Builder(SubmitHomeWorkActivtiy.this,R.layout.info_dialog_green)
                                .setTitle("Done")
                                .setMessage("提交成功")
                                .setButton("OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(SubmitHomeWorkActivtiy.this,MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                ).create();
                        infoDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_white);
                        infoDialog.show();
                        for(int i=0;i<photoList.size();i++){
                            uploadImagesOfHomework(i);
                            Log.e("执行了上传图片的方法","i="+i);
                            try {
                                Thread.sleep(1);//主线程休眠1ms，防止图片重名
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
//        again.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        tvDate.setText(formatter.format(date));
    }
    //修改逻辑
    public void updateMoney() throws JSONException {
        //2.创建Request请求对象
        //请求体是字符串
        User user = new User();
        user.setId(UserCache.user.getId());
        user.setClapping_money(UserCache.user.getClapping_money()-10);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),new Gson().toJson(user));
        //3.创建Call对象
        Request request = new Request.Builder()
                .post(requestBody)//请求方式为POST
                .url(IP.CONSTANT+"user/money")
                .build();
        final Call call = okHttpClient.newCall(request);
        //4.提交请求并返回响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时回调
                Log.e("修改请求结果","失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //从服务器端获取到JSON格式字符串
                String str = response.body().string();
                Log.e("Money",str);
                Message msg = new Message();
                msg.what=1;
                msg.obj=str;
                handler.sendMessage(msg);
            }
        });
    }
    private void submitHomeworkInformation() {
        Homework homework = new Homework();
        homework.setSubmitTime(tvDate.getText().toString());
        homework.setHomework_image(photoList);
        homework.setHomeworkType(spinner.getSelectedItem().toString());
        homework.setChatId(UserCache.user.getChat_id());
        homework.setDeadline(dateText.getText().toString()+" "+timeText.getText().toString());
        homework.setUser_id(UserCache.user.getId());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),new Gson().toJson(homework));
        Request request = new Request.Builder().post(requestBody).url(IP.CONSTANT+"homework/addHomework").build();
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
                //请求成功时回调的方法
                Log.e("异步请求的结果",response.body().string());
            }
        });
    }

    private void uploadImagesOfHomework(int i) {
        long time = Calendar.getInstance().getTimeInMillis();
        Log.e("获取到的时间",time+"");
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"),new File(photoList.get(0)));
        Log.e("list的内容",photoList.get(0)+"");
        Request request = new Request.Builder().post(body).url(IP.CONSTANT+"homework/uploadHomeworkImage/"+time+".jpg").build();
        photoList.remove(0);
        photoList.add(time+".jpg");
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(i==photoList.size()-1){
                    submitHomeworkInformation();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra("photoList",photoList);
            setResult(300,intent);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}