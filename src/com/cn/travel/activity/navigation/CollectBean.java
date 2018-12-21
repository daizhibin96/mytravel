package com.cn.travel.activity.navigation;

public class CollectBean {

	private int id;
	private String username;
	private String title;
	private String image;
	private String type;
	private String time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public CollectBean(){
		super();
	}
	
	public CollectBean(int id,String username,String title,String image,String type,String time){
		super();
		this.id=id;
		this.username=username;
		this.title=title;
		this.image=image;
		this.type=type;
		this.time=time;
	}
	
}
