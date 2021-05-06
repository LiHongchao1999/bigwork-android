package com.example.homeworkcorrect.entity;

public class Teacher {
    private int teacherId;
    private String nickname;//用户昵称
    private String image;
    private String pNumber;//教师手机号
    private String password;//教师密码
    private String fSchool;//来自于哪个学校
    private int rank;//认证等级
    private String qqNumber;//qq账号
    private String weNumber;//微信账号
    private String chat_id;//聊天id
    private String chat_token;//聊天token
    private int clapping_money;//拍拍币数量

    public int getClapping_money() {
        return clapping_money;
    }

    public void setClapping_money(int clapping_money) {
        this.clapping_money = clapping_money;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getChat_id() {
        return chat_id;
    }
    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }
    public String getChat_token() {
        return chat_token;
    }
    public void setChat_token(String chat_token) {
        this.chat_token = chat_token;
    }
    public int getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getpNumber() {
        return pNumber;
    }
    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getfSchool() {
        return fSchool;
    }
    public void setfSchool(String fSchool) {
        this.fSchool = fSchool;
    }
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public String getQqNumber() {
        return qqNumber;
    }
    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }
    public String getWeNumber() {
        return weNumber;
    }
    public void setWeNumber(String weNumber) {
        this.weNumber = weNumber;
    }

    public Teacher() {
    }

    public Teacher(int teacherId, String nickname, String image, String pNumber, String password, String fSchool, int rank, String qqNumber, String weNumber, String chat_id, String chat_token, int clapping_money) {
        this.teacherId = teacherId;
        this.nickname = nickname;
        this.image = image;
        this.pNumber = pNumber;
        this.password = password;
        this.fSchool = fSchool;
        this.rank = rank;
        this.qqNumber = qqNumber;
        this.weNumber = weNumber;
        this.chat_id = chat_id;
        this.chat_token = chat_token;
        this.clapping_money = clapping_money;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", nickname='" + nickname + '\'' +
                ", image='" + image + '\'' +
                ", pNumber='" + pNumber + '\'' +
                ", password='" + password + '\'' +
                ", fSchool='" + fSchool + '\'' +
                ", rank=" + rank +
                ", qqNumber='" + qqNumber + '\'' +
                ", weNumber='" + weNumber + '\'' +
                ", chat_id='" + chat_id + '\'' +
                ", chat_token='" + chat_token + '\'' +
                ", clapping_money=" + clapping_money +
                '}';
    }
}
