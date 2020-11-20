package com.example.demo.MANAGER;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ManagerRowMapper implements RowMapper<Manager>{

	@Override
    public Manager mapRow(ResultSet rs,int i) throws SQLException {
		Manager manager= new Manager();
		manager.setId(rs.getString("id"));
		manager.setPassword(rs.getString("password"));
        manager.setType(rs.getString("type"));
        manager.setIf_identify(rs.getInt("if_identify"));
		
        return manager;
    }
}
