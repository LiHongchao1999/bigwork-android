package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
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


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.CircleDetailActivity;
import com.example.homeworkcorrect.HisselfInfoActivity;
import com.example.homeworkcorrect.R;

import com.example.homeworkcorrect.ScrollableGridView;
import com.example.homeworkcorrect.ShowCircleImagesDialog;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.chat.CircleImageView;
import com.example.homeworkcorrect.entity.Circle;
import com.example.homeworkcorrect.entity.LikeInfo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomCircleAdapter extends BaseAdapter {
    private List<LikeInfo> like=new ArrayList<>();
    private Context mContext;
    private List<Circle> circles = new ArrayList<>();
    private int itemLayoutRes;
    private int count;
   /* private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1://新增点赞信息
                    String str = msg.obj.toString();
                    if(str!=null){
                        LikeInfo a = new Gson().fromJson(str, LikeInfo.class);
                        like.add(a);
                        notifyDataSetChanged();
                        Log.e("adaptercoun",like.size()+"");
                        Log.e("添加点赞信息","成功");
                    }else{
                        Log.e("添加点赞信息","失败");
                    }
                    break;
                case 2:
                    String str1 = msg.obj.toString();
                    if(!str1.equals("0")) {//
                        LikeInfo like2 = new Gson().fromJson(str1, LikeInfo.class);
                        like.remove(like2);
                        notifyDataSetChanged();
                        Log.e("删除点赞信息","成功");
                    }else{
                        Log.e("删除点赞信息","失败");
                    }
                    break;
                case 3://-1
                    String str2 = msg.obj.toString();
                    int position = Integer.parseInt(str2);//position
                    if(!str2.equals("0")){//表示修改成功
                        Log.e("点赞-1","成功");
                        int count = circles.get(position).getLikeSize()-1;
                        Log.e("-后的coun",count+"");
                        circles.get(position).setLikeSize(count);
                        notifyDataSetChanged();
                    }else{
                        Log.e("点赞-1","失败");
                    }
                    break;
                case 4://+1
                    String str3 = msg.obj.toString();
                    int position1 = Integer.parseInt(str3);//position
                    if(!str3.equals("0")){//表示修改成功
                        Log.e("点赞+1","成功");
                        int count = circles.get(position1).getLikeSize()+1;
                        Log.e("+后的coun",count+"");
                        circles.get(position1).setLikeSize(count);
                        notifyDataSetChanged();
                    }else{
                        Log.e("点赞+1","失败");
                    }
                    break;
            }
        }
    };*/
    public CustomCircleAdapter(Context mContext, List<Circle> circles, int msg_list_item,List<LikeInfo> like) {
        this.mContext = mContext;
        this.circles = circles;
        if(like!=null){
            Log.e("点赞Adapter 1",like.toString());
        }else{
            Log.e("点赞Adapter ","1111");
        }

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
        //convertView每个item的视图对象
        //加载item的布局文件
        final boolean[] flags = new boolean[2];
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
            viewHolder.flags = new boolean[2];
            viewHolder.flags[0] = false;
            viewHolder.flags[1] = true;
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final View finalConvertView = convertView;
        viewHolder.likeImg.setImageBitmap(BitmapFactory.decodeResource(finalConvertView.getResources(),R.drawable.like));
        viewHolder.likeSize.setTextColor(finalConvertView.getResources().getColor(android.R.color.black));
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
        if(like!=null && like.size()!=0) {
            Log.e("点赞like",position+"："+like.toString());
            for (int j = 0; j < like.size(); j++) {
                if (UserCache.user.getId() == like.get(j).getUserid()) {
                    if (circles.get(current).getId() == like.get(j).getCircleid()) {
                        Log.e("点赞个人信息",like.get(j).toString());
                        viewHolder.likeSize.setText(circles.get(position).getLikeSize()+"");
                        viewHolder.likeSize.setTextColor(convertView.getResources().getColor(android.R.color.holo_red_light));
                        viewHolder.likeImg.setImageBitmap(BitmapFactory.decodeResource(convertView.getResources(), R.drawable.like1));
                        flags[0] = true;
                        break;
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
        //点击评论
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
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int coun = Integer.parseInt(viewHolder.likeSize.getText().toString());
                Log.e("当前coun",coun+":");
                if(viewHolder.flags[0] ==true){ //已点赞 点击取消
                    viewHolder.flags[0] =false;
                    if (viewHolder.flags[1]=false){
                        Log.e("当前coun 点赞取消",coun+":");
                        viewHolder.likeSize.setText(coun+"");
                        viewHolder.flags[1]=true;
                    }else if(viewHolder.flags[1]=true){//点赞未取消，直接会点赞数-1
                        //已点赞直接取消
                        Log.e("当前coun 点赞-1",coun+":");
                        coun = coun-1;
                        viewHolder.likeSize.setText(coun+"");
                        viewHolder.flags[1]=false;
                        Log.e("当前coun 点赞-1 1",coun+":");
                    }
                    viewHolder.likeImg.setImageBitmap(BitmapFactory.decodeResource(finalConvertView.getResources(),R.drawable.like));
                    viewHolder.likeSize.setTextColor(finalConvertView.getResources().getColor(android.R.color.black));
                    //将数据返回给服务端进行修改
                    deleteLikeInfo(position);
                    //点赞数-1
                    reduceLikeSize(position,coun);
                }else {//未点赞 点击点赞
                    if(viewHolder.flags[1]==true){
                        //未点赞直接点赞
                        Log.e("当前coun 直接点赞",coun+":");
                        coun=coun+1;
                        Log.e("当前coun 为点赞",coun+"");
                        viewHolder.likeSize.setText(coun+"");
                        Log.e("当前coun 为点赞1",coun+"");
                        viewHolder.flags[1]=false;
                    }else {
                        //已点赞取消后再点赞
                        Log.e("coun 取消后在点赞",coun+"");
                        coun = coun+1;
                        viewHolder.likeSize.setText(coun+"");
                        viewHolder.flags[1]=true;

                    }
                    viewHolder.flags[0] =true;
                     viewHolder.likeSize.setTextColor(finalConvertView.getResources().getColor(android.R.color.holo_red_light));
                     viewHolder.likeImg.setImageBitmap(BitmapFactory.decodeResource(finalConvertView.getResources(),R.drawable.like1));
                     //添加点赞信息
                     addLikeInfo(position);
                     //点赞数+1
                     addLikeSize(position,coun);
                }
            }
        });
        return convertView;
    }
    //点赞数+1
    private void addLikeSize(int position,int size) {
        Circle circle = new Circle();
        circle.setLikeSize(size);
        Log.e("addSize",size+"");
        circle.setId(circles.get(position).getId());
        RequestBody requestBody= RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"),new Gson().toJson(circle));
        Request request1 = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .post(requestBody)
                .url(IP.CONSTANT+"like/add/"+position)
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
                /*Message message=new Message();
                message.what=4;
                message.obj=res;
                handler.sendMessage(message);*/
            }
        });
    }

    //添加点赞信息
    private void addLikeInfo(int position) {
        LikeInfo like = new LikeInfo();
        like.setUserid(UserCache.user.getId());
        like.setCircleid(circles.get(position).getId());
        Log.e("add",position+"");
        RequestBody requestBody= RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"),new Gson().toJson(like));
        Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .post(requestBody)
                .url(IP.CONSTANT+"like/addLikeInfo")
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
                Log.e("新增的点赞信息",res);
               /* Message message = new Message();
                message.what=1;
                message.obj =res;
                handler.sendMessage(message);*/
            }
        });
    }

    //点赞数-1
    private void reduceLikeSize(int position,int size) {
        Circle circle = new Circle();
        circle.setId(circles.get(position).getId());
        circle.setLikeSize(size);
        RequestBody requestBody1= RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"),new Gson().toJson(circle));
        Log.e("reduce",size+"");
        Request request1 = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .post(requestBody1)
                .url(IP.CONSTANT+"like/reduce/"+position)
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
                /*Message message=new Message();
                message.what=3;
                message.obj=res;
                handler.sendMessage(message);*/
            }
        });
    }

    //删除该用户点赞信息
    private void deleteLikeInfo(int position) {
        LikeInfo like = new LikeInfo();
        like.setCircleid(circles.get(position).getId());
        like.setUserid(UserCache.user.getId());
        RequestBody requestBody= RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"),new Gson().toJson(like));
        Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .post(requestBody)
                .url(IP.CONSTANT+"like/deleteLike")
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
                /*Message message=new Message();
                message.what=2;
                message.obj=res;
                handler.sendMessage(message);*/
            }
        });
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
        public boolean[] flags;
    }



}
