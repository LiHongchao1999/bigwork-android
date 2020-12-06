package com.example.homeworkcorrect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.entity.WrongQuestion;

import java.util.List;


public class ErrorTopicAdapter extends BaseAdapter {
    private Context mContext;
    private List<WrongQuestion> questions;
    private int itemLayoutRes;
    public ErrorTopicAdapter(Context mContext, List<WrongQuestion> questions, int msg_list_item) {
        this.mContext = mContext;
        this.questions = questions;
        this.itemLayoutRes = msg_list_item;
    }

    @Override
    public int getCount() {
        if (questions!=null){
            return questions.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(itemLayoutRes,null);
            viewHolder = new ViewHolder();
            viewHolder.time = convertView.findViewById(R.id.question_time);
            viewHolder.img = convertView.findViewById(R.id.question_img_first);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.time.setText(questions.get(position).getUpdate_time());
        Glide.with(convertView).load(IP.CONSTANT+"images/"+questions.get(position).getHomework_image().get(0)).into(viewHolder.img);
        return convertView;
    }
    private class ViewHolder{
        TextView time;
        ImageView img;
    }
}
