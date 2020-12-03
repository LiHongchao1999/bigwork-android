package com.example.homeworkcorrect.entity;
//错题本

import java.util.List;

public class WrongQuestion {
	private int id;//id
	private int wrong_id;//错题id
	private int user_id;//用户id
	private String question_Type;//错题类型
	private String update_time;//上传时间
	private List<String> homework_image;//老师批改图片
	private List<String> result_image;//用户上传图片
	private String result_text_teacher;//老师注释
	private String result_text_student;//学生注释
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWrong_id() {
		return wrong_id;
	}
	public void setWrong_id(int wrong_id) {
		this.wrong_id = wrong_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public List<String> getHomework_image() {
		return homework_image;
	}
	public void setHomework_image(List<String> homework_image) {
		this.homework_image = homework_image;
	}
	public List<String> getResult_image() {
		return result_image;
	}
	public void setResult_image(List<String> result_image) {
		this.result_image = result_image;
	}
	
	public String getQuestion_Type() {
		return question_Type;
	}
	public void setQuestion_Type(String question_Type) {
		this.question_Type = question_Type;
	}
	public String getResult_text_teacher() {
		return result_text_teacher;
	}
	public void setResult_text_teacher(String result_text_teacher) {
		this.result_text_teacher = result_text_teacher;
	}
	public String getResult_text_student() {
		return result_text_student;
	}
	public void setResult_text_student(String result_text_student) {
		this.result_text_student = result_text_student;
	}
	@Override
	public String toString() {
		return "WrongQuestion [id=" + id + ", wrong_id=" + wrong_id + ", user_id=" + user_id + ", question_Type="
				+ question_Type + ", update_time=" + update_time + ", homework_image=" + homework_image
				+ ", result_image=" + result_image + ", result_text_teacher=" + result_text_teacher
				+ ", result_text_student=" + result_text_student + "]";
	}
	
	
	
	
	

}
