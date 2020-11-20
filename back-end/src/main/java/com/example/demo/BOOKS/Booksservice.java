package com.example.demo.BOOKS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.MANAGER.Manager;
import com.example.demo.MANAGER.Managerdao;

@Service
public class Booksservice {

	@Autowired
	private Booksdao booksdao;
	
	@Autowired
	private Managerdao managerdao;
	
	public Manager addbooks(Books book , String id , String password) {
		Manager manager1 = managerdao.queryManagerById(id);
		Books book1 = booksdao.queryBookById(book.getId());
		Manager manager_return = new Manager();
		if(manager1 == null)
		{
			manager_return.setId("-1");
			return manager_return;//身份验证失败
		}
		else if(!manager1.getPassword().equals(password))
		{
			manager_return.setId("-1");
			return manager_return;//身份验证失败
		}
		else if(manager1.getType().equals("M") )
		{
			manager_return.setId("-2");
			return manager_return;//无权限
		}
		else if(manager1.getType().equals("P")  && !book.getPublisher().equals(id))
		{
			manager_return.setId("-2");
			return manager_return;//无权限
		}
		else if(manager1.getIf_identify() == 0)
		{
			manager_return.setId("-5");
			return manager_return;//账号无效
		}
		else if(book1 != null)
		{
			manager_return.setId("-3");
			return manager_return;//图书ID已经存在
		}
		else if(manager1.getType().equals("P")  && book.getPublisher().equals(id))
		{
			booksdao.insertBook(book);
			return manager1;
		}	
		else
		{
			manager_return.setId("-4");
			return manager_return;
		}
	}
	
	
	
	public Books booksdetails_one(String id) {
		return booksdao.queryBookById(id);
	}
	
	public List<Books> booksdetails_type(String type) {
		return booksdao.queryBooksByType(type);
	}
	
	public List<Books> booksdetails_all() {
		return booksdao.queryAllBooks();
	}
	
	public Manager removebook(Books book , String id , String password) {
		Manager manager1 = managerdao.queryManagerById(id);
		Books book1 = booksdao.queryBookById(book.getId());
		Manager manager_return = new Manager();
		if(manager1 == null)
		{
			manager_return.setId("-1");
			return manager_return;//身份验证失败
		}
		else if(book1 == null)
		{
			manager_return.setId("-3");
			return manager_return;//图书ID不存在
		}
		else if(!manager1.getPassword().equals(password))
		{
			manager_return.setId("-1");
			return manager_return;//身份验证失败
		}
//		else if(manager1.getType().equals("M") )
//		{
//			manager_return.setId("-2");
//			return manager_return;//无权限
//		}
		else if(manager1.getType().equals("P")  && !book1.getPublisher().equals(id))
		{
			manager_return.setId("-2");
			return manager_return;//无权限
		}
		else if(manager1.getType().equals("P")  && manager1.getIf_identify() == 0)
		{
			manager_return.setId("-5");
			return manager_return;//账号无效
		}
		else if((manager1.getType().equals("P")  && book.getPublisher().equals(id)) || manager1.getType().equals("M"))
		{
			booksdao.deleteBookById(book.getId());
			return manager1;
		}	
		else
		{
			manager_return.setId("-4");
			return manager_return;
		}
	}
	
	public Books changebooks_discount(Books book , String id , String password) {
		Manager manager1 = managerdao.queryManagerById(id);
		Books book1 = booksdao.queryBookById(book.getId());
		Books book2 = new Books();
		if(manager1 == null || !manager1.getPassword().equals(password))
		{
			book2.setId("-1");
			return book2;
		}
		else if(manager1.getIf_identify() == 0)
		{
			book2.setId("-3");
			return book2;//账号未得到认证
		}
		else if(book1 == null)
		{
			return null;//图书不存在
		}
		else if(manager1.getPassword().equals(password) && manager1.getType().equals("P") && book1.getPublisher().equals(id))
		{
			booksdao.updateBook_discountById(id, book);
			return booksdao.queryBookById(book.getId());
		}
		else
		{
			book2.setId("-2");
			return book2;
		}		
	}
	
	public Books changebooks_number(Books book , String id , String password) {
		Manager manager1 = managerdao.queryManagerById(id);
		Books book1 = booksdao.queryBookById(book.getId());
		Books book2 = new Books();
		if(manager1 == null || !manager1.getPassword().equals(password))
		{
			book2.setId("-1");
			return book2;
		}
		else if(book1 == null)
		{
			return null;
		}
		else if(manager1.getIf_identify() == 0)
		{
			book2.setId("-3");
			return book2;//账号未得到认证
		}
		else if(manager1.getPassword().equals(password) && manager1.getType().equals("P") && book1.getPublisher().equals(id))
		{
			booksdao.updateBook_numberById(id, book);
			return booksdao.queryBookById(book.getId());
		}
		else
		{
			book2.setId("-2");
			return book2;
		}		
	}
	
}
