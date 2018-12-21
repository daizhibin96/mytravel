package com.cn.travel.activity.find;

public class forumbean {
	String userid;//用户id
	String image;//封面图片
	String title;//帖子标题
	String userimage;//用户头像
	String username;//用户名
	String time;//发表时间
	String inputStr;//文本文字
	String imagePath;//帖子图片路径
	
	
	public forumbean()
	{
		super();
	}
	public forumbean(String userid, String image, String title,
			String userimage, String username, String time,String inputStr,String imagePath) {
		super();
		this.userid = userid;
		this.image = image;
		this.title = title;
		this.userimage = userimage;
		this.username = username;
		this.time = time;
		this.inputStr=inputStr;
		this.imagePath=imagePath;
	}
	public String getInputStr() {
		return inputStr;
	}
	public void setInputStr(String inputStr) {
		this.inputStr = inputStr;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getUserid() {
		return userid;
	}
	@Override
	public String toString() {
		return "forumbean [userid=" + userid + ", image=" + image + ", title="
				+ title + ", userimage=" + userimage + ", username=" + username
				+ ", time=" + time + "]";
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	public String getUserimage() {
		return userimage;
	}
	public void setUserimage(String userimage) {
		this.userimage = userimage;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	

}
