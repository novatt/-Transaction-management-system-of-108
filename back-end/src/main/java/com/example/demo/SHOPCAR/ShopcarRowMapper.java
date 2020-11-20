package com.example.demo.SHOPCAR;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ShopcarRowMapper implements RowMapper<Shopcar>{

	@Override
    public Shopcar mapRow(ResultSet rs,int i) throws SQLException {
		Shopcar shopcar= new Shopcar();

		shopcar.setCustomer_id(rs.getInt("customer_id"));
		shopcar.setBook_id(rs.getString("book_id"));
		shopcar.setNumber(rs.getInt("number"));
		shopcar.setSingle_price(rs.getDouble("single_price"));
		shopcar.setDiscount(rs.getDouble("discount"));
        
        return shopcar;
    }
}
