package com.example.demo.REVIEW;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.BOOKS.Books;
import com.example.demo.BOOKS.Booksdao;
import com.example.demo.ORDER.Order;
import com.example.demo.ORDER.Orderdao;

@Service
public class Reviewservice {

	@Autowired
	private Reviewdao reviewdao;
	
	@Autowired
	private Booksdao booksdao;
	
	@Autowired
	private Orderdao orderdao;
	
	public Review get_review_byorder_id(String order_id) {
		return reviewdao.queryreview_byorder_id(order_id);
	}
	
	public List<Review> get_review(String book_id) throws Exception {  	
		return reviewdao.queryreview(book_id);	
	}
	
	public Review addreview(int customer_id , Review review) {
		Order order = orderdao.queryorderbyorder_id(review.getOrder_id());
		Review review2 = new Review();
		if(order == null)
		{
			review2.setBook_id("-3");
			return review2;
		}
		Books book = booksdao.queryBookById(order.getBook_id());
		Review review1 = reviewdao.queryreview_byorder_id(review.getOrder_id());		
		if(order.getCustomer_id() != customer_id)//账户中无此订单
		{
			review2.setBook_id("-1");
			return review2;
		}
		else if(review1 != null)//已经评论过了
		{
			review2.setBook_id("-2");
			return review2;
		}
		else if(review.getReview_star() == 1895)//没给出星级
		{
			review2.setBook_id("-3");
			return review2;
		}
		else
		{
			review.setCustomer_id(customer_id);
			review.setBook_id(book.getId());
			review.setPublisher(book.getPublisher());
			return reviewdao.addreview(review);
		}
	}
}
