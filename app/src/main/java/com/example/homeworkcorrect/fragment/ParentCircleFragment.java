package com.example.homeworkcorrect.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.homeworkcorrect.CircleDetailActivity;
import com.example.homeworkcorrect.MyListView;
import com.example.homeworkcorrect.PublishCircleActivity;
import com.example.homeworkcorrect.PublishImageActivity;
import com.example.homeworkcorrect.PublishVideoActivity;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.adapter.CustomCircleAdapter;
import com.example.homeworkcorrect.adapter.CustomSelectAdapter;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.entity.Circle;
import com.example.homeworkcorrect.entity.Like;
import com.example.homeworkcorrect.entity.PopWindowEntity;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import io.rong.imageloader.utils.L;
import io.rong.imlib.model.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ParentCircleFragment extends Fragment {
    private OkHttpClient client;
    private View view;
    private List like = new ArrayList();
    private LinearLayout nearCircle; //附近圈子
    private LinearLayout freCircle;  //好友圈子
    private TextView publicSection;
    private ImageView publish;//发表朋友圈
    private MyListView listView;  //动态列表
    private List<Circle> circles;
    private CustomCircleAdapter circleAdapter;
    private PopupWindow popupWindow;
    private CustomSelectAdapter selectAdapter;
    private List<PopWindowEntity> list; //动态、图片、视频
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    circleAdapter = new CustomCircleAdapter(getContext(),circles,R.layout.circle_item_list_layout,like);
                    listView.setAdapter(circleAdapter);
                    //给每个item绑定单机事件
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //点击跳转到圈子详情页
                            Intent intent = new Intent(getContext(),CircleDetailActivity.class);
                            String str = new Gson().toJson(circles.get(position));
                            intent.putExtra("circle",str);
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.parent_circle_fragment,container,false);
        client = new OkHttpClient();
        //获取控件
        getViews();
        list = new ArrayList<>();
        //设置弹出框绑定的listview以及初始化popupwindow
        setListView();
        if (UserCache.user != null){
            Log.e("获取",""+UserCache.user.getId());
            //从服务端获取最新的动态
            getCircleListInfo();
        }else {
            Toast.makeText(getContext(), "您还未登录", Toast.LENGTH_SHORT).show();
        }
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
    * 从服务端获取信息
    * */
    private void getCircleListInfo() {
        //请求体是普通的字符串
        //3、创建请求对象
        Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .url(IP.CONSTANT+"circle/getCircles")
                .build();
        //4、创建Call对象，发送请求，并接受响应
        Call call = new OkHttpClient().newCall(request);
        //如果使用异步请求，不需要手动使用子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时候回调
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功以后回调
                String str = response.body().string();//字符串数据
                Log.e("朋友圈",str);
                Type collectionType = new TypeToken<List<Circle>>(){}.getType();
                circles = new Gson().fromJson(str,collectionType);
                Message msg = new Message();
                msg.what=1;
                handler.sendMessage(msg);
            }
        });
    }
    /*private void getLikelist() {
        //请求体是普通的字符串
        //3、创建请求对象
        Request request = new Request.Builder()//调用post方法表示请求方式为post请求   put（.put）
                .url(IP.CONSTANT+"like/getLikes")
                .build();
        //4、创建Call对象，发送请求，并接受响应
        Log.e("执行getLikeList","mmm");
        Call call = new OkHttpClient().newCall(request);
        //如果使用异步请求，不需要手动使用子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时候回调
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功以后回调
                String str = response.body().string();//字符串数据
                Log.e("点赞", str);
                Type collectionType = new TypeToken<List<Like>>() {
                }.getType();
                like = new Gson().fromJson(str, collectionType);
                Log.e("点赞信息", like.toString());
            }
        });
    }
*/
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
        listView.setDivider(getResources().getDrawable(R.color.colorFill));
        listView.setDividerHeight(0);
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
        if (UserCache.user != null){
            Log.e("获取",""+UserCache.user.getId());
            //从服务端获取最新的动态
            getCircleListInfo();
        }else{
            Toast.makeText(getContext(),"您还未登录",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        /*if(hidden){
        return;
        }else { circles=null;
            like=null;
            getLikelist();
            getCircleListInfo();
        }*/
        //从服务端获取最新的动态
        getCircleListInfo();
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
            String str = data.getStringExtra("circle");
            Log.e("接收到发表的动态信息",str);
            Circle circle = new Gson().fromJson(str,Circle.class);
            circles.add(circle);
            circleAdapter.notifyDataSetChanged();
        }
        if(requestCode==200 && resultCode==250){//接收到发表的图片动态信息
            String str = data.getStringExtra("circle");
            Log.e("接收到发表的图片信息",str);
            Circle circle = new Gson().fromJson(str,Circle.class);
            circles.add(circle);
            circleAdapter.notifyDataSetChanged();
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
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
            //设置获取焦点，防止多次弹出，实现点一次弹出一次，在点一次收起
            //设置PopUpWindow的焦点，设置为true之后，PopupWindow内容区域，才可以响应点击事件
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            //设置边缘点击收起
            popupWindow.setOutsideTouchable(true);
            //设置动画效果(必须放在showatLocation前面)
            popupWindow.setAnimationStyle(R.style.style_pop_animation);
            //设置popupwindow关闭监听器
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f);
                    publish.setTag(R.id.tag_first,"close");
                }
            });
        }
        popupWindow.showAsDropDown(publish);
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

    @Override
    public void onResume() {
        super.onResume();

    }
}

