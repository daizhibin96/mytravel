package com.cn.travel.bean;

public class Order {
	private int id;
	private String imgePic;//�̼�ͷ��
	private String ordername;//���������߾Ƶ�����
	private String ordertime;//�µ�ʱ��
	private String price;//�۸�
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
