package com.example.homeworkcorrect.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.homeworkcorrect.IP;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.ScrollableGridView;
import com.example.homeworkcorrect.adapter.HomeworkAdapter;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homework_fragment, container,false);
        //获取控件
        getViews();
        getAllHomework();
        this.okHttpClient = new OkHttpClient();
        return view;
    }
    /*
    * 获取控件引用
    * */
    private void getViews() {
        lvHomework = view.findViewById(R.id.lv_homework);
        lvHomework.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
    public void getAllHomework(){
        Request request = new Request.Builder().url(IP.CONSTANT+"").build();
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
                ArrayList<Homework> list = new ArrayList<>();
                try{
                    Gson gson = new Gson();
                    list = gson.fromJson(res, new TypeToken<ArrayList<Homework>>() {}.getType());
                    homeworkAdapter = new HomeworkAdapter(getContext(),list, R.layout.homework_list_item);
                    lvHomework.setAdapter(homeworkAdapter);
                }catch (JsonSyntaxException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
