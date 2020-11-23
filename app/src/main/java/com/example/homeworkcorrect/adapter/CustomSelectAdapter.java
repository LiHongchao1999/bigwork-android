package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.homeworkcorrecting.R;

public class CustomSelectAdapter extends BaseAdapter {
    private Context mContext;
    private String[] arr;
    private int itemLayoutRes;
    public CustomSelectAdapter(Context mContext,String[] arr, int select_list_item) {
        this.mContext = mContext;
        this.arr = arr;
        this.itemLayoutRes = select_list_item;
    }
    @Override
    public int getCount() {//获取数据的条数
        if(null!=arr){
            return arr.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {//获取每个item显示的数据对象
        if(null!=arr){
            return arr[position];
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
        //获取控件引用
        TextView textView = convertView.findViewById(R.id.txt_circle);
        textView.setText(arr[position]);
        return convertView;
    }
}
