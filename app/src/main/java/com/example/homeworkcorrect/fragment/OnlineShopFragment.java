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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.homeworkcorrect.AnimationNestedScrollView;
import com.example.homeworkcorrect.CommonUtil;
import com.example.homeworkcorrect.GlideImageLoader;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.ScrollableGridView;
import com.example.homeworkcorrect.ShowAllBookInfoActivity;
import com.example.homeworkcorrect.ShowAllSchoolInfoActivity;
import com.example.homeworkcorrect.ShowBookInfoActivity;
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
    private TextView tv_book_store;
    private TextView tv_class;
    private AnimationNestedScrollView sv_view;
    private LinearLayout ll_search;
    private TextView tv_title;
    private float LL_SEARCH_MIN_TOP_MARGIN, LL_SEARCH_MAX_TOP_MARGIN, LL_SEARCH_MAX_WIDTH, LL_SEARCH_MIN_WIDTH, TV_TITLE_MAX_TOP_MARGIN;
    private ViewGroup.MarginLayoutParams searchLayoutParams, titleLayoutParams;
    //资源文件
    private Integer[] images = {R.drawable.inclass1, R.drawable.cake02, R.drawable.cake03, R.drawable.cake04, R.drawable.cake05};
    
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
                    //准备数据
                    initViewForSchool();
                    break;
            }
        }

        ;

    };

    private void initViewForSchool() {
        customSchoolAdapter = new CustomSchoolAdapter(getContext(), schools,
                R.layout.school_list_item);
        ScrollableGridView schoolListView = root.findViewById(R.id.gv_school);
        schoolListView.setAdapter(customSchoolAdapter);
    }

    private void initView() {
        customBookAdapter = new CustomBookAdapter(getContext(), books,
                R.layout.book_list_item);
        ScrollableGridView bookListView = root.findViewById(R.id.gv_book);
        bookListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        bookListView.setAdapter(customBookAdapter);
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //打印
                Log.e("选中的书本名称----------------", "" + books.get(position).getBookName());
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
                intent.setClass(getActivity(),
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

    private void getSchoolList() {
        School school1 = new School("音悦家艺术教育培训学校1", "河北省石家庄市裕华区南二环东路",
                "数学、英语", "", "18811111111");
        School school2 = new School("音悦家艺术教育培训学校2", "河北省石家庄市裕华区南二环东路",
                "语文、数学、英语", "", "18811111111");
        School school3 = new School("音悦家艺术教育培训学校3", "河北省石家庄市裕华区南二环东路",
                "数学", "", "18811111111");
        schools.add(school1);
        schools.add(school2);
        schools.add(school3);
        Log.e("school", schools.toString());
        //资源下载完成，返回消息
        Message msg = handler.obtainMessage();
        msg.what = 2;
        handler.sendMessage(msg);

    }

    private void setListeners() {
        tv_book_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowAllBookInfoActivity.class);
                startActivity(intent);
            }
        });

        tv_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowAllSchoolInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        banner = root.findViewById(R.id.banner);
        tv_book_store = root.findViewById(R.id.tv_book_store);
        tv_class = root.findViewById(R.id.tv_class);
        sv_view = root.findViewById(R.id.search_sv_view);
        ll_search = root.findViewById(R.id.search_ll_search);
        tv_title = root.findViewById(R.id.search_tv_title);
        searchLayoutParams = (ViewGroup.MarginLayoutParams) ll_search.getLayoutParams();
        titleLayoutParams = (ViewGroup.MarginLayoutParams) tv_title.getLayoutParams();

        LL_SEARCH_MIN_TOP_MARGIN = CommonUtil.dp2px(getContext(), 10f);//布局关闭时顶部距离
        LL_SEARCH_MAX_TOP_MARGIN = CommonUtil.dp2px(getContext(), 49f);//布局默认展开时顶部距离
        LL_SEARCH_MAX_WIDTH = CommonUtil.getScreenWidth(getContext()) - CommonUtil.dp2px(getContext(), 30f);//布局默认展开时的宽度
        LL_SEARCH_MIN_WIDTH = CommonUtil.getScreenWidth(getContext()) - CommonUtil.dp2px(getContext(), 82f);//布局关闭时的宽度
        TV_TITLE_MAX_TOP_MARGIN = CommonUtil.dp2px(getContext(), 11.5f);

        sv_view.setOnAnimationScrollListener(new AnimationNestedScrollView.OnAnimationScrollChangeListener() {
            @Override
            public void onScrollChanged(float dy) {
                float searchLayoutNewTopMargin = LL_SEARCH_MAX_TOP_MARGIN - dy;
                float searchLayoutNewWidth = LL_SEARCH_MAX_WIDTH - dy * 1.3f;//此处 * 1.3f 可以设置搜索框宽度缩放的速率

                float titleNewTopMargin = (float) (TV_TITLE_MAX_TOP_MARGIN - dy * 0.5);

                //处理布局的边界问题
                searchLayoutNewWidth = searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH ? LL_SEARCH_MIN_WIDTH : searchLayoutNewWidth;

                if (searchLayoutNewTopMargin < LL_SEARCH_MIN_TOP_MARGIN) {
                    searchLayoutNewTopMargin = LL_SEARCH_MIN_TOP_MARGIN;
                }

                if (searchLayoutNewWidth < LL_SEARCH_MIN_WIDTH) {
                    searchLayoutNewWidth = LL_SEARCH_MIN_WIDTH;
                }

                float titleAlpha = 255 * titleNewTopMargin / TV_TITLE_MAX_TOP_MARGIN;
                if (titleAlpha < 0) {
                    titleAlpha = 0;
                }

                //设置相关控件的LayoutParams  此处使用的是MarginLayoutParams，便于设置params的topMargin属性
                tv_title.setTextColor(tv_title.getTextColors().withAlpha((int) titleAlpha));
                titleLayoutParams.topMargin = (int) titleNewTopMargin;
                tv_title.setLayoutParams(titleLayoutParams);

                searchLayoutParams.topMargin = (int) searchLayoutNewTopMargin;
                searchLayoutParams.width = (int) searchLayoutNewWidth;
                ll_search.setLayoutParams(searchLayoutParams);
            }
        });
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e("下拉刷新", "666");
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
