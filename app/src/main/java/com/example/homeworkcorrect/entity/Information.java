package com.example.homeworkcorrect.entity;

import android.graphics.Bitmap;

public class Information {
    private int userId;//用户id
    private String nickName; //用户昵称
    private String content;//发送的最新消息
    private String sendTime;//发送的最新时间
    private Bitmap userImg;//用户头像
    public Information() {
    }

    public Information(int userId, String nickName, String content, String sendTime, Bitmap userImg) {
        this.userId = userId;
        this.nickName = nickName;
        this.content = content;
        this.sendTime = sendTime;
        this.userImg = userImg;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
