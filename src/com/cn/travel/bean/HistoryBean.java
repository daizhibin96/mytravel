package com.cn.travel.bean;

public class HistoryBean {

	private int nid;
	private String userid;
	private String name;
	private String image;
	private String time;
	private String type;
	public int getId() {
		return nid;
	}
	public void setId(int id) {
		this.nid = id;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public HistoryBean(){
		super();
	}
	public HistoryBean(int nid,String userid,String name,String image,String time,String type){
		super();
		this.nid=nid;
		this.userid=userid;
		this.name=name;
		this.image=image;
		this.time=time;
		this.type=type;
	}
}
