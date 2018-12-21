package com.cn.travel.activity.first.food;

import java.sql.Date;

public class Comment1 {
	int id;
	int foodid;//美食Id
	String floor;
	String username;//用户id
	 String message;//评论信息
	 String time;
	 public Comment1(){
	 
	 }
	public String getTime() {
		return time;
	}
	public void setTime( String time) {
		this.time = time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFoodid() {
		return foodid;
	}
	//构�?函数
	public Comment1(String username, String message, String time) {
		super();
		this.username = username;
		this.message = message;
		this.time=time;
	}
	public void setFoodid(int foodid) {
		this.foodid = foodid;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
