package com.example.demo.SHOPCAR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.BOOKS.Books;
import com.example.demo.BOOKS.Booksdao;

@Service
public class Shopcarservice {
	
	@Autowired
	private Shopcardao shopcardao;
	
	@Autowired
	private Booksdao booksdao;
	
	public List<Shopcar> get_order(int customer_id) throws Exception {  
		
		return shopcardao.queryorder(customer_id);	
	}   
	
	public Books addingorder(int customer_id , Shopcar shopcar) {
		Shopcar order = shopcardao.queryorderbybook_id(customer_id, shopcar.getBook_id());
		Books book = booksdao.queryBookById(shopcar.getBook_id());
		Books book1 = new Books();
		if(book == null)
		{
			book1.setId("-1");
			return book1;
		}
		else if(book.getNumber() < shopcar.getNumber())
	    {
	    	book1.setId("-2");
	    	return book1;
	    }
		else if(order == null)
		{
			return shopcardao.insertorder(customer_id , shopcar);
		}
		else
		{
			return null;
		}
	}
	
	public Books changingorder(int customer_id , Shopcar shopcar) {
		Shopcar order = shopcardao.queryorderbybook_id(customer_id, shopcar.getBook_id());
		Books book = booksdao.queryBookById(shopcar.getBook_id());
		Books book1 = new Books();
		if(book == null)
		{
			book1.setId("-1");
			return book1;
		}
		else if(order == null)
		{
			return null;
		}
		else if(book.getNumber() < shopcar.getNumber())
	    {
	    	book1.setId("-2");
	    	return book1;
	    }
		else
		{
			return shopcardao.updateorderById(customer_id , shopcar);
		}
		
		
	}
	
	public void removeorder(int customer_id , String book_id) {
		shopcardao.deleteorderById(customer_id, book_id);
	}
}
