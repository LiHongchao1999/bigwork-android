package com.example.homeworkcorrect.entity;

public class CircleComment {
    private int id;//评论的id
    private String commentName;//评论用户名称
    private String content;//评论内容
    private String image;//用户头像

    public CircleComment(int id, String commentName, String content, String image) {
        super();
        this.id = id;
        this.commentName = commentName;
        this.content = content;
        this.setImage(image);
    }


    public CircleComment(int id, String commentName, String content) {
        super();
        this.id = id;
        this.commentName = commentName;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }
}
