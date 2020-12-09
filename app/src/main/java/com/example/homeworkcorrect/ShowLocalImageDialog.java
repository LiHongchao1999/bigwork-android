package com.example.homeworkcorrect;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.adapter.ShowImagesAdapter;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowLocalImageDialog extends Dialog {
    private View mView ;
    private Context mContext;
    private ShowImagesViewPager mViewPager;
    private TextView mIndexText;
    private List<String> mImgUrls;
    private List<String> mTitles;
    private List<View> mViews;
    private ShowImagesAdapter mAdapter;
    private int current;
    public ShowLocalImageDialog(@NonNull Context context, List<String> imgUrls,int i) {
        super(context, R.style.transparentBgDialog);
        this.mContext = context;
        this.mImgUrls = imgUrls;
        this.current = i;
        initView();
        initData();
    }
    private void initView() {
        mView = View.inflate(mContext, R.layout.dialog_images_brower, null);
        mViewPager = (ShowImagesViewPager) mView.findViewById(R.id.vp_images);
        mIndexText = (TextView) mView.findViewById(R.id.tv_image_index);
        mTitles = new ArrayList<>();
        mViews = new ArrayList<>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(mView);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        DisplayMetrics dm2 = getContext().getResources().getDisplayMetrics();
        wl.height = dm2.heightPixels;
        wl.width = dm2.widthPixels; //屏幕宽高的属性由DisplayMetrics类获得
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
    }
    private void initData() {
        //点击图片监听
        PhotoViewAttacher.OnPhotoTapListener listener = new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                dismiss();
            }
        };
        for (int i = 0; i < mImgUrls.size(); i++) {
            final PhotoView photoView = new PhotoView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(layoutParams);
            photoView.setOnPhotoTapListener(listener);
            //点击图片外围（无图片处）监听

            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y){
                    dismiss();
                }
            });
            final ObjectAnimator anim = ObjectAnimator.ofInt(photoView, "ImageLevel", 0,10000);
            anim.setDuration(3000);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(ObjectAnimator.INFINITE);
            anim.start();

            Glide.with(mContext)
                    .load(mImgUrls.get(i))
                    .placeholder(R.drawable.rotate_loading)
                    .error(R.drawable.fail)
                    .into(photoView);
            mViews.add(photoView);
            mTitles.add(i + "");
        }

        mAdapter = new ShowImagesAdapter(mViews, mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(current);
        mIndexText.setText(current+1 + "/" + mImgUrls.size());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                mIndexText.setText(position +1  + "/" + mImgUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

