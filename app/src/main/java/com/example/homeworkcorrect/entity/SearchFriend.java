package com.example.homeworkcorrect.entity;

import android.graphics.Bitmap;

public class SearchFriend {//搜索好友的结果类
    private Bitmap headImg;//头像
    private String nickName;//昵称
    private String sex;//性别

    public SearchFriend() {
    }

    public SearchFriend(Bitmap headImg, String nickName, String sex) {
        this.headImg = headImg;
        this.nickName = nickName;
        this.sex = sex;
    }

    public Bitmap getHeadImg() {
        return headImg;
    }

    public void setHeadImg(Bitmap headImg) {
        this.headImg = headImg;
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
