package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.homeworkcorrect.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterSelfResult extends BaseAdapter {
    private Context mContext;
    private List<String> imgs = new ArrayList<>();
    private int itemLayoutRes;

    public CustomAdapterSelfResult(Context mContext, List<String> imgs, int itemLayoutRes) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);//布局填充器
            convertView = inflater.inflate(itemLayoutRes, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.send_img);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        if(imgs.get(position).equals("add")){
            RequestOptions options = new RequestOptions().bitmapTransform(new RoundedCorners(25));//图片圆角为30
            Glide.with(convertView).load(R.drawable.add_img).into(viewHolder.imageView);
        }else{
            RequestOptions options = new RequestOptions().bitmapTransform(new RoundedCorners(25));//图片圆角为30
            Glide.with(convertView).load(imgs.get(position)).apply(options).into(viewHolder.imageView);
        }
        return convertView;
    }
    private class ViewHolder{
        ImageView imageView;
    }
}
