package com.example.homeworkcorrect.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.homeworkcorrect.HomeWorkCorrectDetail;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.ScrollableGridView;
import com.example.homeworkcorrect.adapter.HomeworkAdapter;
import com.example.homeworkcorrect.entity.Homework;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homework_fragment, container,false);
        //获取控件
        getViews();
        this.okHttpClient = new OkHttpClient();
        getAllHomework();
        lvHomework.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击跳转到作业详情页
                if(list.get(position).getTag().equals("等批改")){
                    Toast.makeText(getContext(),"当前作业正在等待被修改",Toast.LENGTH_LONG).show();
                }else if(list.get(position).getTag().equals("批改中")){
                    Toast.makeText(getContext(),"当前作业正在被修改",Toast.LENGTH_LONG).show();
                }else if(list.get(position).getTag().equals("批改完成")){
                    Log.e("点击",position+"");
                    Log.e("1111",list.get(position).getId()+"");
                    Intent intent = new Intent(getContext(), HomeWorkCorrectDetail.class);
                    Homework homework = list.get(position);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id",homework.getId());
                        jsonObject.put("text",homework.getResult_text());
                        jsonObject.put("submitTime",homework.getSubmitTime());
                        jsonObject.put("deadLine",homework.getDeadline());
                        jsonObject.put("type",homework.getHomeworkType());
                        jsonObject.put("tag",homework.getTag());
                        jsonObject.put("teacher_id",homework.getTeacher_id());
                        Gson gson = new Gson();
                        String result = gson.toJson(homework.getResult_image());
                        jsonObject.put("resultImg",result);
                        jsonObject.put("resultText",homework.getResult_text());
                        jsonObject.put("money",homework.getMoney());
                        jsonObject.put("isGrade",homework.isGrade()+"");
                        jsonObject.put("grade",homework.getGrade());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("homework",jsonObject.toString());
                    startActivity(intent);
                }
            }
        });
        return view;
    }
    /*
    * 获取控件引用
    * */
    private void getViews() {
        lvHomework = view.findViewById(R.id.lv_homework);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllHomework();
    }

    public void getAllHomework(){
        Request request = new Request.Builder().url(IP.CONSTANT+"GetHomeworkListServlet").build();
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
