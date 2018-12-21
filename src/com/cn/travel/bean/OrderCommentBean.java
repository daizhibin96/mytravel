package com.cn.travel.bean;

public class OrderCommentBean {
	private int id;
	private String orderid;
	private String time;
	private String message;
	private String username;
	private String type;
	private String name;
	private String userimg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserimg() {
		return userimg;
	}
	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}
	public OrderCommentBean(){
		super();
	}
	public OrderCommentBean(int id,String orderid,String time,String message,String username,String type,String name,String userimg){
		super();
		this.id=id;
		this.orderid=orderid;
		this.time=time;
		this.message=message;
		this.username=username;
		this.type=type;
		this.name=name;
		this.userimg=userimg;
	}
}
