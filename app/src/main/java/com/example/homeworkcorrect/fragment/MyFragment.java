package com.example.homeworkcorrect.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.homeworkcorrect.ContactUsActivity;
import com.example.homeworkcorrect.ErrorTopicBookActivity;
import com.example.homeworkcorrect.LoginActivity;
import com.example.homeworkcorrect.MoneyActivity;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.SelfInformationActivity;
import com.example.homeworkcorrect.SettingActivity;
import com.example.homeworkcorrect.alipay.PayActivity;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.chat.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView nickName;
    private TextView lever;
    private CircleImageView img;
    private TextView login;

    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my, container, false);
        Toolbar toolbar = view.findViewById(R.id.tool);
        //点击我的拍拍币
        RelativeLayout money = view.findViewById(R.id.clap_money);
        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.getText().toString()==null || login.getText().toString().equals("")) {
                    Intent intent = new Intent(getContext(), MoneyActivity.class);
                    startActivityForResult(intent,210);
                }
            }
        });
        //点击分享
        RelativeLayout relativeLayout = view.findViewById(R.id.recommend);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
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
        //点击用户头像
        img = view.findViewById(R.id.image_user);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.getText().toString()==null || login.getText().toString().equals("")) {
                    Intent intent = new Intent(getContext(), SelfInformationActivity.class);
                    startActivityForResult(intent,10);
                }
            }
        });
        //点击设置
        RelativeLayout setting = view.findViewById(R.id.setting_relative);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivityForResult(intent,66);
            }
        });
        //点击联系我们
        RelativeLayout contactUs = view.findViewById(R.id.contactUs);
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ContactUsActivity.class);
                startActivity(intent);
            }
        });
        //点击登录
        login = view.findViewById(R.id.login_kip);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent,100);
            }
        });
        nickName = view.findViewById(R.id.user_name);
        lever = view.findViewById(R.id.user_lever);
        img = view.findViewById(R.id.image_user);
        //点击错题本
        RelativeLayout errorBook = view.findViewById(R.id.error_topic_book);
        errorBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ErrorTopicBookActivity.class);
                startActivity(intent);
            }
        });
        if(UserCache.user != null){//表示当前用户已经登录
            lever.setVisibility(View.VISIBLE);
            nickName.setVisibility(View.VISIBLE);
            nickName.setText(UserCache.user.getNickname());
            lever.setText("lv1");
            login.setText("");
            login.setVisibility(View.INVISIBLE);
            Log.e("用户头像",UserCache.user.getImage()+"");
            Glide.with(getContext())
                    .load(IP.CONSTANT+"userImage/"+ UserCache.user.getImage())
                    .into(img);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(UserCache.user != null ){//表示当前用户已经登录
            lever.setVisibility(View.VISIBLE);
            nickName.setVisibility(View.VISIBLE);
            nickName.setText(UserCache.user.getNickname());
            lever.setText("lv1");
            login.setText("");
            login.setVisibility(View.INVISIBLE);
            Glide.with(getContext())
                    .load(IP.CONSTANT+"userImage/"+ UserCache.user.getImage())
                    .into(img);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode == 150){//QQ登录
            lever.setVisibility(View.VISIBLE);
            nickName.setVisibility(View.VISIBLE);
            nickName.setText(UserCache.user.getNickname()+"");
            lever.setText("lv1");
            login.setText("");
            login.setVisibility(View.INVISIBLE);
            Glide.with(getContext()).load(IP.CONSTANT+"userImage/"+UserCache.user.getImage()).into(img);
        }
        if (requestCode==100 && resultCode==160){ //手机号+验证码登录
            //设置个人信息
            lever.setVisibility(View.VISIBLE);
            nickName.setVisibility(View.VISIBLE);
            nickName.setText(UserCache.user.getNickname());
            lever.setText("lv1");
            login.setText("");
            login.setVisibility(View.INVISIBLE);
            Glide.with(getContext())
                    .load(IP.CONSTANT+"userImage/"+ UserCache.user.getImage())
                    .into(img);
        }
        if (requestCode==100 && resultCode==200){
            String phone = data.getStringExtra("phone");
            lever.setVisibility(View.VISIBLE);
            nickName.setVisibility(View.VISIBLE);
            nickName.setText(phone);
            lever.setText("lv1");
            login.setText("");
            login.setVisibility(View.INVISIBLE);
            img.setImageDrawable(getResources().getDrawable(R.drawable.head));
        }
        if (requestCode==10 && resultCode==20){//修改完个人信息返回的
            lever.setVisibility(View.VISIBLE);
            nickName.setVisibility(View.VISIBLE);
            nickName.setText(UserCache.user.getNickname());
            lever.setText("lv1");
            login.setText("");
            login.setVisibility(View.INVISIBLE);
            Glide.with(getContext())
                    .load(IP.CONSTANT+"userImage/"+UserCache.user.getImage());
        }
        if(requestCode==66&&resultCode==666){//注销登录
            img.setVisibility(View.VISIBLE);
            img.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.initial));
            login.setVisibility(View.VISIBLE);
            login.setText("点击登录");
            lever.setVisibility(View.INVISIBLE);
            nickName.setVisibility(View.INVISIBLE);
        }
    }
}