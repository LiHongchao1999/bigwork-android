package com.example.homeworkcorrect;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.homeworkcorrect.fragment.HomeworkFragment;
import com.example.homeworkcorrect.fragment.MyFragment;
import com.example.homeworkcorrect.fragment.MyFragmentMainContent;
import com.example.homeworkcorrect.fragment.OnlineShopFragment;
import com.example.homeworkcorrect.fragment.ParentCircleFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment currentFragment = new Fragment(); //当前fragment
    private MyFragment myFragment; //我的页面
    private MyFragmentMainContent mainContent; //主页页面
    private OnlineShopFragment onlineShopFragment; //商城页面
    private ParentCircleFragment parentCircleFragment; //家长圈页面
    private HomeworkFragment homeworkFragment;//作业页面
    private ImageView mainImg;
    private ImageView shopImg;
    private ImageView assignmentImg;
    private ImageView parentImg;
    private ImageView mineImg;
    private TextView mainText;
    private TextView shopText;
    private TextView assignmentText;
    private TextView parentText;
    private TextView mineText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取控件
        getViews();
        //获取fragment对象
        mainContent = new MyFragmentMainContent();
        onlineShopFragment = new OnlineShopFragment();
        parentCircleFragment = new ParentCircleFragment();
        myFragment = new MyFragment();
        homeworkFragment = new HomeworkFragment();
        //设置当前页
        changeTeb(mainContent);
        currentFragment = mainContent;
        mainImg.setImageResource(R.drawable.main1);
        mainText.setTextColor(Color.rgb(240,128,128));
    }
    private void getViews() {
        mainImg = findViewById(R.id.main_img);
        mainText = findViewById(R.id.main_text);
        shopImg = findViewById(R.id.shop_img);
        shopText = findViewById(R.id.shop_text);
        assignmentImg = findViewById(R.id.assignment_img);
        assignmentText = findViewById(R.id.assignment_text);
        parentImg = findViewById(R.id.parent_circle_img);
        parentText = findViewById(R.id.parent_circle_text);
        mineImg = findViewById(R.id.mine_img);
        mineText = findViewById(R.id.mine_text);
    }

    private void changeTeb(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (currentFragment != fragment) {
            if (!fragment.isAdded()) {
                transaction.add(R.id.tab_content, fragment);
            }
            transaction.hide(currentFragment);
            transaction.show(fragment);
            transaction.commit();
            currentFragment = fragment;
        }
    }
    /*
    * 单机事件
    * */
    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.main://主页
                changeTeb(mainContent);
                mainImg.setImageResource(R.drawable.main1);
                mainText.setTextColor(Color.rgb(240,128,128));
                shopImg.setImageResource(R.drawable.shop);
                shopText.setTextColor(Color.BLACK);
                assignmentImg.setImageResource(R.drawable.assignment);
                assignmentText.setTextColor(Color.BLACK);
                parentImg.setImageResource(R.drawable.friend);
                parentText.setTextColor(Color.BLACK);
                mineImg.setImageResource(R.drawable.my);
                mineText.setTextColor(Color.BLACK);
                break;
            case R.id.shop: //商城
                changeTeb(onlineShopFragment);
                mainImg.setImageResource(R.drawable.main);
                mainText.setTextColor(Color.BLACK);
                shopImg.setImageResource(R.drawable.shop1);
                shopText.setTextColor(Color.rgb(240,128,128));
                assignmentImg.setImageResource(R.drawable.assignment);
                assignmentText.setTextColor(Color.BLACK);
                parentImg.setImageResource(R.drawable.friend);
                parentText.setTextColor(Color.BLACK);
                mineImg.setImageResource(R.drawable.my);
                mineText.setTextColor(Color.BLACK);
                break;
            case R.id.assignment: //作业
                changeTeb(homeworkFragment);
                mainImg.setImageResource(R.drawable.main);
                mainText.setTextColor(Color.BLACK);
                shopImg.setImageResource(R.drawable.shop);
                shopText.setTextColor(Color.BLACK);
                assignmentImg.setImageResource(R.drawable.assignment1);
                assignmentText.setTextColor(Color.rgb(240,128,128));
                parentImg.setImageResource(R.drawable.friend);
                parentText.setTextColor(Color.BLACK);
                mineImg.setImageResource(R.drawable.my);
                mineText.setTextColor(Color.BLACK);
                break;
            case R.id.parent_circle: //家长圈
               changeTeb(parentCircleFragment);
                mainImg.setImageResource(R.drawable.main);
                mainText.setTextColor(Color.BLACK);
                shopImg.setImageResource(R.drawable.shop);
                shopText.setTextColor(Color.BLACK);
                assignmentImg.setImageResource(R.drawable.assignment);
                assignmentText.setTextColor(Color.BLACK);
                parentImg.setImageResource(R.drawable.friends1);
                parentText.setTextColor(Color.rgb(240,128,128));
                mineImg.setImageResource(R.drawable.my);
                mineText.setTextColor(Color.BLACK);
                break;
            case R.id.mine: //我的
                changeTeb(myFragment);
                mainImg.setImageResource(R.drawable.main);
                mainText.setTextColor(Color.BLACK);
                shopImg.setImageResource(R.drawable.shop);
                shopText.setTextColor(Color.BLACK);
                assignmentImg.setImageResource(R.drawable.assignment);
                assignmentText.setTextColor(Color.BLACK);
                parentImg.setImageResource(R.drawable.friend);
                parentText.setTextColor(Color.BLACK);
                mineImg.setImageResource(R.drawable.my1);
                mineText.setTextColor(Color.rgb(240,128,128));
                break;
        }
    }
}
