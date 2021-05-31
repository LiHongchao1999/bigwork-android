package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.chat.CircleImageView;
import com.example.homeworkcorrect.entity.CircleComment;

import java.util.ArrayList;
import java.util.List;

public class CustomCommentAdapter extends BaseAdapter {
    private Context mContext;
//    private List<Circle> circles = new ArrayList<>();
    private List<CircleComment> list=new ArrayList<>();
    private int itemLayoutRes;
    public CustomCommentAdapter(Context mContext, List<CircleComment> list, int msg_list_item) {
        this.mContext = mContext;
        this.list = list;
        Log.e("jsad ",list.size()+"");
        this.itemLayoutRes = msg_list_item;
    }
    @Override
    public int getCount() {//获取数据的条数
        if(null!=list){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {//获取每个item显示的数据对象
        if(null!=list){
            return list.get(position);
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
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);//布局填充器
            convertView = inflater.inflate(itemLayoutRes, null);
            viewHolder=new ViewHolder();
            viewHolder.commentName=convertView.findViewById(R.id.tv_comment_name);
            viewHolder.content=convertView.findViewById(R.id.tv_comment_content);
            viewHolder.userImg=convertView.findViewById(R.id.fre_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //给控件赋值
        viewHolder.commentName.setText(list.get(position).getCommentName());
        viewHolder.content.setText(list.get(position).getContent());
        //给控件赋值
        Glide.with(mContext).load(IP.CONSTANT+"userImage/"+list.get(position).getImage())
                .into(viewHolder.userImg);
        return convertView;
    }
    private class ViewHolder{
        private TextView commentName;//评论用户昵称
        private TextView content;//评论内容
        private CircleImageView userImg; //用户头像
    }
}
