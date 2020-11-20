package com.example.demo.ORDER;

import java.sql.Date;

public class Order {

	private String order_id = "0";
	private int customer_id = 0;
	private String book_id = "0";
	private int number;
	private double single_price;
	private double discount;
	private double money_first;
	private double money_last;
	private double score = 0;
	private int if_post;
	private int if_identify;
	private int if_return;
	private int times;
	private String time ;

	
	
	public int getTimes() {
		return times;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {//此处参数无用
		this.time = time;
	}
	public double getSingle_price() {
		return single_price;
	}
	public void setSingle_price(double single_price) {
		this.single_price = single_price;
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getMoney_first() {
		return money_first;
	}
	public void setMoney_first(double money_first) {
		this.money_first = money_first;
	}
	public double getMoney_last() {
		return money_last;
	}
	public void setMoney_last(double money_last) {
		this.money_last = money_last;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getIf_post() {
		return if_post;
	}
	public void setIf_post(int if_post) {
		this.if_post = if_post;
	}
	public int getIf_identify() {
		return if_identify;
	}
	public void setIf_identify(int if_identify) {
		this.if_identify = if_identify;
	}
	public int getIf_return() {
		return if_return;
	}
	public void setIf_return(int if_return) {
		this.if_return = if_return;
	}
	
	@Override
	public String toString() {
		return "\n交易时间：" + this.time + 
				"订单号：" + this.order_id + 
				" 图书ID：" + this.book_id + 
				" 图书单价：" + this.single_price + 
				" 购买数量：" + this.number + 
				" 总价：" + this.money_first + 
				" 折扣后总价：" + this.money_last + 
				" 折扣为：" + this.discount + "\n";
	}
}
