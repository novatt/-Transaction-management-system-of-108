package com.example.demo.REVIEW;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ReviewRowMapper implements RowMapper<Review>{
	
	@Override
    public Review mapRow(ResultSet rs,int i) throws SQLException {
		Review review= new Review();
		review.setOrder_id(rs.getString("order_id"));
		review.setCustomer_id(rs.getInt("customer_id"));
		review.setBook_id(rs.getString("book_id"));
		review.setPublisher(rs.getString("publisher"));
		review.setReview_star(rs.getInt("review_star"));
		review.setBody(rs.getString("body"));
        return review;
    }

}
