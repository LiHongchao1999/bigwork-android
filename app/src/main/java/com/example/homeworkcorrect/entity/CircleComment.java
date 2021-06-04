package com.example.homeworkcorrect.entity;

public class CircleComment {
    private int id;//评论的id
    private int circle_id;//朋友圈id
    private int comment_id;//用户id
    private String commentName;//评论用户名称
    private String content;//评论内容
    private String image;//用户头像

    public CircleComment() {
    }

    public CircleComment(int id, int circle_id, int comment_id, String commentName, String content, String image) {
        this.id = id;
        this.circle_id = circle_id;
        this.comment_id = comment_id;
        this.commentName = commentName;
        this.content = content;
        this.image = image;
    }

    public int getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(int circle_id) {
        this.circle_id = circle_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
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

    @Override
    public String toString() {
        return "CircleComment{" +
                "id=" + id +
                ", circle_id=" + circle_id +
                ", comment_id=" + comment_id +
                ", commentName='" + commentName + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
