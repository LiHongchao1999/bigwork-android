package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.R;

import java.io.File;
import java.util.List;

public class CustomVideoListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> videos;
    private int itemLayoutRes;
    public CustomVideoListAdapter(Context mContext, List<String> videos, int msg_list_item) {
        this.mContext = mContext;
        this.videos = videos;
        this.itemLayoutRes = msg_list_item;
    }
    @Override
    public int getCount() {
        if(videos!=null){
            return videos.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);//布局填充器
            convertView = inflater.inflate(itemLayoutRes, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (convertView.findViewById(R.id.main_gridView_item_video));
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(convertView).load(Uri.fromFile(new File(videos.get(position)))).into(viewHolder.imageView);
        return convertView;
    }
    private class ViewHolder{
        ImageView imageView;
    }
}
