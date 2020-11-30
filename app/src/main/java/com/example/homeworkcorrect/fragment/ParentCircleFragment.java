package com.example.homeworkcorrect.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.homeworkcorrect.CircleDetailActivity;
import com.example.homeworkcorrect.PublishCircleActivity;
import com.example.homeworkcorrect.PublishImageActivity;
import com.example.homeworkcorrect.PublishVideoActivity;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.adapter.CustomCircleAdapter;
import com.example.homeworkcorrect.adapter.CustomSelectAdapter;
import com.example.homeworkcorrect.entity.Circle;
import com.example.homeworkcorrect.entity.PopWindowEntity;

import java.util.ArrayList;
import java.util.List;


public class ParentCircleFragment extends Fragment {
    private View view;
    private LinearLayout nearCircle;
    private LinearLayout freCircle;
    private TextView publicSection;
    private ImageView publish;//发表朋友圈
    private ListView listView;
    private List<Circle> circles = new ArrayList<>();
    private CustomCircleAdapter circleAdapter;
    private PopupWindow popupWindow;
    private CustomSelectAdapter selectAdapter;
    private List<PopWindowEntity> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.parent_circle_fragment,container,false);
        //获取控件
        getViews();
        list = new ArrayList<>();
        //设置弹出框绑定的listview以及初始化popupwindow
        setListView();
        //绑定自定义适配器
        Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.my1);
        Bitmap img1 = BitmapFactory.decodeResource(getResources(),R.drawable.my1);
        Bitmap img2 = BitmapFactory.decodeResource(getResources(),R.drawable.cake04);
        Bitmap img3 = BitmapFactory.decodeResource(getResources(),R.drawable.cake03);
        Bitmap img4 = BitmapFactory.decodeResource(getResources(),R.drawable.cake02);
        List<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(img1);
        bitmaps.add(img2);
        bitmaps.add(img3);
        bitmaps.add(img4);
        Circle circlew = new Circle(img,"sss","2020/02/01 7:15","今天天气不错",bitmaps,10,20,10);
        Circle circlea = new Circle(img,"bbb","2020/02/01 7:15","今天天气真好",bitmaps,10,20,10);
        Circle circleb = new Circle(img,"www","2020/02/01 7:15","今天天气真SaaS",bitmaps,10,20,10);
        Circle circlec = new Circle(img,"ccc","2020/02/01 7:15","今天天气真111",bitmaps,10,20,10);
        Circle circled = new Circle(img,"eee","2020/02/01 7:15","今天天气真奥术大师",bitmaps,10,20,10);
        Circle circlee = new Circle(img,"zzz","2020/02/01 7:15","今天天气真撒发放",bitmaps,10,20,10);
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
        //准备数据
        list.add(new PopWindowEntity(R.drawable.print,"发表"));
        list.add(new PopWindowEntity(R.drawable.image,"图片"));
        list.add(new PopWindowEntity(R.drawable.video,"视频"));
        //设置add的tag
        publish.setTag(R.id.tag_first,"close");
        //设置监听器类
        MyListener myListener = new MyListener();
        publish.setOnClickListener(myListener);
        nearCircle.setOnClickListener(myListener);
        freCircle.setOnClickListener(myListener);
        return view;
    }
    /*
    * 设置弹出框的数据listview
    * */
    private void setListView() {
        ListView listView = new ListView(getContext());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (list.get(position).getSection()){
                    case "发表":
                        Intent intent = new Intent(getContext(), PublishCircleActivity.class);
                        startActivityForResult(intent,100);
                        break;
                    case "图片":
                        Intent intent1 = new Intent(getContext(), PublishImageActivity.class);
                        startActivityForResult(intent1,200);
                        break;
                    case "视频":
                        Intent intent2 = new Intent(getContext(), PublishVideoActivity.class);
                        startActivityForResult(intent2,300);
                        break;
                }
            }
        });
        selectAdapter = new CustomSelectAdapter(getContext(),list,R.layout.item_list_layout);
        listView.setAdapter(selectAdapter);
        //弹出一个listview的下拉框
        int width=300;
        int height= ViewGroup.LayoutParams.WRAP_CONTENT;
        popupWindow = new PopupWindow(listView,width,height);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.e("tag",publish.getTag(R.id.tag_first)+"");
        if(popupWindow.isShowing()){
            publish.setTag(R.id.tag_first,"close");
            popupWindow.dismiss();
            backgroundAlpha(1.0f);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==150){//接收到发表的动态信息
            //传递自定义类型对象（第三种方式）
//                Student student = new Student("张三","110","110");
//                intent.putExtra("stu",student);
            //第三种方式接受数据
//        Student stu= (Student) request.getSerializableExtra("stu");
//        String name = stu.getName();
//        String phone = stu.getPhone();
//        String email = stu.getEmail();
        }
        if(requestCode==200 && resultCode==250){//接收到发表的图片动态信息

        }
        if(requestCode==300 && resultCode==350){//接收到发表的视频动态信息

        }
    }

    private void getViews() {
        nearCircle = view.findViewById(R.id.circle_near);
        freCircle = view.findViewById(R.id.circle_fre);
        publish = view.findViewById(R.id.publish);
        listView = view.findViewById(R.id.listview);
        publicSection = view.findViewById(R.id.circle_public);
    }
    /*
    * 自定义Listener*/
    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.circle_near://点击的是附近圈子
                    nearCircle.setBackground(getResources().getDrawable(R.drawable.circle_border_style2));
                    freCircle.setBackground(getResources().getDrawable(R.drawable.circle_style_border1));
                    publicSection.setBackground(getResources().getDrawable(R.drawable.public_section_style));
                    break;
                case R.id.circle_fre://点击的是好友圈子
                    freCircle.setBackground(getResources().getDrawable(R.drawable.circle_border_style3));
                    nearCircle.setBackground(getResources().getDrawable(R.drawable.circle_style_border));
                    publicSection.setBackground(getResources().getDrawable(R.drawable.public_section_style1));
                    break;
                case R.id.publish:
                    //绑定监听器
                    //对图片进行判断
                    //点击会弹出列表框
                    if(publish.getTag(R.id.tag_first).equals("close")){
                        clickArrow();
                        //换标记
                        backgroundAlpha(0.5f);
                        publish.setTag(R.id.tag_first,"open");
                    }else{
                        popupWindow.dismiss();
                        backgroundAlpha(1.0f);
                        publish.setTag(R.id.tag_first,"close");
                    }
                    break;
            }
        }
    }
    /*
     * popupwindow弹出列表
     * */
    private void clickArrow() {
        if(popupWindow == null){
            popupWindow.setBackgroundDrawable(getDrawable());
            //设置获取焦点，防止多次弹出，实现点一次弹出一次，在点一次收起
            //设置PopUpWindow的焦点，设置为true之后，PopupWindow内容区域，才可以响应点击事件
            popupWindow.setFocusable(true);
            //设置边缘点击收起
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);

            //设置popupwindow关闭监听器
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f);
                    publish.setTag(R.id.tag_first,"close");
                }
            });
        }
        popupWindow.showAtLocation(publish, Gravity.NO_GRAVITY,770,210);
    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 生成一个 透明的背景图片
     * @return
     */
    private Drawable getDrawable() {
        ShapeDrawable bgdrawable = new ShapeDrawable(new OvalShape());
        bgdrawable.getPaint().setColor(getContext().getResources().getColor(android.R.color.transparent));
        return bgdrawable;
    }
}

