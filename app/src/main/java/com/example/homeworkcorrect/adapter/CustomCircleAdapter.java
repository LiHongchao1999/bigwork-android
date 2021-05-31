package com.example.homeworkcorrect.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.CircleDetailActivity;
import com.example.homeworkcorrect.HisselfInfoActivity;
import com.example.homeworkcorrect.MyListView;
import com.example.homeworkcorrect.R;

import com.example.homeworkcorrect.ScrollableGridView;
import com.example.homeworkcorrect.ShowCircleImagesDialog;
import com.example.homeworkcorrect.ShowImagesDialog;
import com.example.homeworkcorrect.WrongQuestionDetailActivity;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.chat.CircleImageView;
import com.example.homeworkcorrect.entity.Circle;
import com.example.homeworkcorrect.entity.Like;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.rong.imageloader.utils.L;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomCircleAdapter extends BaseAdapter {
    private List<Like> like=new ArrayList<>();
    private Context mContext;
    private List<Circle> circles = new ArrayList<>();
    private int itemLayoutRes;
    private int count;
    public CustomCircleAdapter(Context mContext, List<Circle> circles, int msg_list_item,List<Like> like) {
        this.mContext = mContext;
        this.circles = circles;
        Log.e("jsad ",circles.size()+"");
        this.itemLayoutRes = msg_list_item;
        this.like=like;
    }
    @Override
    public int getCount() {//获取数据的条数
        if(null!=circles){
            return circles.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {//获取每个item显示的数据对象
        if(null!=circles){
            return circles.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {//获取每个item的id值
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int current = position;
        ViewHolder viewHolder;
        final boolean[] flags = {false,true};
        //convertView每个item的视图对象
        //加载item的布局文件
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);//布局填充器
            convertView = inflater.inflate(itemLayoutRes, null);
            viewHolder = new ViewHolder();
            viewHolder.userImg = convertView.findViewById(R.id.fre_img);
            viewHolder.nickName = convertView.findViewById(R.id.fre_nickname);
            viewHolder.sendTime = convertView.findViewById(R.id.fre_send_time);
            viewHolder.content = convertView.findViewById(R.id.fre_send_content);
            viewHolder.gridView = convertView.findViewById(R.id.send_img_list);
            viewHolder.forward = convertView.findViewById(R.id.fre_forward);
            viewHolder.comment = convertView.findViewById(R.id.fre_comment);
            viewHolder.like = convertView.findViewById(R.id.fre_like);
            viewHolder.forwardSize = convertView.findViewById(R.id.fre_forward_size);
            viewHolder.commentSize=convertView.findViewById(R.id.fre_comment_size);
            viewHolder.likeSize=convertView.findViewById(R.id.fre_like_size);
            viewHolder.likeImg=convertView.findViewById(R.id.fre_like_one);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //给控件赋值
        Glide.with(mContext).load(IP.CONSTANT+"userImage/"+circles.get(position).getUserImg())
                .into(viewHolder.userImg);
        Log.e("头像地址",circles.get(position).getUserImg());
        //给头像绑定单机事件
        viewHolder.userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击头像 跳转到其个人信息页面
                Intent intent =new Intent(mContext, HisselfInfoActivity.class);
                Gson gson = new Gson();
                String str = gson.toJson(circles.get(position));
                intent.putExtra("self",str);
                mContext.startActivity(intent);
            }
        });
        viewHolder.nickName.setText(circles.get(position).getUserName());
        viewHolder.sendTime.setText(circles.get(position).getTime());
        viewHolder.content.setText(circles.get(position).getContent());
        viewHolder.gridView.setAdapter(new CircleImgListAdapter(mContext,circles.get(position).getSendImg(),R.layout.send_img_list_item));
        //给图片绑定单击事件
        viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new ShowCircleImagesDialog(mContext,circles.get(current).getSendImg(),position).show();
            }
        });
        //判断是否点过赞
        if(UserCache.user!=null) {
            for (int j = 0; j < like.size(); j++) {
                if (UserCache.user.getId() == like.get(j).getUserid()) {
                    if (circles.get(position).getId() == like.get(j).getCircleid()) {
                        viewHolder.likeSize.setText(circles.get(position).getLikeSize() + "");
                        viewHolder.likeSize.setTextColor(convertView.getResources().getColor(android.R.color.holo_red_light));
                        viewHolder.likeImg.setImageBitmap(BitmapFactory.decodeResource(convertView.getResources(), R.drawable.like1));
                        flags[0] = true;
                    }
                }
            }
        }
        viewHolder.forwardSize.setText(circles.get(position).getForwardSize()+"");
        viewHolder.commentSize.setText(circles.get(position).getCommentSize()+"");
        viewHolder.likeSize.setText(circles.get(position).getLikeSize()+"");
        //设置监听器
        viewHolder.forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = circles.get(position).getForwardSize() +1;
                viewHolder.forwardSize.setText(count+"");
            }
        });

        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = circles.get(position).getCommentSize();
                viewHolder.commentSize.setText(count+"");
                //点击跳转到详情
                Intent intent =new Intent(mContext, CircleDetailActivity.class);
                Gson gson = new Gson();
                String str = gson.toJson(circles.get(position));
                intent.putExtra("circle",str);
                mContext.startActivity(intent);
            }
        });
        final View finalConvertView = convertView;
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(flags[0] ==true){ //已点赞 点击取消
                    flags[0] =false;
                    if (flags[1]=false){
                        count= circles.get(position).getLikeSize()+1 ;
                        viewHolder.likeSize.setText(count+"");
                        flags[1]=true;

                    }else if(flags[1]=true){
                        count=circles.get(position).getLikeSize();
                        viewHolder.likeSize.setText(count+"");
                        flags[1]=false;
                    }
                    viewHolder.likeImg.setImageBitmap(BitmapFactory.decodeResource(finalConvertView.getResources(),R.drawable.like));
                    viewHolder.likeSize.setTextColor(finalConvertView.getResources().getColor(android.R.color.black));
                    RequestBody requestBody= RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"),String.valueOf(circles.get(position).getId()));
                    Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                            .post(requestBody)
                            .url(IP.CONSTANT+"like/delteLike")
                            .build();
                    //4、创建Call对象，发送请求，并接受响应
                    Call call = new OkHttpClient().newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body().string();

                        }
                    });
                    List<String> list= new ArrayList<String>();
                    list.add(circles.get(position).getId()+"");
                    list.add(count+1+"");
                    String a=String.join(",",list);
                    RequestBody requestBody1= RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"),a);
                    Request request1 = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                            .post(requestBody1)
                            .url(IP.CONSTANT+"like/addLikeCircle")
                            .build();
                    //4、创建Call对象，发送请求，并接受响应
                    Call call1 = new OkHttpClient().newCall(request1);
                    call1.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body().string();

                        }
                    });
                }else {//未点赞 点击点赞
                     flags[0] =true;
                     flags[1]=false;
                int coun = circles.get(position).getLikeSize() +1;
                viewHolder.likeSize.setText(coun+"");
                viewHolder.likeSize.setTextColor(finalConvertView.getResources().getColor(android.R.color.holo_red_light));
                viewHolder.likeImg.setImageBitmap(BitmapFactory.decodeResource(finalConvertView.getResources(),R.drawable.like1));
                List<String> list= new ArrayList<String>();
                list.add(UserCache.user.getId()+"");
                list.add(circles.get(position).getId()+"");
                String s=String.join(",",list);
                Log.e("acac",s);

                    RequestBody requestBody= RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"),s);
                    Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                            .post(requestBody)
                            .url(IP.CONSTANT+"like/addLike")
                            .build();
                    //4、创建Call对象，发送请求，并接受响应
                    Call call = new OkHttpClient().newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body().string();

                        }
                    });
                    List<String> list1= new ArrayList<String>();
                    list1.add(circles.get(position).getId()+"");
                    list1.add(coun+"");
                    String bb=String.join(",",list1);
                    RequestBody requestBody1= RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"),bb);
                    Request request1 = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                            .post(requestBody1)
                            .url(IP.CONSTANT+"like/addLikeCircle")
                            .build();
                    //4、创建Call对象，发送请求，并接受响应
                    Call call1 = new OkHttpClient().newCall(request1);
                    call1.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body().string();

                        }
                    });

                }
            }
        });
        return convertView;
    }
    private class ViewHolder{
        private CircleImageView userImg; //用户头像
        public TextView nickName; //用户昵称
        public TextView content; //发送的内容
        public ScrollableGridView gridView;//存放上传的图片列表
        public TextView sendTime; //发送的时间
        public LinearLayout forward; //转发
        public LinearLayout comment; //评论
        public LinearLayout like; //点赞
        public TextView forwardSize; //转发数
        public TextView commentSize;  //评论数
        public TextView likeSize;  //点赞数
        public ImageView likeImg; //点赞图片
    }



}
