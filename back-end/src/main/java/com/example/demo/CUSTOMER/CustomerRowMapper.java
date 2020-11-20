package com.example.demo.CUSTOMER;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomerRowMapper implements RowMapper<Customer>{

	@Override
    public Customer mapRow(ResultSet rs,int i) throws SQLException {
		Customer customer= new Customer();
		
		customer.setId(rs.getInt("id"));
		customer.setPassword(rs.getString("password"));
		customer.setScore(rs.getDouble("score_now"));
		customer.setAddress(rs.getString("address"));
        return customer;
    }
}
