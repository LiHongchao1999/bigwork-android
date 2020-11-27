package com.example.homeworkcorrect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowBookInfoActivity extends AppCompatActivity {
    private Banner banner;
    //资源文件
    private Integer[] images = new Integer[1];
    private ArrayList<String> list_path = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_info);

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