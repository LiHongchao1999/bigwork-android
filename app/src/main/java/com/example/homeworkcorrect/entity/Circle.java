package com.example.homeworkcorrect.entity;

import android.graphics.Bitmap;

import java.util.List;

public class Circle {
    private int id;
    private int userId;//用户id
    private String chatId;//chatId
    private String userImg; //用户头像
    private String userName;  //用户名
    private String time;  //发表的时间
    private String content;  //发表的内容
    private List<String> sendImg;  //发表的图片
    private int likeSize;  //点赞数
    private int forwardSize;  //转发数
    private int commentSize;  //评论数

    public Circle() {
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
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

    public List<String> getSendImg() {
        return sendImg;
    }

    public void setSendImg(List<String> sendImg) {
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


    @Override
    public String toString() {
        return "Circle{" +
                "id=" + id +
                ", userId=" + userId +
                ", chatId='" + chatId + '\'' +
                ", userImg='" + userImg + '\'' +
                ", userName='" + userName + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", sendImg=" + sendImg +
                ", likeSize=" + likeSize +
                ", forwardSize=" + forwardSize +
                ", commentSize=" + commentSize +
                '}';
    }
}
