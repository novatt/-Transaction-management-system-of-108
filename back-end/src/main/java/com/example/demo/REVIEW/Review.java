package com.example.demo.REVIEW;

public class Review {

	private String order_id;
	private int customer_id;
	private String book_id;
	private String publisher;
	private int review_star = 1895;
	private String body;
	
	public int getReview_star() {
		return review_star;
	}
	public void setReview_star(int review_star) {
		this.review_star = review_star;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public String toString() {
		String result;
		result = "\n用户ID：" + this.customer_id + 
				"\n图书ID：" +  this.book_id + 
				"\n评分：" +  this.review_star  + 
				"\n评论内容："+ this.body + "\n";
		return result;
		
	}
}
