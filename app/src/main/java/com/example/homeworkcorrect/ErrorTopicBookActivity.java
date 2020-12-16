package com.example.homeworkcorrect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homeworkcorrect.adapter.ErrorTopicAdapter;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.entity.WrongQuestion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ErrorTopicBookActivity extends AppCompatActivity implements View.OnClickListener{

    private MyViewPager viewPager;
    private ArrayList<View> pageview;
    private TextView englishText;
    private TextView mathText;
    // 滚动条图片
    private ImageView scrollbar;
    // 滚动条初始偏移量
    private int offset = 0;
    // 当前页编号
    private int currIndex = 0;
    // 滚动条宽度
    private int bmpW;
    //一倍滚动量
    private int one;
    private OkHttpClient okHttpClient;
    private MyListView gvHomework;
    private View math;
    private View english;
    private ErrorTopicAdapter adapter;
    private ArrayList<WrongQuestion> questionList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_topic_book);
        viewPager = findViewById(R.id.viewpager);
        okHttpClient = new OkHttpClient();
        getViews();
        //获取从添加错题传来的数据
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if(type!=null && !type.equals("")){
            if (type.equals("math")){
                viewPager.setCurrentItem(0);
            }else{
                viewPager.setCurrentItem(1);
            }
            getQuestionOfSpecificSubject(type);
        }else{
            viewPager.setCurrentItem(0);
            getQuestionOfSpecificSubject("math");
        }
        //查找布局文件用LayoutInflater.inflate
        LayoutInflater mInflater = LayoutInflater.from(this);
        english = mInflater.inflate(R.layout.view_english, null);
        math = mInflater.inflate(R.layout.view_math, null);
        englishText.setOnClickListener(this);
        mathText.setOnClickListener(this);
        pageview =new ArrayList<View>();
        //添加想要切换的界面
        pageview.add(math);
        pageview.add(english);
        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter(){
            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return pageview.size();
            }

            @Override
            //判断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0==arg1;
            }
            //使从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageview.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1){
                ((ViewPager)arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }
        };
        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);
        //设置viewPager的初始界面为第一个界面
        gvHomework = math.findViewById(R.id.math_grid);
        //添加切换界面的监听器
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        // 获取滚动条的宽度
        //bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.scrollbar).getWidth();
        //为了获取屏幕宽度，新建一个DisplayMetrics对象
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //将当前窗口的一些信息放在DisplayMetrics类中
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //得到屏幕的宽度
        int screenW = displayMetrics.widthPixels;
        //计算出滚动条初始的偏移量
        offset = (screenW / 2 - bmpW) / 2;
        //计算出切换一个界面时，滚动条的位移量
        one = offset * 2 + bmpW;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        //将滚动条的初始位置设置成与左边界间隔一个offset
        scrollbar.setImageMatrix(matrix);
        //给每个item绑定单击事件
        gvHomework.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("错题本id",questionList.get(position).getId()+"");
                Intent intent = new Intent(ErrorTopicBookActivity.this,WrongQuestionDetailActivity.class);
                WrongQuestion question = questionList.get(position);
                Gson gson = new Gson();
                String str = gson.toJson(question);
                Log.e("错题本详情",str);
                intent.putExtra("question",str);
                startActivity(intent);
            }
        });
    }

    private void getViews() {
        englishText = findViewById(R.id.english);
        mathText = findViewById(R.id.math);
        scrollbar = findViewById(R.id.scroll);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.math:
                Log.e("当前页面","数学");
                viewPager.setCurrentItem(0);
                gvHomework = math.findViewById(R.id.math_grid);
                gvHomework.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("错题本id",questionList.get(position).getId()+"");
                        Intent intent = new Intent(ErrorTopicBookActivity.this,WrongQuestionDetailActivity.class);
                        WrongQuestion question = questionList.get(position);
                        Gson gson = new Gson();
                        String str = gson.toJson(question);
                        intent.putExtra("question",str);
                        startActivity(intent);
                    }
                });
                getQuestionOfSpecificSubject("math");
                break;
            case R.id.english:
                Log.e("当前页面","英语");
                viewPager.setCurrentItem(1);
                gvHomework = english.findViewById(R.id.english_grid);
                getQuestionOfSpecificSubject("english");
                gvHomework.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("错题本id",questionList.get(position).getId()+"");
                        Intent intent = new Intent(ErrorTopicBookActivity.this,WrongQuestionDetailActivity.class);
                        WrongQuestion question = questionList.get(position);
                        Gson gson = new Gson();
                        String str = gson.toJson(question);
                        intent.putExtra("question",str);
                        startActivity(intent);
                    }
                });
                break;
        }
    }

    public void onClicked(View view) {
        switch (view.getId()){
            case R.id.error_return:
                finish();
                break;
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    /**
                     * TranslateAnimation的四个属性分别为
                     * float fromXDelta 动画开始的点离当前View X坐标上的差值
                     * float toXDelta 动画结束的点离当前View X坐标上的差值
                     * float fromYDelta 动画开始的点离当前View Y坐标上的差值
                     * float toYDelta 动画开始的点离当前View Y坐标上的差值
                     **/
                    animation = new TranslateAnimation(one, 0, 0, 0);
                    gvHomework = math.findViewById(R.id.math_grid);
                    getQuestionOfSpecificSubject("math");
                    gvHomework.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ErrorTopicBookActivity.this,WrongQuestionDetailActivity.class);
                            WrongQuestion question = questionList.get(position);
                            Gson gson = new Gson();
                            String str = gson.toJson(question);
                            intent.putExtra("question",str);
                            startActivity(intent);
                        }
                    });
                    break;
                case 1:
                    animation = new TranslateAnimation(offset, one, 0, 0);
                    gvHomework = english.findViewById(R.id.english_grid);
                    getQuestionOfSpecificSubject("english");
                    gvHomework.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ErrorTopicBookActivity.this,WrongQuestionDetailActivity.class);
                            WrongQuestion question = questionList.get(position);
                            Gson gson = new Gson();
                            String str = gson.toJson(question);
                            intent.putExtra("question",str);
                            startActivity(intent);
                        }
                    });
                    break;
            }
            //arg0为切换到的页的编码
            currIndex = arg0;
            // 将此属性设置为true可以使得图片停在动画结束时的位置
            animation.setFillAfter(true);
            //动画持续时间，单位为毫秒
            animation.setDuration(200);
            //滚动条开始动画
            scrollbar.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


    public void getQuestionOfSpecificSubject(String subject){
        final Request request = new Request.Builder().url(IP.CONSTANT+"GetWrongQuestionListServlet?questionType="+subject).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时回调的方法
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("错题本",str);
                questionList = new Gson().fromJson(str,new TypeToken<ArrayList<WrongQuestion>>(){}.getType());
                //根据时间进行排序

                Log.e("获取到的错题数据",questionList.toString());
                adapter = new ErrorTopicAdapter(ErrorTopicBookActivity.this,questionList, R.layout.question_list_item_layout);
                runOnUiThread(new Runnable() {
                    public void run() {
                        gvHomework.setAdapter(adapter);
                    }
                });
            }
        });
    }
}