package com.example.homeworkcorrect.entity;

import android.graphics.Bitmap;

import java.util.List;

public class Homework {
    private int id;//作业id
    private String submitTime;//作业发布时间
    private String deadline;//作业截止时间
    private String homeworkType;//作业类型
    private String tag;//作业状态
    private List<String> homework_image;//作业图片
    private int teacher_id;//教师id
    private List<String> result_image;//结果图片
    private String result_text;//结果文字
    private double money;//佣金

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getHomeworkType() {
        return homeworkType;
    }

    public void setHomeworkType(String homeworkType) {
        this.homeworkType = homeworkType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getHomework_image() {
        return homework_image;
    }

    public void setHomework_image(List<String> homework_image) {
        this.homework_image = homework_image;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public List<String> getResult_image() {
        return result_image;
    }

    public void setResult_image(List<String> result_image) {
        this.result_image = result_image;
    }

    public String getResult_text() {
        return result_text;
    }

    public void setResult_text(String result_text) {
        this.result_text = result_text;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Homework() {

    }

    public Homework(String submitTime, String deadline, String homeworkType, List<String> homework_image, List<String> result_image, double money) {
        this.submitTime = submitTime;
        this.deadline = deadline;
        this.homeworkType = homeworkType;
        this.homework_image = homework_image;
        this.result_image = result_image;
        this.money = money;
    }
}
