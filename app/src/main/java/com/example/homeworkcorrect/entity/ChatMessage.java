package com.example.homeworkcorrect.entity;

public class ChatMessage {
	private int id;
	private int sender_id;//发送者id
	private int recipient_id;//接收者id
	private String content;//消息内容
	private String send_time;//发送时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSender_id() {
		return sender_id;
	}
	public void setSender_id(int sender_id) {
		this.sender_id = sender_id;
	}
	public int getRecipient_id() {
		return recipient_id;
	}
	public void setRecipient_id(int recipient_id) {
		this.recipient_id = recipient_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	@Override
	public String toString() {
		return "ChatMessage [id=" + id + ", sender_id=" + sender_id + ", recipient_id=" + recipient_id + ", content="
				+ content + ", send_time=" + send_time + "]";
	}
	public ChatMessage(int sender_id, int recipient_id, String content, String send_time) {
		super();
		this.sender_id = sender_id;
		this.recipient_id = recipient_id;
		this.content = content;
		this.send_time = send_time;
	}
	public ChatMessage() {
		super();
	}
	
	
	
	

}
