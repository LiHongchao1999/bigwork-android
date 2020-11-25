package com.example.homeworkcorrect.entity;

import java.io.Serializable;

public class School implements Serializable {
    private int id;//培训机构id
    private String name;//培训机构名称
    private String address;//培训机构地址
    private String content;//培训内容
    private String photo;//照片
    private String phone;//电话
    private String commission;//推广佣金

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public School(String name, String address, String content, String photo, String phone) {
        this.name = name;
        this.address = address;
        this.content = content;
        this.photo = photo;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", content='" + content + '\'' +
                ", photo='" + photo + '\'' +
                ", phone='" + phone + '\'' +
                ", commission='" + commission + '\'' +
                '}';
    }
}
