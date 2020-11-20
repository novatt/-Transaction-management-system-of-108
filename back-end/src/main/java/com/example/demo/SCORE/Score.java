package com.example.demo.SCORE;

public class Score {

	private int customer_id;
	private String order_id;
	private double scores;
	private String time;
	
	public double getScores() {
		return scores;
	}
	public void setScores(double scores) {
		this.scores = scores;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
