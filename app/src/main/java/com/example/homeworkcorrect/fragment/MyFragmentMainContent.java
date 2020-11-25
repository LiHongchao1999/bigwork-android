package com.example.homeworkcorrect.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.homeworkcorrect.InformationActivity;
import com.example.homeworkcorrect.Camera2Activity;
import com.example.homeworkcorrect.Camera2Activity_ViewBinding;
import com.example.homeworkcorrect.R;


public class MyFragmentMainContent extends Fragment {
    private LinearLayout llcamera;
    private View view;
    private LinearLayout llrecommand;
    private RelativeLayout rlerrors;
    private RelativeLayout rlperson;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mymaincontent,
                container,
                false);
        getViews();
        MyListener listener=new MyListener();
        llcamera.setOnClickListener(listener);
        llrecommand.setOnClickListener(listener);
        rlperson.setOnClickListener(listener);
        rlerrors.setOnClickListener(listener);
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
       rlerrors=view.findViewById(R.id.rl_errors);
       rlperson=view.findViewById(R.id.rl_person);
    }
}
