package com.example.homeworkcorrect.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.homeworkcorrect.GlideImageLoader;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.ScrollableGridView;
import com.example.homeworkcorrect.adapter.CustomBookAdapter;
import com.example.homeworkcorrect.adapter.CustomSchoolAdapter;
import com.example.homeworkcorrect.entity.Book;
import com.example.homeworkcorrect.entity.School;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnlineShopFragment extends Fragment {
    private View root;
    private Banner banner;
    private List<Book> books = new ArrayList<>();
    private List<School> schools = new ArrayList<>();
    private CustomBookAdapter customBookAdapter;
    private CustomSchoolAdapter customSchoolAdapter;
    private SmartRefreshLayout refreshLayout;

    //资源文件
    private Integer[] images = {R.drawable.cake01, R.drawable.cake02, R.drawable.cake03, R.drawable.cake04, R.drawable.cake05};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_online_shop, container, false);

        findViews();
        setListeners();
        getBookList();
        getSchoolList();
        banner = (Banner) root.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(Arrays.asList(images));
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        return root;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            // 处理消息
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //准备数据
                    initView();
                    break;
                case 2:
                    //准备数据
                    initViewForSchool();
                    break;
            }
        };

    };

    private void initViewForSchool() {
        customSchoolAdapter = new CustomSchoolAdapter(getContext(), schools,
                R.layout.school_list_item);
        ListView schoolListView = root.findViewById(R.id.lv_school);
        schoolListView.setAdapter(customSchoolAdapter);
    }

    private void initView() {
        customBookAdapter = new CustomBookAdapter(getContext(), books,
                R.layout.book_list_item);
        ScrollableGridView bookListView = root.findViewById(R.id.gv_book);
        bookListView.setAdapter(customBookAdapter);
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //打印
                Log.e("选中的蛋糕名称----------------","" + books.get(position).getBookName());
                int menu_id =  books.get(position).getId();
                //判断用户是否登录
//                if (user_Name.length() <= 0){
//                    Toast.makeText(getActivity(), "请先登录",
//                            Toast.LENGTH_SHORT).show();
//                }
//                else {
//
//                    //跳转到蛋糕详细信息的Activity
//                    Intent intent = new Intent();
//                    //设置目的地Activity类
//                    intent.setClass(getActivity(),
//                            ShowCakeInfoActivity.class);
//                    //传递自定义类型的对象
//                    //携带用户选中的菜谱id
//                    intent.putExtra("cake",cakes.get(position));
//                    startActivity(intent);
//                }
            }
        });

        //setListener();
    }


    private void getBookList() {
        Book book1 = new Book("计算高手五年级上每日10分钟","晏飞1","河北少年儿童出版社",21.0f,10,"");
        books.add(book1);
        Book book2 = new Book("计算高手五年级上每日10分钟","晏飞2","河北少年儿童出版社",22.0f,9,":");
        books.add(book2);
        Book book3 = new Book("计算高手五年级上每日10分钟","晏飞3","河北少年儿童出版社",23.0f,8,":");
        books.add(book3);
        Book book4 = new Book("计算高手五年级上每日10分钟","晏飞4","河北少年儿童出版社",24.0f,7,":");
        books.add(book4);
        Book book5 = new Book("计算高手五年级上每日10分钟","晏飞5","河北少年儿童出版社",25.0f,6,":");
        books.add(book5);
        //资源下载完成，返回消息
        Message msg = handler.obtainMessage();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    private void getSchoolList() {
        School school1 = new School("音悦家艺术教育培训学校1","河北省石家庄市裕华区南二环东路",
                "数学、英语","","18811111111");
        School school2 = new School("音悦家艺术教育培训学校2","河北省石家庄市裕华区南二环东路",
                "语文、数学、英语","","18811111111");
        School school3 = new School("音悦家艺术教育培训学校3","河北省石家庄市裕华区南二环东路",
                "数学","","18811111111");
        schools.add(school1);
        schools.add(school2);
        schools.add(school3);
        //资源下载完成，返回消息
        Message msg = handler.obtainMessage();
        msg.what = 2;
        handler.sendMessage(msg);

    }

    private void setListeners() {
    }

    private void findViews() {
        banner = root.findViewById(R.id.banner);
    }

    private void setListener(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e("下拉刷新","666");
                getBookList();
                //资源下载完成，返回消息
                Message msg = handler.obtainMessage();
                msg.what = 3;
                handler.sendMessage(msg);

            }
        });

//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                Log.e("上拉加载","666");
//                refreshLayout.finishLoadMore();
//
//            }
//        });


    }
}
