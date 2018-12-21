package com.cn.travel.activity.first.food;

public class CollectBean {
	private int id;
	
	private String username; //�û���
	private String image;//ͼƬ
	private String title;//����
	private String time;//ʱ��
	private String type;//�ղ�����
	public CollectBean(int id, String username, String image, String title,
			String time,String type) {
		
		this.id = id;
		this.username = username;
		this.image = image;
		this.title = title;
		this.time = time;
		this.type=type;
	}
	public CollectBean(){
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "CollectBean [id=" + id + ", username=" + username + ", image="
				+ image + ", title=" + title + ", time=" + time + "]";
	}
	
	

}
