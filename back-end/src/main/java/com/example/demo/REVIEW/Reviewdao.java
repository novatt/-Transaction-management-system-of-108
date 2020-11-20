package com.example.demo.REVIEW;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class Reviewdao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public Review queryreview_byorder_id(String order_id) {
		String sql = "SELECT * FROM review WHERE order_id=?";
        Review review = new Review();
        try {
	        jdbcTemplate.queryForObject(sql , new ReviewRowMapper() , order_id);
	        System.out.print("成功查询");
	        return review;
        }catch(Exception e)
		{
			return null;
		}
	}
	
	public List<Review> queryreview(String book_id) {
        String sql = "SELECT * FROM review WHERE book_id=?";
        List<Review> reviewList = jdbcTemplate.query(sql , new ReviewRowMapper() , book_id);
        System.out.print("成功查询所有订单");
        return reviewList;
    }
	
	public Review addreview(Review review) {
		String sql = "INSERT INTO review (order_id , customer_id , book_id , publisher , review_star , body) VALUES (? , ? , ? , ? , ? , ?)";
        jdbcTemplate.update(sql,  review.getOrder_id() , review.getCustomer_id() , review.getBook_id() , review.getPublisher() , review.getReview_star() , review.getBody());
        System.out.print("成功更新");
        return review;
	}
}
