package com.example.demo.SCORE;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.SHOPCAR.Shopcar;

public class ScoreRowMapper implements RowMapper<Score>{

	@Override
    public Score mapRow(ResultSet rs,int i) throws SQLException {
		Score score = new Score();
		
		score.setCustomer_id(rs.getInt("customer_id"));
		score.setOrder_id(rs.getString("order_id"));
		score.setScores(rs.getDouble("score"));
		score.setTime(rs.getString("time"));
		
		return score;
	}
}
