package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.R;

import com.example.homeworkcorrect.ShowSchoolInfoActivity;
import com.example.homeworkcorrect.entity.School;

import java.util.ArrayList;
import java.util.List;

public class CustomSchoolAdapter extends BaseAdapter {
    private Context mContext;
    private List<School> schools = new ArrayList<>();
    private int itemLayoutRes;

    public CustomSchoolAdapter(Context mContext, List<School> schools, int itemLayoutRes) {
        this.mContext = mContext;
        this.schools = schools;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {//获得数据的条数
        if (null != schools){
            return schools.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {//获取每个item显示的数据对象
        if (null != schools){
            return schools.get(position);
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

        //获取item中控件的引用
        ImageView school_photo = convertView.findViewById(R.id.school_photo);
        TextView school_name = convertView.findViewById(R.id.school_name);
        TextView school_address = convertView.findViewById(R.id.school_address);
        TextView school_content = convertView.findViewById(R.id.school_content);
        Button button = convertView.findViewById(R.id.btn_show_detail);

        //设置控件内容
        //加载图片
        String src = schools.get(position).getPhoto();
        //String url = "address" + src;
        String url ="https://s3.ax1x.com/2020/11/25/DafUns.png";
        Glide.with(mContext)
                .load(url)
                .into(school_photo);
        school_name.setText(schools.get(position).getName());
        school_address.setText(schools.get(position).getAddress()+"");
        school_content.setText(schools.get(position).getContent()+"");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowSchoolInfoActivity.class);
                intent.putExtra("school",schools.get(position));
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
