package com.example.demo.SCORE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.SHOPCAR.ShopcarRowMapper;

@Service
public class Scoredao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<Score> queryorder(int customer_id) {
        String sql = "SELECT * FROM score WHERE customer_id=?";
        List<Score> orderList = jdbcTemplate.query(sql , new ScoreRowMapper() , customer_id);
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
	
}
