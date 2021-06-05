package com.example.homeworkcorrect.entity;

public class LikeInfo {
	private int userid;
	private int circleid;
	private int id;
	public LikeInfo(int id, int userid, int circleid) {
		this.userid=userid;
		this.circleid=circleid;
		this.id=id;
	}
	public LikeInfo(){}
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
