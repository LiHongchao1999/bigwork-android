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

import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.chat.CircleImageView;
import com.example.homeworkcorrect.entity.Circle;

import java.util.ArrayList;
import java.util.List;

public class CustomCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<Circle> circles = new ArrayList<>();
    private int itemLayoutRes;
    public CustomCommentAdapter(Context mContext, List<Circle> circles, int msg_list_item) {
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
        //convertView每个item的视图对象
        //加载item的布局文件
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);//布局填充器
            convertView = inflater.inflate(itemLayoutRes, null);
        }
        return convertView;
    }
    private class ViewHolder{
    }
}
