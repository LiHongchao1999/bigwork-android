package com.example.homeworkcorrect.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.homeworkcorrect.HomeWorkCorrectDetail;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.ScrollableGridView;
import com.example.homeworkcorrect.adapter.HomeworkAdapter;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.entity.Homework;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeworkFragment extends Fragment {
    private View view;
    private OkHttpClient okHttpClient;
    private ScrollableGridView lvHomework; //作业列表
    private HomeworkAdapter homeworkAdapter;
    private ArrayList<Homework> list;//作业
    private ScrollView scrollView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homework_fragment, container,false);
        //获取控件
        getViews();
        okHttpClient = new OkHttpClient();
        lvHomework.setHorizontalSpacing(20);
        lvHomework.setVerticalSpacing(20);
        lvHomework.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击跳转到作业详情页
                if(list.get(position).getTag().equals("待批改")){
                    Toast.makeText(getContext(),"当前作业正在等待被修改",Toast.LENGTH_SHORT).show();
                }else if(list.get(position).getTag().equals("批改中")){
                    Toast.makeText(getContext(),"当前作业正在被修改",Toast.LENGTH_SHORT).show();
                }else if(list.get(position).getTag().equals("批改完成")){
                    Log.e("点击",position+"");
                    Log.e("1111",list.get(position).getId()+"");
                    Intent intent = new Intent(getContext(), HomeWorkCorrectDetail.class);
                    Homework homework = list.get(position);
                    Gson gson = new Gson();
                    String work = gson.toJson(homework);
                    Log.e("作业详情",work);
                    intent.putExtra("homework",work);
                    startActivity(intent);
                }
            }
        });
        if (UserCache.user != null){//表示用户登录
            getAllHomeworkById();
        }else{
            Toast.makeText(getContext(),"您还未登录",Toast.LENGTH_LONG).show();
        }
//        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//                if (i3 < i1 && ((i1 - i3) > 5)) {// 向上
//                    getActivity().findViewById(R.id.tabs).setVisibility(View.INVISIBLE);
//
//                } else if (i3 > i1 && (i3 - i1) > 5) {// 向下
//                    getActivity().findViewById(R.id.tabs).setVisibility(View.VISIBLE);
//
//                }
//            }
//        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (UserCache.user != null){
            Log.e("获取",""+UserCache.user.getId());
            getAllHomeworkById();
        }else{
            Toast.makeText(getContext(),"您还未登录",Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * 获取控件引用
    * */
    private void getViews() {
        lvHomework = view.findViewById(R.id.lv_homework);
        scrollView = view.findViewById(R.id.scrollView);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserCache.user != null){
            getAllHomeworkById();
        }else{
            Toast.makeText(getContext(),"您还未登录",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){

        }else{
            if (UserCache.user != null){
                getAllHomeworkById();
            }else{
                Toast.makeText(getContext(),"您还未登录",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getAllHomeworkById(){
        Request request = new Request.Builder().url(IP.CONSTANT+"homework/getHomeworks/"+UserCache.user.getId()).build();
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
                String res = response.body().string();
                list = new ArrayList<>();
                try{
                    Gson gson = new Gson();
                    list = gson.fromJson(res, new TypeToken<ArrayList<Homework>>() {}.getType());
                    homeworkAdapter = new HomeworkAdapter(getContext(),list, R.layout.homework_list_item);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            lvHomework.setAdapter(homeworkAdapter);
                        }
                    });
                }catch (JsonSyntaxException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
