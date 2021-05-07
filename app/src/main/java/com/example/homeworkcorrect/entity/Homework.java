package com.example.homeworkcorrect.entity;

import android.graphics.Bitmap;

import java.util.List;

public class Homework {
    private int id;//作业id
    private int user_id;//user_id
    private String submitTime;//作业发布时间
    private String deadline;//作业截止时间
    private String homeworkType;//作业类型
    private String tag;//作业状态
    private List<String> homework_image;//作业图片
    private int teacher_id;//教师id
    private List<String> result_image;//结果图片
    private List<String> result_image_teacher;//老师结果图片
    private String result_text;//结果文字
    private double money;//佣金
    private int grade;//作业评分
    private String scored;//判断是否评分
    private String chatId;//聊天id
    private String homework_image1;
    private String result_image1;
    private String result_image_teacher1;

    @Override
    public String toString() {
        return "Homework{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", submitTime='" + submitTime + '\'' +
                ", deadline='" + deadline + '\'' +
                ", homeworkType='" + homeworkType + '\'' +
                ", tag='" + tag + '\'' +
                ", homework_image=" + homework_image +
                ", teacher_id=" + teacher_id +
                ", result_image=" + result_image +
                ", result_image_teacher=" + result_image_teacher +
                ", result_text='" + result_text + '\'' +
                ", money=" + money +
                ", grade=" + grade +
                ", scored='" + scored + '\'' +
                ", chatId='" + chatId + '\'' +
                ", homework_image1='" + homework_image1 + '\'' +
                ", result_image1='" + result_image1 + '\'' +
                ", result_image_teacher1='" + result_image_teacher1 + '\'' +
                '}';
    }

    public Homework() {
    }

    public Homework(int id, int user_id, String submitTime, String deadline, String homeworkType, String tag, List<String> homework_image, int teacher_id, List<String> result_image, List<String> result_image_teacher, String result_text, double money, int grade, String scored, String chatId) {
        this.id = id;
        this.user_id = user_id;
        this.submitTime = submitTime;
        this.deadline = deadline;
        this.homeworkType = homeworkType;
        this.tag = tag;
        this.homework_image = homework_image;
        this.teacher_id = teacher_id;
        this.result_image = result_image;
        this.result_image_teacher = result_image_teacher;
        this.result_text = result_text;
        this.money = money;
        this.grade = grade;
        this.scored = scored;
        this.chatId = chatId;
    }

    public Homework(int id, int user_id, String submitTime, String deadline, String homeworkType, String tag, List<String> homework_image, int teacher_id, List<String> result_image, List<String> result_image_teacher, String result_text, double money, int grade, String scored, String chatId, String homework_image1, String result_image1, String result_image_teacher1) {
        this.id = id;
        this.user_id = user_id;
        this.submitTime = submitTime;
        this.deadline = deadline;
        this.homeworkType = homeworkType;
        this.tag = tag;
        this.homework_image = homework_image;
        this.teacher_id = teacher_id;
        this.result_image = result_image;
        this.result_image_teacher = result_image_teacher;
        this.result_text = result_text;
        this.money = money;
        this.grade = grade;
        this.scored = scored;
        this.chatId = chatId;
        this.homework_image1 = homework_image1;
        this.result_image1 = result_image1;
        this.result_image_teacher1 = result_image_teacher1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public List<String> getResult_image_teacher() {
        return result_image_teacher;
    }

    public void setResult_image_teacher(List<String> result_image_teacher) {
        this.result_image_teacher = result_image_teacher;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getScored() {
        return scored;
    }

    public void setScored(String scored) {
        this.scored = scored;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getHomework_image1() {
        return homework_image1;
    }

    public void setHomework_image1(String homework_image1) {
        this.homework_image1 = homework_image1;
    }

    public String getResult_image1() {
        return result_image1;
    }

    public void setResult_image1(String result_image1) {
        this.result_image1 = result_image1;
    }

    public String getResult_image_teacher1() {
        return result_image_teacher1;
    }

    public void setResult_image_teacher1(String result_image_teacher1) {
        this.result_image_teacher1 = result_image_teacher1;
    }
}
