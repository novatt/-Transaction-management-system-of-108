package com.example.demo.MANAGER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Managerdao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public Manager queryManagerById(String id) {
        String sql = "SELECT * FROM manager WHERE id=? ";
        Manager manager = new Manager();
        try {
        		manager = jdbcTemplate.queryForObject(sql, new ManagerRowMapper(), id);
	        	System.out.print("成功查询到了");
	        	return manager;
		}catch(Exception e)
		{
			return null;
		}
    }
	
	public void insertpublisher(String id ,String password) {
		String sql = "INSERT INTO manager (id , password , type , if_identify) VALUES (? , ? , ? , ?)";
		jdbcTemplate.update(sql , id , password , "P" , 0);
	}
	
	public void publisher_identify(String id) {
		String sql = "UPDATE manager SET if_identify = 1 WHERE id = ?";
		jdbcTemplate.update(sql , id);
	}
}
