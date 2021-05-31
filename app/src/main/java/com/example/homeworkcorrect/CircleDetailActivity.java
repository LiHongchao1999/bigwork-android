package com.example.homeworkcorrect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.adapter.CircleImgListAdapter;
import com.example.homeworkcorrect.adapter.CustomCommentAdapter;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.chat.CircleImageView;
import com.example.homeworkcorrect.entity.Circle;
import com.example.homeworkcorrect.entity.CircleComment;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CircleDetailActivity extends AppCompatActivity {
    private MyListView commentContent;//评论内容
    private CircleImageView headImg;//头像
    private TextView nickName;//昵称
    private TextView sendTime;//发布时间
    private TextView sendContent;//发布内容
    private ScrollableGridView sendImg;//发布图片
    private TextView forwardSize; //转发数
    private TextView commentSize; //评论数
    private TextView likedSize;  //点赞数
    private EditText sendComment;//发布评论
    private Button submit;//提交评论
    private Circle circle;
    private CircleImgListAdapter adapter;
    private CustomCommentAdapter commentAdapter;
    private List<CircleComment> comments;//所有评论
    private OkHttpClient okHttpClient;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String str = msg.obj.toString();
                    if(str.equals("true")){
                        Toast.makeText(CircleDetailActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(CircleDetailActivity.this,"评论失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    commentAdapter=new CustomCommentAdapter(CircleDetailActivity.this,comments,R.layout.comment_item_list_layout);
                    commentContent.setAdapter(commentAdapter);
                    break;
                case 3:
                    String str1 = msg.obj.toString();
                    if(str1.equals("-1")){
                        Toast.makeText(CircleDetailActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(CircleDetailActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_detail);
        okHttpClient = new OkHttpClient();
        //获取控件
        getViews();
        //获取数据
        Intent intent = getIntent();
        String str = intent.getStringExtra("circle");
        circle = new Gson().fromJson(str,Circle.class);


        //赋值
        Glide.with(this).load(IP.CONSTANT+"userImage/"+circle.getUserImg()).into(headImg);
        adapter = new CircleImgListAdapter(this,circle.getSendImg(),R.layout.send_img_list_item);
        sendImg.setAdapter(adapter);
        sendImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new ShowCircleImagesDialog(CircleDetailActivity.this,circle.getSendImg(),position).show();
            }
        });
        //从服务端获取评论信息
        getAllComments();
        nickName.setText(circle.getUserName());
        sendTime.setText(circle.getTime());
        sendContent.setText(circle.getContent());
        forwardSize.setText(circle.getForwardSize()+"");
        commentSize.setText(circle.getCommentSize()+"");
        likedSize.setText(circle.getLikeSize()+"");
        //点击事件
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment.getText().toString();
                uploadComment();
                sendComment.setText("");
                commentSize.setText(circle.getCommentSize()+1+"");
            }
        });
        //listview长按点击事件
        commentContent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("用户名称",comments.get(position).getCommentName());
                if(UserCache.user.getNickname().equals(comments.get(position).getCommentName()) || UserCache.user.getId()==circle.getUserId()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CircleDetailActivity.this);
                    builder.setTitle("确定要删除这条评论吗？")
                            .setMessage("")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteComment(position);
                                    commentSize.setText(circle.getCommentSize()-1+"");
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }else {
                    Toast.makeText(CircleDetailActivity.this,"无法删除其他用户评论",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    /*
     * 上传评论
     * */
    private void uploadComment() {
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"),"");
        Request request = new Request.Builder().post(body).url(IP.CONSTANT+"UploadCircleCommentServlet?id="+circle.getId()+"&comment="+sendComment.getText().toString()+"&userId="+ UserCache.user.getId()).build();
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
        getAllComments();
//        commentSize.setText(circle.getCommentSize()+1);
    }

    /*
     * 获取所有评论
     * */
    private void getAllComments(){
        //请求体是普通的字符串
        //3、创建请求对象
        Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .url(IP.CONSTANT+"GetCircleComment?id="+circle.getId())
                .build();
        //4、创建Call对象，发送请求，并接受响应
        Call call = new OkHttpClient().newCall(request);
        //如果使用异步请求，不需要手动使用子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时候回调
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功以后回调
                String str = response.body().string();//字符串数据
                Log.e("所有评论",str);
                Type collectionType = new TypeToken<List<CircleComment>>(){}.getType();
                comments = new Gson().fromJson(str,collectionType);
                Message msg = new Message();
                msg.what=2;
                handler.sendMessage(msg);
            }
        });
    }

    /*
     * 删除评论
     * */
    private void deleteComment(int position) {
        //请求体是普通的字符串
        //3、创建请求对象
        Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .url(IP.CONSTANT + "DeleteCommentServlet?id=" + comments.get(position).getId())
                .build();
        //4、创建Call对象，发送请求，并接受响应
        Call call = new OkHttpClient().newCall(request);
        //如果使用异步请求，不需要手动使用子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时候回调
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg = new Message();
                msg.what=3;
                msg.obj=response.body().string();
                handler.sendMessage(msg);
            }
        });
//        commentSize.setText(circle.getCommentSize()-1);

        Iterator<CircleComment> iterator = comments.iterator();
        while (iterator.hasNext()) {
            CircleComment next = iterator.next();
            int id = next.getId();
            if (id == comments.get(position).getId()) {
                iterator.remove();
            }
            commentAdapter.notifyDataSetChanged();
        }

        }
    /*
    * 获取控件
    * */
    private void getViews() {
        commentContent = findViewById(R.id.all_comment);
        headImg = findViewById(R.id.detail_img);
        nickName = findViewById(R.id.detail_nickname);
        sendTime = findViewById(R.id.detail_send_time);
        sendContent = findViewById(R.id.detail_send_content);
        sendImg = findViewById(R.id.deatil_send_img);
        forwardSize = findViewById(R.id.fre_forward_size);
        commentSize = findViewById(R.id.fre_comment_size);
        likedSize = findViewById(R.id.fre_like_size);
        sendComment=findViewById(R.id.et_comment);
        submit=findViewById(R.id.btn_submit);

    }


}