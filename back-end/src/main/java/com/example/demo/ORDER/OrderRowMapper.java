package com.example.demo.ORDER;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OrderRowMapper implements RowMapper<Order>{

	@Override
    public Order mapRow(ResultSet rs,int i) throws SQLException {
		Order order = new Order();
	
		order.setOrder_id(rs.getString("order_id"));
		order.setCustomer_id(rs.getInt("customer_id"));
		order.setBook_id(rs.getString("book_id"));
		order.setNumber(rs.getInt("number"));
		order.setSingle_price(rs.getDouble("single_price"));
		order.setDiscount(rs.getDouble("discount"));
		order.setMoney_first(rs.getDouble("money_first"));
		order.setMoney_last(rs.getDouble("money_last"));
		order.setScore(rs.getDouble("score_use"));
		order.setIf_post(rs.getInt("if_post"));
		order.setIf_identify(rs.getInt("if_identify"));
		order.setIf_return(rs.getInt("if_return"));
		order.setTimes(rs.getInt("times"));
		order.setTime(rs.getString("time"));
		
        return order;
    }
}
