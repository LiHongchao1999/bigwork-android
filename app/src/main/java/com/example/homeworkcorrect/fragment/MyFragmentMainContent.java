package com.example.homeworkcorrect.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.example.homeworkcorrect.Camera2Activity;
import com.example.homeworkcorrect.InformationActivity;
import com.example.homeworkcorrect.R;


public class MyFragmentMainContent extends Fragment {
    private LinearLayout llcamera;
    private View view;
    private LinearLayout llrecommand;
    private ImageView ring;
    private ImageView rotate;
    private ImageView advertise;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mymaincontent,
                container,
                false);
        getViews();
        combineChange();
        MyListener listener=new MyListener();
        llcamera.setOnClickListener(listener);
        llrecommand.setOnClickListener(listener);
        ring.setOnClickListener(listener);

        llrecommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.example);
                Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, null, null));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TITLE,"分享到");
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        });
        return view;
    }
    public class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.ll_camera:
                    Intent intentc=new Intent(getContext(), Camera2Activity.class);
                    startActivity(intentc);
                    break;
                case R.id.iv_ring:
                    Intent intent = new Intent(getContext(), InformationActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
    public void getViews(){
       llcamera=view.findViewById(R.id.ll_camera);
       llrecommand=view.findViewById(R.id.ll_recommand);
       rotate=view.findViewById(R.id.rotate);
       ring = view.findViewById(R.id.iv_ring);
       advertise = view.findViewById(R.id.advertise);
       RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(),R.drawable.advertise));
       circularBitmapDrawable.setCornerRadius(200);
       advertise.setImageDrawable(circularBitmapDrawable);
    }
    public void combineChange(){

        RotateAnimation rotateAnimation = new RotateAnimation(0.0f,360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setDuration(20000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotate.startAnimation(rotateAnimation);
    }
}
