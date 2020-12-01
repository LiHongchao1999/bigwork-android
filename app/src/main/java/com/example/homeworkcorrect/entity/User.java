package com.example.homeworkcorrect.entity;

import android.graphics.Bitmap;

public class User {
    private String nickName;//昵称
    private String sex;//性别
    private Bitmap img;//用户头像

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
