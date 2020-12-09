package com.example.demo.BOOKS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Booksdao {
	
	    @Autowired
	    private JdbcTemplate jdbcTemplate;

	    public Books queryBookById(String id) {
	        String sql = "SELECT * FROM book WHERE id=? ";

	        Books book = new Books();
	        try {
	        	 	book = jdbcTemplate.queryForObject(sql , new BooksRowMapper(), id);
		        	System.out.print("成功查询");
		        	return book;
			}catch(Exception e)
			{
				return null;
			}
	    }

	    public List<Books> queryBooksByType(String type) {
	        String sql = "SELECT * FROM book WHERE type=?";
	        List<Books> bookList = jdbcTemplate.query(sql , new BooksRowMapper() , type);
	        System.out.print("成功查询该种别所有书");
	        return bookList;
	    }
	    
	    public List<Books> queryBooksByPublisher(String publisher) {
	        String sql = "SELECT * FROM book WHERE publisher=?";
	        List<Books> bookList = jdbcTemplate.query(sql , new BooksRowMapper() , publisher);
	        System.out.print("成功查询该出版社所有书");
	        return bookList;
	    }

	    public List<Books> queryAllBooks() {
	        String sql = "SELECT * FROM book";
	        List<Books> bookList = jdbcTemplate.query(sql , new BooksRowMapper());
	        System.out.print("成功查询所有书");
	        return bookList;
	    }

	    public Books insertBook(Books book) {
	        String sql = "INSERT INTO book (id,name,type,price,discount,star,review_number,number,publisher) VALUES(?, ?, ?,?,?,? ,?, ?,?)";
	        jdbcTemplate.update(sql,book.getId(), book.getName(), book.getType() , book.getPrice(), book.getDiscount() , 0 , 0 , book.getNumber() , book.getPublisher());
	        String sql2 = "SELECT * FROM book WHERE id=? ";
	        Books book2 = jdbcTemplate.queryForObject(sql2, new BooksRowMapper(), book.getId());
	        System.out.print("成功插入");
	        return book2;
	    }

	    public void updateBook_discountById(String id,Books book){
	        String sql = "UPDATE book set discount =? WHERE id=?";
	        jdbcTemplate.update(sql, book.getDiscount(),book.getId());
	        System.out.print("成功更新");

	    }
	    
	    public void updateBook_numberById(String id,Books book){
	        String sql = "UPDATE book set number =? WHERE id=?";
	        jdbcTemplate.update(sql, book.getNumber(),book.getId());
	        System.out.print("成功更新");

	    }

	    public void deleteBookById(String id) {
	        String sql = "DELETE FROM book WHERE id=?";
	        jdbcTemplate.update(sql, id);
	        System.out.print("成功删除" + id);
	    }
	}
