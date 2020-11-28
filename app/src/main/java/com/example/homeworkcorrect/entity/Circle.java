package com.example.homeworkcorrect.entity;

import android.graphics.Bitmap;

import java.util.List;

public class Circle {
    private Bitmap userImg;
    private String userName;
    private String time;
    private String content;
    private List<Bitmap> sendImg;
    private int likeSize;
    private int forwardSize;
    private int commentSize;

    public Circle() {
    }

    public Circle(Bitmap userImg, String userName, String time, String content, List<Bitmap> sendImg, int likeSize, int forwardSize, int commentSize) {
        this.userImg = userImg;
        this.userName = userName;
        this.time = time;
        this.content = content;
        this.sendImg = sendImg;
        this.likeSize = likeSize;
        this.forwardSize = forwardSize;
        this.commentSize = commentSize;
    }

    public Bitmap getUserImg() {
        return userImg;
    }

    public void setUserImg(Bitmap userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Bitmap> getSendImg() {
        return sendImg;
    }

    public void setSendImg(List<Bitmap> sendImg) {
        this.sendImg = sendImg;
    }

    public int getLikeSize() {
        return likeSize;
    }

    public void setLikeSize(int likeSize) {
        this.likeSize = likeSize;
    }

    public int getForwardSize() {
        return forwardSize;
    }

    public void setForwardSize(int forwardSize) {
        this.forwardSize = forwardSize;
    }

    public int getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(int commentSize) {
        this.commentSize = commentSize;
    }
}
