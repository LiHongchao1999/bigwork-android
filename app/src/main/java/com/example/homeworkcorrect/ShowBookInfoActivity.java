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

        list_path.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606371190038&di=1f7e0dece43b4a4b91ae6bd7e8f479c9&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201706%2F10%2F20170610192627_yhAMN.thumb.700_0.jpeg");
        list_path.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606371190038&di=1f7e0dece43b4a4b91ae6bd7e8f479c9&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201706%2F10%2F20170610192627_yhAMN.thumb.700_0.jpeg");



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