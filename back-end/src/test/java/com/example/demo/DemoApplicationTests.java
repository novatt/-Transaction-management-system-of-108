package com.example.demo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	DataSource datasource;
	
	@Test
	void contextLoads() throws SQLException {
		System.out.println(datasource.getClass());
		Connection connection = datasource.getConnection();
		System.out.println(connection);
		
		connection.close();
	}

}
