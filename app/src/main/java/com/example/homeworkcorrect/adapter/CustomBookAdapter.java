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
import com.example.homeworkcorrect.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class CustomBookAdapter extends BaseAdapter {
    private Context mContext;
    private List<Book> books = new ArrayList<>();
    private int itemLayoutRes;

    public CustomBookAdapter(Context mContext, List<Book> books, int itemLayoutRes) {
        this.mContext = mContext;
        this.books = books;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {//获得数据的条数
        if (null != books){
            return books.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {//获取每个item显示的数据对象
        if (null != books){
            return books.get(position);
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
        ImageView commodity_img = convertView.findViewById(R.id.commodity_img);
        TextView commodity_name = convertView.findViewById(R.id.commodity_name);
        TextView commodity_price = convertView.findViewById(R.id.commodity_price);
        TextView commodity_stock = convertView.findViewById(R.id.commodity_stock);

        //设置控件内容
        //加载图片
        String src = books.get(position).getImage();
        //String url = "address" + src;
        String url ="https://gw.alicdn.com/bao/uploaded/i2/2617663089/O1CN01RYcFUT1Ygo1vAudYl_!!0-item_pic.jpg";
        Glide.with(mContext)
                .load(url)
                .into(commodity_img);
        commodity_name.setText(books.get(position).getBookName());
        commodity_price.setText(books.get(position).getPrice()+"");
        commodity_stock.setText(books.get(position).getStock()+"");
        return convertView;
    }
}
