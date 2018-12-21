package com.cn.travel.domain;

public class MessageEvent{
	
	private String message;
	private String iconImageId;
	
	public MessageEvent() {
		super();
		// TODO Auto-generated constructor stub
	}


	public MessageEvent(String message,String iconImageId){ 
		this.message=message; 
		this.iconImageId=iconImageId;
		} 
	
	
	public String getMessage() {
		return message;
		}
	
	
	public void setMessage(String message){ 
		this.message = message;
		}

	
	public String getIconImageId() {
		return iconImageId;
	}


	public void setIconImageId(String iconImageId) {
		this.iconImageId = iconImageId;
	} 
	
	
	
}



