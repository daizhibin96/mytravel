package com.cn.travel.bean;

public class HotelOrderBean {

	private int id;
	private String userid;
	private String roomtype;
	private int roomnum;
	private String intime;
	private String outtime;
	private String phone;
	private String price;
	private String pay;
	private String time;
	private String hotel;
	private String state;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getOuttime() {
		return outtime;
	}
	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	public String getRoomtype() {
		return roomtype;
	}
	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}
	public int getRoomnum() {
		return roomnum;
	}
	public void setRoomnum(int roomnum) {
		this.roomnum = roomnum;
	}
	public String getHotel() {
		return hotel;
	}
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}
	public HotelOrderBean(){
		super();
	}
	
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public HotelOrderBean(String userid,String room,int num,String intime,String outtime,String phone,String price,String pay,String time,String hotel,String state){
		super();
		this.userid=userid;
		this.roomtype=room;
		this.roomnum=num;
		this.intime=intime;
		this.outtime=outtime;
		this.phone=phone;
		this.price=price;
		this.pay=pay;
		this.time=time;
		this.hotel=hotel;
		this.state=state;
	}
	
	
}
