package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class infodao {
	
	    @Autowired
	    private JdbcTemplate jdbcTemplate;
   
	    public List<info> queryinfosByPublisher(String publisher_id) {
	        String sql = "SELECT * FROM info where publisher_id = ?";
	        List<info> infos = jdbcTemplate.query(sql , new infoRowMapper() , publisher_id);
	        System.out.println("成功查询所有记录");
	        return infos;
	    }
	    
	    public List<info> queryAllinfos() {
	        String sql = "SELECT * FROM info";
	        List<info> infos = jdbcTemplate.query(sql , new infoRowMapper());
	        System.out.println("成功查询所有记录");
	        return infos;
	    }

	    public void insertInfo(info info) {
	        String sql = "INSERT INTO info (manager_id,publisher_id,book_id,body,time) VALUES(?,?,?,?,?)";
	        jdbcTemplate.update(sql,info.getManager_id(),info.getPublisher_id(),info.getBook_id(),info.getBody(),info.getTime());
	    }

	}
