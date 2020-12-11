package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.cache.IP;

import java.util.ArrayList;
import java.util.List;

public class CircleImgListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> imgs = new ArrayList<>();
    private int itemLayoutRes;

    public CircleImgListAdapter(Context mContext, List<String> imgs, int itemLayoutRes) {
        this.mContext = mContext;
        this.imgs = imgs;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {//获得数据的条数
        if (null != imgs){
            return imgs.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {//获取每个item显示的数据对象
        if (null !=imgs){
            return imgs.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {//获取每个item的id值
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView每个item的视图对象
        //加载item的布局文件
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);//布局填充器
            convertView = inflater.inflate(itemLayoutRes, null);
        }
        ImageView imageView = convertView.findViewById(R.id.send_img);
        Log.e("图片地址",imgs.get(position));
        Glide.with(mContext).load(IP.CONSTANT+"circleImage/"+imgs.get(position))
                .into(imageView);
        return convertView;
    }
}
