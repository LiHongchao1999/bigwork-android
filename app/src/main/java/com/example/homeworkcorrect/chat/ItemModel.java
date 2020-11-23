package com.example.homeworkcorrect.chat;

import java.io.Serializable;


public class ItemModel implements Serializable {

    public static final int CHAT_A = 1001; //接收方
    public static final int CHAT_B = 1002;  //发送方
    public int type;
    public Object object;

    public ItemModel(int type, Object object) {
        this.type = type;
        this.object = object;
    }
}
