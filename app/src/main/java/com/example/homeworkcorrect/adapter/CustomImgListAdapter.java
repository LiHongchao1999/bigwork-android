package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.R;

import java.util.List;

public class CustomImgListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> imgs;
    private int itemLayoutRes;
    public CustomImgListAdapter(Context mContext, List<String> imgs, int msg_list_item) {
        this.mContext = mContext;
        this.imgs = imgs;
        this.itemLayoutRes = msg_list_item;
    }
    @Override
    public int getCount() {
        if(imgs!=null){
            return imgs.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return imgs.get(position);
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
            viewHolder.imageView = convertView.findViewById(R.id.main_gridView_item_photo);
            viewHolder.checkBox = convertView.findViewById(R.id.main_gridView_item_cb);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.e("add路径",imgs.get(imgs.size()-1));
        if(imgs.get(position).equals("add")){//表示是添加图片的图片
            Log.e("img",imgs.get(position));
            viewHolder.checkBox.setVisibility(View.GONE);
            Glide.with(convertView).load(R.drawable.add_img).into(viewHolder.imageView);
        }else{
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeFile(imgs.get(position));
            //Bitmap bitmap = ImageTool.createImageThumbnail(imgs.get(position));
            Glide.with(convertView).load(bitmap).into(viewHolder.imageView);
        }
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgs.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    private class ViewHolder{
        ImageView imageView;
        CheckBox checkBox;
    }
}
