package com.example.demo;

public class info {
	private String manager_id;
	private String publisher_id;
	private String book_id;
	private String body;
	private String time;
	
	public info(String manager_id , String publisher_id , String book_id , String body , String time)
	{
		this.body = body;
		this.book_id = book_id;
		this.manager_id = manager_id;
		this.publisher_id = publisher_id;
		this.time = time;
	}
	
	public String getManager_id() {
		return manager_id;
	}
	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}
	public String getPublisher_id() {
		return publisher_id;
	}
	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
