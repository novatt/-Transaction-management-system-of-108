package com.example.demo.ORDER;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.SHOPCAR.Shopcar;


@Service
public class Orderdao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public Order queryorderbyorder_id(String order_id) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
       
        Order order = new Order();
        try {
    		order = jdbcTemplate.queryForObject(sql, new OrderRowMapper() , order_id);
    		System.out.print("成功查询订单");
        	return order;
		}catch(Exception e)
		{
			return null;
		}
    }
	
	public List<Order> queryorder_in(int customer_id , Shopcar shopcar) {//内部使用，看有多少个以前的相同订单
        String sql = "SELECT * FROM orders WHERE customer_id=? AND book_id = ?";
        List<Order> orderList = null;
        try {
	        orderList = jdbcTemplate.query(sql , new OrderRowMapper() , customer_id , shopcar.getBook_id());
	        System.out.print("成功查询该用户所有订单");
	        return orderList;
        }catch(Exception e)
		{
			return null;
		}
    }
	
	public List<Order> queryallorder() {
        String sql = "SELECT * FROM orders";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper());
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
	
	public List<Order> queryorder(int customer_id) {
        String sql = "SELECT * FROM orders WHERE customer_id=?";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() , customer_id);
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
	
	public List<Order> queryorder_buy(int customer_id) {
        String sql = "SELECT * FROM orders WHERE customer_id=? and if_post = 0";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() , customer_id);
        System.out.print("成功查询该用户待发货订单");
        return orderList;
    }
	
	public List<Order> queryorder_post(int customer_id) {
        String sql = "SELECT * FROM orders WHERE customer_id=? and if_post = 1 and if_identify = 0 and if_return = 0";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() , customer_id);
        System.out.print("成功查询该用户已发货订单");
        return orderList;
    }
	
	public List<Order> queryorder_return(int customer_id) {
        String sql = "SELECT * FROM orders WHERE customer_id=? and if_post = 1 and if_return > 0";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() , customer_id);
        System.out.println("成功查询该用户退货订单");
        return orderList;
    }
	
	public Order queryorder_return_byid(String order_id) {
        String sql = "SELECT * FROM orders WHERE order_id=?";
//        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() , order_id);
        Order order = new Order();
        try {
    		order = jdbcTemplate.queryForObject(sql, new OrderRowMapper() , order_id);
    		System.out.print("成功查询订单");
        	return order;
		}catch(Exception e)
		{
			return null;
		}
    }
	
	public List<Order> queryorder_publisher(String id) {
        String sql = "SELECT * FROM orders where book_id in (select id from book where publisher = ?)";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() ,id);
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
	
	
	
	public List<Order> queryorder_oneday_in(String id ,String date) {
        String sql = "SELECT * FROM orders where book_id in (select id from book where publisher = ?) AND time = ? AND if_return = 0";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() ,id , date);
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
	
	public List<Order> queryorder_onemonth_in(String id ,String date) {
        String sql = "SELECT * FROM orders where book_id in (select id from book where publisher = ?) AND time REGEXP ? AND if_return = 0 ORDER BY time";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() ,id , date);
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
	
	public List<Order> queryorder_period_in(String id ,String date_start , String date_end) {
        String sql = "SELECT * FROM orders where book_id in (select id from book where publisher = ?) AND time >= ? AND time <= ? AND if_return = 0 ORDER BY time";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() ,id , date_start , date_end);
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
	
	public List<Order> queryorder_publisher_newpost(String id) {
        String sql = "SELECT * FROM orders where book_id in (select id from book where publisher = ?)  AND if_post = 0";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() ,id);
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
	
	public List<Order> queryorder_publisher_finish(String id) {
        String sql = "SELECT * FROM orders where book_id in (select id from book where publisher = ?)  AND if_identify = 1";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() ,id);
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
	
	public List<Order> queryorder_publisher_newreturn(String id) {
        String sql = "SELECT * FROM orders where book_id in (select id from book where publisher = ?) AND if_return = 1";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() ,id);
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
	
	public int insertorder(int customer_id , Shopcar shopcar , double score) {
        String sql = "INSERT INTO orders ( order_id , customer_id, book_id , number , single_price , discount , money_first , money_last , score_use , if_post , if_identify , if_return ,time , times ) VALUES(? , ? , ?, ? , ? , ? , ?, ? , ? , ? , ? , ? , ? , ?)";
        
          jdbcTemplate.update(sql ,"" + customer_id + shopcar.getBook_id() + (this.queryorder_in(customer_id, shopcar).size() + 1),
        		  customer_id , shopcar.getBook_id() , shopcar.getNumber() , shopcar.getSingle_price() , shopcar.getDiscount() 
        		, shopcar.getSingle_price()*shopcar.getNumber() , shopcar.getSingle_price()*shopcar.getNumber()*(shopcar.getDiscount()) - score
        		, 0 , 0 , 0 , 0 ,new Date(System.currentTimeMillis()).toString(), this.queryorder_in(customer_id, shopcar).size() + 1);
        return this.queryorder_in(customer_id, shopcar).size();
    }
	
	public void updateorder_identify_ById(String order_id){
        String sql = "UPDATE orders set if_identify = 1 where order_id = ?";
        jdbcTemplate.update(sql,  order_id);
        System.out.print("成功更新");
    }
	
	public void updateorder_post_ById(String order_id){
        String sql = "UPDATE orders set if_post = 1 where order_id = ?";
        jdbcTemplate.update(sql,  order_id);
        System.out.print("成功更新");
    }
	
	public void updateorder_return_ById(String order_id){
        String sql = "UPDATE orders set if_return = 1 where order_id = ?";
        jdbcTemplate.update(sql,  order_id);
        System.out.print("成功更新");
    }
	
	public void updateorder_return_publisher_ById(String order_id){
        String sql = "UPDATE orders set if_return = 2 where order_id = ?";
        jdbcTemplate.update(sql,  order_id);
        System.out.print("成功更新");
    }
	
	//待评价的订单
	public  List<Order> query_wait_review(int customer_id){
	
		String sql = "SELECT * FROM orders where if_identify = 1 AND customer_id = ?";
        List<Order> orderList = jdbcTemplate.query(sql , new OrderRowMapper() , customer_id);
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
}
