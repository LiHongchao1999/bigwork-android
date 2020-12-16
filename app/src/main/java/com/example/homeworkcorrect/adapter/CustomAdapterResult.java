package com.example.homeworkcorrect.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterResult extends BaseAdapter {
    private Context mContext;
    private List<String> imgs = new ArrayList<>();
    private int itemLayoutRes;

    public CustomAdapterResult(Context mContext, List<String> imgs, int itemLayoutRes) {
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
        RequestOptions options = new RequestOptions().bitmapTransform(new RoundedCorners(25));//图片圆角为30
        final ObjectAnimator anim = ObjectAnimator.ofInt(imageView, "ImageLevel", 0,10000);
        anim.setDuration(3000);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        Glide.with(mContext).load(IP.CONSTANT+"images/"+imgs.get(position)).placeholder(R.drawable.rotate_loading).error(R.drawable.fail).apply(options).into(imageView);
        return convertView;
    }
}
