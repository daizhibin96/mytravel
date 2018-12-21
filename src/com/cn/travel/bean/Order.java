package com.cn.travel.bean;

public class Order {
	private int id;
	private String imgePic;//商家头像
	private String ordername;//景点名或者酒店名称
	private String ordertime;//下单时间
	private String price;//价格
	private int ordertype;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the imgePic
	 */
	public String getImgePic() {
		return imgePic;
	}
	/**
	 * @param imgePic the imgePic to set
	 */
	public void setImgePic(String imgePic) {
		this.imgePic = imgePic;
	}
	/**
	 * @return the ordername
	 */
	public String getOrdername() {
		return ordername;
	}
	/**
	 * @param ordername the ordername to set
	 */
	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}
	/**
	 * @return the ordertime
	 */
	public String getOrdertime() {
		return ordertime;
	}
	/**
	 * @param ordertime the ordertime to set
	 */
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	/**
	 * @return the ordertype
	 */
	public int getOrdertype() {
		return ordertype;
	}
	/**
	 * @param ordertype the ordertype to set
	 */
	public void setOrdertype(int ordertype) {
		this.ordertype = ordertype;
	}
	public Order(int id,String imagePic, String ordername, String ordertime, String price,int ordertype) {
		super();
		// TODO Auto-generated constructor stub
		this.id=id;
		this.imgePic=imagePic;
		this.ordername=ordername;
		this.ordertime=ordertime;
		this.price=price;
		this.ordertype=ordertype;
	}
	
}
