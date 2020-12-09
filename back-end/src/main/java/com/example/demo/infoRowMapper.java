package com.example.demo;

import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class infoRowMapper implements RowMapper<info>{

	@Override
    public info mapRow(ResultSet rs,int i) throws SQLException {
        info infos= new info(rs.getString("manager_id"),rs.getString("publisher_id"),rs.getString("book_id"),rs.getString("body"),rs.getString("time"));
        infos.setManager_id(rs.getString("manager_id"));
        infos.setPublisher_id(rs.getString("publisher_id"));
        infos.setBook_id(rs.getString("book_id"));
        infos.setBody(rs.getString("body"));
        infos.setTime(rs.getString("time"));
        return infos;
    }
}
