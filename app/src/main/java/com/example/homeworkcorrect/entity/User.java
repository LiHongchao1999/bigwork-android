package com.example.homeworkcorrect.entity;


public class User {
    private int id;//用户id
    private String nickname;//用户昵称
    private String phoneNumber;//手机号码
    private String password;//密码
    private String image;//头像
    private String qqNumber;//QQ号码
    private String weChatNumber;//微信号码
    private String grade;//年级
    private String sex;//性别
    private int chat_id;//聊天id
    private String chat_token;//聊天token

    public User() {
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public String getChat_token() {
        return chat_token;
    }

    public void setChat_token(String chat_token) {
        this.chat_token = chat_token;
    }

    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getQqNumber() {
        return qqNumber;
    }
    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }
    public String getWeChatNumber() {
        return weChatNumber;
    }
    public void setWeChatNumber(String weChatNumber) {
        this.weChatNumber = weChatNumber;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", qqNumber='" + qqNumber + '\'' +
                ", weChatNumber='" + weChatNumber + '\'' +
                ", grade='" + grade + '\'' +
                ", sex='" + sex + '\'' +
                ", chat_id=" + chat_id +
                ", chat_token='" + chat_token + '\'' +
                '}';
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

}
