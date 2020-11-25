package com.example.homeworkcorrect.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.homeworkcorrect.R;
import com.example.homeworkcorrect.adapter.HomeworkAdapter;
import com.example.homeworkcorrect.entity.Homework;

import java.util.ArrayList;
import java.util.List;

public class HomeworkFragment extends Fragment {

    private ListView lvHomework;
    private List<Homework> homeworks = new ArrayList<>();
    private HomeworkAdapter homeworkAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homework_fragment, container,false);
        lvHomework = view.findViewById(R.id.lv_homework);
        Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.homework1);
        Homework homework1 = new Homework("数学","1999/10/04","批改完成",img);
        homeworks.add(homework1);
        homeworkAdapter = new HomeworkAdapter(getContext(),homeworks, R.layout.homework_list_item);
        lvHomework.setAdapter(homeworkAdapter);
        return view;
    }
}
