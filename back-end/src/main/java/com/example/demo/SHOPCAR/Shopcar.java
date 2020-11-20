package com.example.demo.SHOPCAR;

public class Shopcar {

	
	private int customer_id;
	private String book_id;
	private int number = 1;
	private double single_price;
	private double discount;
	
	
	
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getSingle_price() {
		return single_price;
	}
	public void setSingle_price(double single_price) {
		this.single_price = single_price;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	
}
