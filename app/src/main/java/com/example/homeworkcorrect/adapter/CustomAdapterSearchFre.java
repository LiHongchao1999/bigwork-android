package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.entity.SearchFriend;

import java.util.List;

public class CustomAdapterSearchFre extends BaseAdapter {
    private Context mContext;
    private List<SearchFriend> fres;
    private int itemLayoutRes;
    public CustomAdapterSearchFre(Context mContext, List<SearchFriend> fres, int msg_list_item) {
        this.mContext = mContext;
        this.fres = fres;
        this.itemLayoutRes = msg_list_item;
    }
    @Override
    public int getCount() {
        if(fres!=null){
            return fres.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return fres.get(position);
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
            viewHolder.imageView = convertView.findViewById(R.id.search_img);
            viewHolder.nickName = convertView.findViewById(R.id.search_nick);
            viewHolder.sex = convertView.findViewById(R.id.search_sex);
            viewHolder.addFre = convertView.findViewById(R.id.add_fre);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //进行赋值
        viewHolder.addFre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.addFre.getText().toString().equals("添加好友")){//点击添加
                    //进行数据库添加
                    viewHolder.addFre.setText("已添加");
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView nickName;
        TextView sex;
        Button addFre;
    }
}
