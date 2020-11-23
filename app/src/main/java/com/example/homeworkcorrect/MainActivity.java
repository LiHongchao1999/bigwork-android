package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.homeworkcorrect.fragment.MyFragment;
import com.example.homeworkcorrect.fragment.ParentCircleFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();
    private String userName;
    private String demo;
    private String userName1;
    private String demo1;
    private String userName2;
    private String userNam4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取FragmentTabHost的引用
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        //初始化

        fragmentTabHost.setup(this,
                getSupportFragmentManager(),//管理多个Fragment对象的管理器
                android.R.id.tabcontent);//显示内容页面的控件的id


        //创建内容页面TabSpec对象
        TabHost.TabSpec tab1 = fragmentTabHost.newTabSpec("first_tab")
                .setIndicator(getTabSpecView("first_tab","主页",R.drawable.main1));
        //Class参数：类名.class,对象.getClass()
        fragmentTabHost.addTab(tab1,
                MyFragment.class,//FristFragment类的Class（字节码）对象
                null);//传递数据时使用，不需要传递数据直接传null


        //创建内容页面TabSpec对象
        TabHost.TabSpec tab2 = fragmentTabHost.newTabSpec("second_tab")
                .setIndicator(getTabSpecView("second_tab","商城",R.drawable.shop));
        //Class参数：类名.class,对象.getClass()
        fragmentTabHost.addTab(tab2,
                MyFragment.class,//FristFragment类的Class（字节码）对象
                null);//传递数据时使用，不需要传递数据直接传null


        //创建内容页面TabSpec对象
        TabHost.TabSpec tab3 = fragmentTabHost.newTabSpec("third_tab")
                .setIndicator(getTabSpecView("third_tab","作业",R.drawable.assignment));
        //Class参数：类名.class,对象.getClass()
        fragmentTabHost.addTab(tab3,
                MyFragment.class,//FristFragment类的Class（字节码）对象
                null);//传递数据时使用，不需要传递数据直接传null


        //创建内容页面TabSpec对象
        TabHost.TabSpec tab4 = fragmentTabHost.newTabSpec("forth_tab")
                .setIndicator(getTabSpecView("forth_tab","家长圈",R.drawable.friend));
        //Class参数：类名.class,对象.getClass()
        fragmentTabHost.addTab(tab4,
                ParentCircleFragment.class,//FristFragment类的Class（字节码）对象
                null);//传递数据时使用，不需要传递数据直接传null


        //创建内容页面TabSpec对象
        TabHost.TabSpec tab5 = fragmentTabHost.newTabSpec("fifth_tab")
                .setIndicator(getTabSpecView("fifth_tab","我的",R.drawable.my));
        //Class参数：类名.class,对象.getClass()
        fragmentTabHost.addTab(tab5,
                MyFragment.class,//FristFragment类的Class（字节码）对象
                null);//传递数据时使用，不需要传递数据直接传null


        //处理fragmentTabHost的选项切换事件
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //修改图片和文字的颜色
                switch (tabId){
                    case "first_tab"://选中了短信
                        imageViewMap.get("first_tab").setImageResource(R.drawable.main1);
                        textViewMap.get("first_tab").setTextColor(Color.rgb(240,128,128));
                        imageViewMap.get("second_tab").setImageResource(R.drawable.shop);
                        textViewMap.get("second_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("third_tab").setImageResource(R.drawable.assignment);
                        textViewMap.get("third_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("forth_tab").setImageResource(R.drawable.friend);
                        textViewMap.get("forth_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("fifth_tab").setImageResource(R.drawable.my);
                        textViewMap.get("fifth_tab").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "second_tab"://选中了联系人
                        imageViewMap.get("first_tab").setImageResource(R.drawable.main);
                        textViewMap.get("first_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("second_tab").setImageResource(R.drawable.shop1);
                        textViewMap.get("second_tab").setTextColor(Color.rgb(240,128,128));
                        imageViewMap.get("third_tab").setImageResource(R.drawable.assignment);
                        textViewMap.get("third_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("forth_tab").setImageResource(R.drawable.friend);
                        textViewMap.get("forth_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("fifth_tab").setImageResource(R.drawable.my);
                        textViewMap.get("fifth_tab").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "third_tab"://选中了短信
                        imageViewMap.get("first_tab").setImageResource(R.drawable.main);
                        textViewMap.get("first_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("second_tab").setImageResource(R.drawable.shop);
                        textViewMap.get("second_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("third_tab").setImageResource(R.drawable.assignment1);
                        textViewMap.get("third_tab").setTextColor(Color.rgb(240,128,128));
                        imageViewMap.get("forth_tab").setImageResource(R.drawable.friend);
                        textViewMap.get("forth_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("fifth_tab").setImageResource(R.drawable.my);
                        textViewMap.get("fifth_tab").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "forth_tab"://选中了联系人
                        imageViewMap.get("first_tab").setImageResource(R.drawable.main);
                        textViewMap.get("first_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("second_tab").setImageResource(R.drawable.shop);
                        textViewMap.get("second_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("third_tab").setImageResource(R.drawable.assignment);
                        textViewMap.get("third_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("forth_tab").setImageResource(R.drawable.friends1);
                        textViewMap.get("forth_tab").setTextColor(Color.rgb(240,128,128));
                        imageViewMap.get("fifth_tab").setImageResource(R.drawable.my);
                        textViewMap.get("fifth_tab").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "fifth_tab"://选中了短信
                        imageViewMap.get("first_tab").setImageResource(R.drawable.main);
                        textViewMap.get("first_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("second_tab").setImageResource(R.drawable.shop);
                        textViewMap.get("second_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("third_tab").setImageResource(R.drawable.assignment);
                        textViewMap.get("third_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("forth_tab").setImageResource(R.drawable.friend);
                        textViewMap.get("forth_tab").setTextColor(getResources().getColor(android.R.color.black));
                        imageViewMap.get("fifth_tab").setImageResource(R.drawable.my1);
                        textViewMap.get("fifth_tab").setTextColor(Color.rgb(240,128,128));
                        break;
                }
            }
        });

        //设置默认选中的标签页:参数是下标
        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("first_tab").setImageResource(R.drawable.main1);
        textViewMap.get("first_tab").setTextColor(Color.rgb(240,128,128));
        imageViewMap.get("second_tab").setImageResource(R.drawable.shop);
        textViewMap.get("second_tab").setTextColor(getResources().getColor(android.R.color.black));
        imageViewMap.get("third_tab").setImageResource(R.drawable.assignment);
        textViewMap.get("third_tab").setTextColor(getResources().getColor(android.R.color.black));
        imageViewMap.get("forth_tab").setImageResource(R.drawable.friend);
        textViewMap.get("forth_tab").setTextColor(getResources().getColor(android.R.color.black));
        imageViewMap.get("fifth_tab").setImageResource(R.drawable.my);
        textViewMap.get("fifth_tab").setTextColor(getResources().getColor(android.R.color.black));
    }
    public View getTabSpecView(String tag, String tilte, int drawable){
        View view = getLayoutInflater().inflate(R.layout.tab_spec_layout,null);

        //获取tab_spec_layout布局当中视图控件的引用
        ImageView icon = view.findViewById(R.id.icon);
        icon.setImageResource(drawable);

        //将ImageView对象存储到Map中
        imageViewMap.put(tag,icon);

        TextView tvTitle = view.findViewById(R.id.title);
        tvTitle.setText(tilte);

        textViewMap.put(tag,tvTitle);

        return view;
    }
}
