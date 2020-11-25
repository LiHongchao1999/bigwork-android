package com.example.homeworkcorrect.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.homeworkcorrect.GlideImageLoader;
import com.example.homeworkcorrect.R;
import com.youth.banner.Banner;

import java.util.Arrays;

public class OnlineShopFragment extends Fragment {
    private View root;
    private Banner banner;

    //资源文件
    private Integer[] images = {R.drawable.cake01, R.drawable.cake02, R.drawable.cake03, R.drawable.cake04, R.drawable.cake05};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_online_shop, container, false);

        findViews();
        setListeners();

        Banner banner = (Banner) root.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(Arrays.asList(images));
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        return root;
    }

    private void setListeners() {
    }

    private void findViews() {
        banner = root.findViewById(R.id.banner);
    }
}
