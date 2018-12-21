package com.cn.travel.bean;





public class NoteBean {
	private String name;
	private String userId;
	private String  content;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NoteBean [name=" + name + ", userId=" + userId + ", content="
				+ content + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public NoteBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
