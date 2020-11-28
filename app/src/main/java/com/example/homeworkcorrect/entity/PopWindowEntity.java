package com.example.homeworkcorrect.entity;


public class PopWindowEntity {
    private int img;
    private String section;

    public PopWindowEntity() {
    }

    public PopWindowEntity(int img, String section) {
        this.img = img;
        this.section = section;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
