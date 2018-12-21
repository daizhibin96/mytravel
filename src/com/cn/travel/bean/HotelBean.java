package com.cn.travel.bean;

public class HotelBean {

	private int id;
	private String name;
	private String image;
	private String address;
	private String title;
	private String message;
	private double latlng;
	private double longlng;
	private String oneprice;
	private String twoprice;
	
	
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

	public String getOneprice() {
		return oneprice;
	}
	public void setOneprice(String oneprice) {
		this.oneprice = oneprice;
	}
	public String getTwoprice() {
		return twoprice;
	}
	public void setTwoprice(String twoprice) {
		this.twoprice = twoprice;
	}
	public HotelBean(){
		super();
	}
	
	public HotelBean(int id,String name,String image,String address,String title,String message,double latlng,double longlng){
		
		super();
		this.id=id;
		this.name=name;
		this.image=image;
		this.address=address;
		this.title=title;
		this.message=message;
		this.latlng=latlng;
		this.longlng=longlng;
	}
	
}
