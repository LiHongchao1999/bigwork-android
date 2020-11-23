package com.example.homeworkcorrect.chat;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkcorrect.R;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // item
    private ChatAdapter adapter;
    private EditText et;
    private TextView tvSend;
    private String content; //输入的内容
    private ImageView back;//返回
    private TextView nickName; //用户昵称
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getViews();
        //获取Intent
        //赋值
        recyclerView.setHasFixedSize(true);
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        //设置垂直布局
        manager.setOrientation(RecyclerView.VERTICAL);
        //设置adapter
        adapter = new ChatAdapter();
        recyclerView.setAdapter(adapter);
        //清楚缓存
        adapter.replaceAll(TestData.getTestAdData());
        initData();
    }
    /*
    * 获取控件引用
    * */
    private void getViews() {
        recyclerView = findViewById(R.id.recylerView);
        et = findViewById(R.id.et);
        tvSend = findViewById(R.id.tvSend);
        back = findViewById(R.id.img_back);
        nickName = findViewById(R.id.nickname);
    }

    private void initData() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //获取输入的值
                content = s.toString().trim();
            }
        });
        tvSend.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                ArrayList<ItemModel> data = new ArrayList<>();
                ChatModel model = new ChatModel();
                model.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg");
                model.setContent(content);
                data.add(new ItemModel(ItemModel.CHAT_B, model));
                adapter.addAll(data);
                et.setText("");
                hideKeyBorad(et);
            }
        });

    }

    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

}
