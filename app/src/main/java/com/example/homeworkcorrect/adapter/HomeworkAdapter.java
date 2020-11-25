package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.entity.Homework;

import java.util.ArrayList;
import java.util.List;

public class HomeworkAdapter extends BaseAdapter {

    private Context context;
    private List<Homework> dataSource = new ArrayList<>();
    private int id;

    public HomeworkAdapter(Context context, List<Homework> dataSource, int id) {
        this.context = context;
        this.dataSource = dataSource;
        this.id = id;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(id, null);
            viewHolder = new ViewHolder();
            viewHolder.imgHomework = convertView.findViewById(R.id.img_homework);
            viewHolder.tvHomeworkType = convertView.findViewById(R.id.tv_homework_type);
            viewHolder.tvSubmitTime = convertView.findViewById(R.id.tv_submit_time);
            viewHolder.tvState = convertView.findViewById(R.id.tv_state);
            convertView.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) convertView.getTag();
        }
        viewHolder.imgHomework.setImageBitmap(dataSource.get(position).getHomeworkImg());
        viewHolder.tvHomeworkType.setText(dataSource.get(position).getHomeworkType());
        viewHolder.tvSubmitTime.setText(dataSource.get(position).getSubmitTime());
        viewHolder.tvState.setText(dataSource.get(position).getState());
        return convertView;
    }
    private class ViewHolder{
        private ImageView imgHomework;
        private TextView tvHomeworkType;
        private TextView tvSubmitTime;
        private TextView tvState;
    }
}
