package com.example.demo.BOOKS;

import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksRowMapper implements RowMapper<Books>{

	@Override
    public Books mapRow(ResultSet rs,int i) throws SQLException {
        Books book= new Books();
        book.setId(rs.getString("id"));
        book.setName(rs.getString("name"));
        book.setPrice(rs.getDouble("price"));
        book.setReview_star(rs.getDouble("star"));
        book.setReview_number(rs.getDouble("review_number"));
        book.setNumber(rs.getInt("number"));
        book.setDiscount(rs.getDouble("discount"));
        book.setType(rs.getString("type"));
        book.setPublisher(rs.getString("publisher"));
        return book;
    }
}
