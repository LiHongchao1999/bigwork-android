package com.example.homeworkcorrect.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.homeworkcorrect.CircleDetailActivity;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.SearchUserActivity;
import com.example.homeworkcorrect.adapter.CustomCircleAdapter;
import com.example.homeworkcorrect.adapter.CustomSelectAdapter;
import com.example.homeworkcorrect.entity.Circle;

import java.util.ArrayList;
import java.util.List;


public class ParentCircleFragment extends Fragment {
    private View view;
    private String[] arr = {"附近圈子","好友圈子"};
    private EditText circle; //圈子
    private ImageView down;  //向上、向下
    private PopupWindow popupWindow;
    private CustomSelectAdapter customSelectAdapter;
    private RelativeLayout relativeLayout;
    private ImageView add;//添加新朋友
    private ListView listView;
    private List<Circle> circles = new ArrayList<>();
    private CustomCircleAdapter circleAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.parent_circle_fragment,container,false);
        //获取控件
        getViews();
        //绑定自定义适配器
        Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.my1);
        Bitmap img1 = BitmapFactory.decodeResource(getResources(),R.drawable.my1);
        Circle circlew = new Circle(img,"sss","2020/02/01 7:15","今天天气不错",img1,10,20,10);
        Circle circlea = new Circle(img,"bbb","2020/02/01 7:15","今天天气真好",img1,10,20,10);
        Circle circleb = new Circle(img,"www","2020/02/01 7:15","今天天气真SaaS",img1,10,20,10);
        Circle circlec = new Circle(img,"ccc","2020/02/01 7:15","今天天气真111",img1,10,20,10);
        Circle circled = new Circle(img,"eee","2020/02/01 7:15","今天天气真奥术大师",img1,10,20,10);
        Circle circlee = new Circle(img,"zzz","2020/02/01 7:15","今天天气真撒发放",img1,10,20,10);
        circles.add(circlew);
        circles.add(circlea);
        circles.add(circleb);
        circles.add(circlec);
        circles.add(circled);
        circles.add(circlee);
        circleAdapter = new CustomCircleAdapter(getContext(),circles,R.layout.circle_item_list_layout);
        listView.setAdapter(circleAdapter);
        //给每个item设置监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到对应的详情页
                Intent intent = new Intent(getContext(), CircleDetailActivity.class);
                startActivity(intent);
            }
        });
        //绑定监听器
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //对图片进行判断
                if(down.getTag().equals("select")){//箭头是向下的
                    //点击会弹出列表框
                    clickArrow();
                    //换标记
                    down.setTag("unSelect");
                    Log.e("tag",down.getTag()+"");
                    //点击向下箭头，修改箭头图标以及填充色
                    down.setImageDrawable(getResources().getDrawable(R.drawable.up));
                    relativeLayout.setBackground(getResources().getDrawable(R.drawable.circle_style_border1));
                    //修改字体颜色
                    circle.setTextColor(getResources().getColor(R.color.colorCircle));
                }else{//箭头是向上的
                    //点击弹出框消失
                    down.setTag("select");
                    popupWindow.dismiss();
                    //点击向下箭头，修改箭头图标以及填充色
                    down.setImageDrawable(getResources().getDrawable(R.drawable.down));
                    relativeLayout.setBackground(getResources().getDrawable(R.drawable.circle_style_border));
                    //修改字体颜色
                    circle.setTextColor(getResources().getColor(R.color.colorFill));
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击添加朋友
                Intent intent = new Intent(getContext(), SearchUserActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    /*
    * 弹出列表
    * */
    private void clickArrow() {
        if(popupWindow == null){
            //弹出一个listview的下拉框
            ListView listView = new ListView(getContext());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String str = arr[position];
                    circle.setText(str);
                    popupWindow.dismiss();
                    //点击某个item后，改变背景图，以及图标
                    down.setImageDrawable(getResources().getDrawable(R.drawable.down));
                    relativeLayout.setBackground(getResources().getDrawable(R.drawable.circle_style_border));
                    //修改字体颜色
                    circle.setTextColor(getResources().getColor(R.color.colorFill));
                }
            });
            customSelectAdapter = new CustomSelectAdapter(getContext(),arr,R.layout.item_list_layout);
            listView.setAdapter(customSelectAdapter);
            int width=relativeLayout.getWidth();
            int height= ViewGroup.LayoutParams.WRAP_CONTENT;
            popupWindow = new PopupWindow(listView,width,height);
            //设置获取焦点，防止多次弹出，实现点一次弹出一次，在点一次收起
            //设置PopUpWindow的焦点，设置为true之后，PopupWindow内容区域，才可以响应点击事件
            popupWindow.setFocusable(true);
            //设置边缘点击收起
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            //设置popupwindow关闭监听器
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if(down.getTag().equals("unSelect")){
                        popupWindow.dismiss();
                        down.setTag("select");
                        //点击向下箭头，修改箭头图标以及填充色
                        down.setImageDrawable(getResources().getDrawable(R.drawable.down));
                        relativeLayout.setBackground(getResources().getDrawable(R.drawable.circle_style_border));
                        //修改字体颜色
                        circle.setTextColor(getResources().getColor(R.color.colorFill));
                    }
                }
            });
        }
        popupWindow.showAsDropDown(relativeLayout);
    }
    private void getViews() {
        circle = view.findViewById(R.id.edit_circle);
        down = view.findViewById(R.id.img_down);
        circle.setFocusable(false);
        circle.setFocusableInTouchMode(false);
        relativeLayout = view.findViewById(R.id.relative_layout);
        add = view.findViewById(R.id.add_user);
        listView = view.findViewById(R.id.listview);
    }
}
