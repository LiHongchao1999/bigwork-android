package com.example.homeworkcorrect.entity;

public class Like {
	private int userid;
	private int circleid;
	private int id;
	public Like(int id,int userid,int circleid) {
		this.userid=userid;
		this.circleid=circleid;
		this.id=id;
	}
	public Like(){}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getCircleid() {
		return circleid;
	}
	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}
	
}
