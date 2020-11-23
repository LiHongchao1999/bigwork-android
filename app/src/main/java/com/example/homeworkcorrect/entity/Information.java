package com.example.homeworkcorrect.entity;

import android.graphics.Bitmap;

public class Information {
    private String nickName; //用户昵称
    private String content;//发送的消息
    private String sendTime;//发送的时间
    private Bitmap userImg;//用户头像

    public Information() {
    }

    public Information(String nickName, String content, String sendTime, Bitmap userImg) {
        this.nickName = nickName;
        this.content = content;
        this.sendTime = sendTime;
        this.userImg = userImg;
    }

    public Bitmap getUserImg() {
        return userImg;
    }

    public void setUserImg(Bitmap userImg) {
        this.userImg = userImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
