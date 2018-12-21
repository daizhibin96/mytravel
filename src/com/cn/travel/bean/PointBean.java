package com.cn.travel.bean;

public class PointBean {

	private int id;
	private String name;
	private String image;
	private String address;
	private String title;
	private String message;
	private double latlng;
	private double longlng;
	private String price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public double getLatlng() {
		return latlng;
	}
	public void setLatlng(double latlng) {
		this.latlng = latlng;
	}
	public double getLonglng() {
		return longlng;
	}
	public void setLonglng(double longlng) {
		this.longlng = longlng;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public PointBean(){
		super();
	}
	
	public PointBean(int id,String name,String image,String address,String title,String message,String price,double latlng,double longlng){
		super();
		this.id=id;
		this.name=name;
		this.image=image;
		this.address=address;
		this.title=title;
		this.message=message;
		this.price=price;
		this.latlng=latlng;
		this.longlng=longlng;
	}
	
}
