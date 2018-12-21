package com.cn.travel.bean;

public class PointOrderBean {
	private int id;
	private String userid;
	private int num;
	private String phone;
	private String price;
	private String pay;
	private String time;
	private String tourist;
	private String state;
	private String date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
	public String getTourist() {
		return tourist;
	}
	public void setTourist(String tourist) {
		this.tourist = tourist;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public PointOrderBean(){
		super();
	}
	
	public PointOrderBean(int id,String userid,int num,String phone,String price,
			String pay,String time,String tourist,String state,String date){
		super();
		this.id=id;
		this.userid=userid;
		this.num=num;
		this.phone=phone;
		this.price=price;
		this.pay=pay;
		this.time=time;
		this.tourist=tourist;
		this.state=state;
		this.date=date;
	}
	
}
