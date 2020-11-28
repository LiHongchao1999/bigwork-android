package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.homeworkcorrect.MyListView;
import com.example.homeworkcorrect.R;

import com.example.homeworkcorrect.ScrollableGridView;
import com.example.homeworkcorrect.chat.CircleImageView;
import com.example.homeworkcorrect.entity.Circle;

import java.util.ArrayList;
import java.util.List;

public class CustomCircleAdapter extends BaseAdapter {
    private Context mContext;
    private List<Circle> circles = new ArrayList<>();
    private int itemLayoutRes;
    public CustomCircleAdapter(Context mContext, List<Circle> circles, int msg_list_item) {
        this.mContext = mContext;
        this.circles = circles;
        Log.e("jsad ",circles.size()+"");
        this.itemLayoutRes = msg_list_item;
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
        final ViewHolder viewHolder;
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
        viewHolder.userImg.setImageBitmap(circles.get(position).getUserImg());
        viewHolder.nickName.setText(circles.get(position).getUserName());
        viewHolder.sendTime.setText(circles.get(position).getTime());
        viewHolder.content.setText(circles.get(position).getContent());
        viewHolder.gridView.setAdapter(new CustomSendImgAdapter(mContext,circles.get(position).getSendImg(),R.layout.send_img_list_item));
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
                int count = circles.get(position).getCommentSize() +1;
                viewHolder.commentSize.setText(count+"");
            }
        });
        final View finalConvertView = convertView;
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = circles.get(position).getLikeSize() +1;
                viewHolder.likeSize.setText(count+"");
                viewHolder.likeSize.setTextColor(finalConvertView.getResources().getColor(android.R.color.holo_red_light));
                viewHolder.likeImg.setImageBitmap(BitmapFactory.decodeResource(finalConvertView.getResources(),R.drawable.like1));
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
