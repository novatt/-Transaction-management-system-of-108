package com.example.demo.CUSTOMER;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Customerdao {

	 @Autowired
	    private JdbcTemplate jdbcTemplate;
	 
	 public Customer queryCustomerById(int id) throws Exception{
	        String sql = "SELECT * FROM customer WHERE id=? ";

	        Customer customer = null;

	        try {     	
		        	customer = jdbcTemplate.queryForObject(sql, new CustomerRowMapper(), id);
		        	System.out.print("成功查询");
		        	return customer;
			}catch(Exception e)
			{
				return null;
			}
	    }
	 
	 public double get_score(int customer_id) {//获取一位用户的积分
		 double score_now;
		 String sql = "SELECT * FROM customer WHERE id=? ";
		 Customer customer = jdbcTemplate.queryForObject(sql, new CustomerRowMapper(), customer_id);
		 score_now = customer.getScore();
		 return score_now;
	 }
	 
	 public Customer addcustomer(Customer customer) {//注册
	        String sql = "INSERT INTO customer (id,password,score_now,address) VALUES(?, ?, ?,?)";
	        jdbcTemplate.update(sql,customer.getId(), customer.getPassword(), 0, null);
	        String sql2 = "SELECT * FROM customer WHERE id=? ";
	        Customer customer1 = jdbcTemplate.queryForObject(sql2, new CustomerRowMapper(), customer.getId());
	        System.out.print("成功插入");
	        return customer1;
	    }
	 
	 public Customer updateCustomer_password_ById(int id , String password , Customer customer) throws Exception{//改密码
	        String sql = "UPDATE customer set password=? WHERE id=?";
	        jdbcTemplate.update(sql, customer.getPassword() , id);
	        System.out.print("成功更新");
	        return queryCustomerById(id);
	 }
	 
	 public Customer updateCustomer_address_ById(int id , String password , Customer customer) throws Exception{//改地址
	        String sql = "UPDATE customer set address =? WHERE id=?";
	        jdbcTemplate.update(sql , customer.getAddress() , id);
	        System.out.print("成功更新");
	        return queryCustomerById(id);
	 }
}
