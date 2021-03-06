package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.entity.PopWindowEntity;

import java.util.List;


public class CustomSelectAdapter extends BaseAdapter {
    private Context mContext;
    private List<PopWindowEntity> list;
    private int itemLayoutRes;
    public CustomSelectAdapter(Context mContext,List<PopWindowEntity> list, int select_list_item) {
        this.mContext = mContext;
        this.list = list;
        this.itemLayoutRes = select_list_item;
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
            viewHolder.imageView = convertView.findViewById(R.id.pop_img);
            viewHolder.textView = convertView.findViewById(R.id.pop_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //给控件赋值
        viewHolder.imageView.setBackground(convertView.getResources().getDrawable(list.get(position).getImg()));
        viewHolder.textView.setText(list.get(position).getSection());
        return convertView;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
