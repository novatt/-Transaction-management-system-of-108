package com.example.demo.SHOPCAR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.BOOKS.Books;
import com.example.demo.BOOKS.Booksdao;

@Service
public class Shopcardao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Autowired
	private Booksdao booksdao;
	
	public Shopcar queryorderbybook_id(int customer_id , String book_id) {
        String sql = "SELECT * FROM shopcar WHERE customer_id = ? AND book_id = ?";
       
        Shopcar order = new Shopcar();
        try {
    		order = jdbcTemplate.queryForObject(sql, new ShopcarRowMapper() , customer_id , book_id);
    		System.out.print("成功查询订单");
        	return order;
		}catch(Exception e)
		{
			return null;
		}
    }
	
	public List<Shopcar> queryorder(int id) {
        String sql = "SELECT * FROM shopcar WHERE customer_id=?";
        List<Shopcar> orderList = jdbcTemplate.query(sql , new ShopcarRowMapper() , id);
        System.out.print("成功查询该用户所有订单");
        return orderList;
    }
	
	public Books insertorder(int customer_id , Shopcar shopcar) {
        String sql = "INSERT INTO shopcar ( customer_id, book_id , number , single_price, discount) VALUES(?, ? , ? , ? , ?)";
        Books book = booksdao.queryBookById(shopcar.getBook_id());
        jdbcTemplate.update(sql ,customer_id , shopcar.getBook_id() , shopcar.getNumber() ,  book.getPrice(), book.getDiscount());
        return book;
    }
	
	public Books updateorderById(int customer_id , Shopcar shopcar){
        String sql = "UPDATE shopcar set number=? where customer_id = ? AND book_id = ?";
        jdbcTemplate.update(sql, shopcar.getNumber() , customer_id , shopcar.getBook_id());
        System.out.print("成功更新");
        Books book = booksdao.queryBookById(shopcar.getBook_id());
        return book;
    }

	
	public void deleteorderById(int customer_id , String book_id) {
        String sql = "DELETE FROM shopcar WHERE customer_id = ? AND book_id=?";
        jdbcTemplate.update(sql, customer_id , book_id);
        System.out.print("成功删除" + book_id);
    }
}
