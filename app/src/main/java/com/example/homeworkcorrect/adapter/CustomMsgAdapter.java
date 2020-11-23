package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.entity.Information;

import java.util.ArrayList;
import java.util.List;

public class CustomMsgAdapter extends BaseAdapter {
    private Context mContext;
    private List<Information> list = new ArrayList<>();
    private int itemLayoutRes;
    public CustomMsgAdapter(Context mContext, List<Information> list, int msg_list_item) {
        this.mContext = mContext;
        this.list = list;
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
        ViewHolder viewHolder;
        //convertView每个item的视图对象
        //加载item的布局文件
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);//布局填充器
            convertView = inflater.inflate(itemLayoutRes, null);
            viewHolder = new ViewHolder();
            viewHolder.img = convertView.findViewById(R.id.user_img);
            viewHolder.nickName = convertView.findViewById(R.id.user_nickname);
            viewHolder.sendTime = convertView.findViewById(R.id.send_time);
            viewHolder.content = convertView.findViewById(R.id.send_content);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //给控件赋值
        viewHolder.img.setImageBitmap(list.get(position).getUserImg());
        viewHolder.nickName.setText(list.get(position).getNickName());
        viewHolder.sendTime.setText(list.get(position).getSendTime());
        viewHolder.content.setText(list.get(position).getContent());
        return convertView;
    }
    private class ViewHolder{
        public ImageView img;
        public TextView nickName;
        public TextView content;
        public TextView sendTime;
    }
}
