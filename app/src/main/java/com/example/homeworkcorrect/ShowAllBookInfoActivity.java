package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.homeworkcorrect.adapter.CustomBookAdapter;
import com.example.homeworkcorrect.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class ShowAllBookInfoActivity extends AppCompatActivity {
    private CustomBookAdapter customBookAdapter;
    private List<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_book_info);
        getBookList();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 处理消息
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //准备数据
                    initView();
                    break;
                case 2:

                    break;
            }
        }

    };


    private void getBookList() {
        Book book1 = new Book("计算高手五年级上每日10分钟", "晏飞1", "河北少年儿童出版社", 21.0f, 10, "");
        books.add(book1);
        Book book2 = new Book("计算高手五年级上每日10分钟", "晏飞2", "河北少年儿童出版社", 22.0f, 9, ":");
        books.add(book2);
        Book book3 = new Book("计算高手五年级上每日10分钟", "晏飞3", "河北少年儿童出版社", 23.0f, 8, ":");
        books.add(book3);
        Book book4 = new Book("计算高手五年级上每日10分钟", "晏飞4", "河北少年儿童出版社", 24.0f, 7, ":");
        books.add(book4);
        Book book5 = new Book("计算高手五年级上每日10分钟", "晏飞5", "河北少年儿童出版社", 25.0f, 6, ":");
        books.add(book5);
        //资源下载完成，返回消息
        Message msg = handler.obtainMessage();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    private void initView() {
        customBookAdapter = new CustomBookAdapter(this, books,
                R.layout.book_list_item);
        ScrollableGridView bookListView = findViewById(R.id.gv_all_book);
        bookListView.setAdapter(customBookAdapter);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //打印
                Log.e("选中的蛋糕名称----------------", "" + books.get(position).getBookName());
                int menu_id = books.get(position).getId();
                //判断用户是否登录
//                if (user_Name.length() <= 0){
//                    Toast.makeText(getActivity(), "请先登录",
//                            Toast.LENGTH_SHORT).show();
//                }
//                else {
//
                //跳转到蛋糕详细信息的Activity
                Intent intent = new Intent();
                //设置目的地Activity类
                intent.setClass(ShowAllBookInfoActivity.this,
                        ShowBookInfoActivity.class);
                //传递自定义类型的对象
                //携带用户选中的菜谱id
                intent.putExtra("book", books.get(position));
                startActivity(intent);
//                }
            }
        });

        //setListener();
    }
}