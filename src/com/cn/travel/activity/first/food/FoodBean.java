package com.cn.travel.activity.first.food;

import android.R.integer;

public class FoodBean {
	private int id;//美食id
	private String title;//美食标题
	private String image;//美食封面
	private String message;//�?��
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public FoodBean() {
		super();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public FoodBean(String name, String activityFood1) {
		
		this.title = name;
		this.image = activityFood1;
	}
	public String getName() {
		return title;
	}
	public void setName(String name) {
		this.title = name;
	}
	@Override
	public String toString() {
		return "Food [id=" + id + ", title=" + title + ", image=" + image
				+ ", message=" + message + "]";
	}
	public String getImageID() {
		return image;
	}
	public void setImageID(String imageID) {
		this.image = imageID;
	}
	
	

}
