package com.example.homeworkcorrect.entity;

import android.graphics.Bitmap;

public class Homework {
    private String homeworkType;
    private String submitTime;
    private String state;
    private Bitmap homeworkImg;

    public Homework(String homeworkType, String submitTime, String state, Bitmap homeworkImg) {
        this.homeworkType = homeworkType;
        this.submitTime = submitTime;
        this.state = state;
        this.homeworkImg = homeworkImg;
    }

    public Homework() {
    }

    public String getHomeworkType() {
        return homeworkType;
    }

    public void setHomeworkType(String homeworkType) {
        this.homeworkType = homeworkType;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Bitmap getHomeworkImg() {
        return homeworkImg;
    }

    public void setHomeworkImg(Bitmap homeworkImg) {
        this.homeworkImg = homeworkImg;
    }
}
