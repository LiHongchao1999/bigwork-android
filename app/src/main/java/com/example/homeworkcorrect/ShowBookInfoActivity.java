package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowBookInfoActivity extends AppCompatActivity {
    private Banner banner;
    //资源文件
    private Integer[] images = new Integer[1];
    private ArrayList<String> list_path = new ArrayList<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_info);
        toolbar = findViewById(R.id.tool_book);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        list_path.add("https://ftp.bmp.ovh/imgs/2020/11/fa11ecddf576dead.jpg");
        list_path.add("https://ftp.bmp.ovh/imgs/2020/11/fa11ecddf576dead.jpg");




        findAllView();
    }

    private void findAllView() {

        banner = findViewById(R.id.banner_book);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list_path);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
}