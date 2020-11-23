package com.example.homeworkcorrect.chat;

import java.io.Serializable;

public class ChatModel implements Serializable{
    private String icon=""; //头像
    private String content=""; //内容
    private String type="";  //是发送方还是接收方

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
