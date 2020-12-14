package com.example.homeworkcorrect;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String appKey = "cpj2xarlcm6on";
        RongIM.init(this, appKey);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
