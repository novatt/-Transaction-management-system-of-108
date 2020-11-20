package com.example.demo.CUSTOMER;

public class Customer {
	
	private int id = -1;
	private String password;
	private String address;
	private double score_now = 0;
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public double getScore() {
		return score_now;
	}
	
	public void setScore(double score) {
		this.score_now = score;
	}
	
}
