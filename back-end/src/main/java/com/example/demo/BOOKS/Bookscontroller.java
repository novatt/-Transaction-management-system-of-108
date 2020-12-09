package com.example.demo.BOOKS;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller;
import com.example.demo.info;
import com.example.demo.infoservice;
import com.example.demo.MANAGER.Manager;
import com.example.demo.MANAGER.Managerservice;
import com.example.demo.REVIEW.Review;
import com.example.demo.REVIEW.Reviewservice;
import com.example.demo.SHOPCAR.Shopcar;


@Controller
public class Bookscontroller {

	@Autowired
    private Booksservice booksservice;
	
	@Autowired
	private Managerservice managerservice;
	
	@Autowired
	private Reviewservice reviewservice;
	
	@Autowired
	private infoservice infos;
	
	@GetMapping("/books/allbooks")
    public String getAllBooks(Model model) {
		Collection<Books> books = booksservice.booksdetails_all();
		model.addAttribute("books", books);
        return "list";
    }
	
	@GetMapping("/books/by_type/{id}")
    public String getBooks_type(@PathVariable("id") int id , Model model) {
        String type = "";
        if(id == 1)
        {
        	type = "teaching";
        }
        else if(id == 2)
        {
        	type = "lol";
        }
        else if(id == 3)
        {
        	type = "dictionary";
        }
        else if(id == 4)
        {
        	type = "novel";
        }
        else if(id == 5)
        {
        	type = "magazine";
        }
        Collection<Books> books = booksservice.booksdetails_type(type);
		model.addAttribute("books", books);
        return "list";
    }
	
	@GetMapping("/books/allbooks/{id}")
    public String getBooks_detail(@PathVariable("id") String id , Model model) throws Exception {
		Books book = booksservice.booksdetails_one(id);
		Collection<Review> reviews = reviewservice.get_review(id);
		model.addAttribute("book", book);
		model.addAttribute("reviews", reviews);
        return "book_detail_normal";
    }
	
	@GetMapping("/book/allbooks/{id}")
    public String getBook_detail(@PathVariable("id") String id , Model model) throws Exception {
		Books book = booksservice.booksdetails_one(id);
		Collection<Review> reviews = reviewservice.get_review(id);
		model.addAttribute("book", book);
		model.addAttribute("reviews", reviews);
        return "book_detail";
    }
	
	@GetMapping("/books/by_id")
	public String getBook(@RequestParam(value = "id", defaultValue = "0") String book_id) throws Exception {
		Books book = booksservice.booksdetails_one(book_id);
		if(book == null)
		{
			return "抱歉，未查到该ID对应的书籍~";
		}
		else
		{
			StringBuffer str = new StringBuffer();
			for(int i = 0 ; i < reviewservice.get_review(book_id).size() ; i++)
			{
				str.append(reviewservice.get_review(book_id).get(i).toString());
			}
			return "图书ID为：" + book.getId() + 
					"\n图书类型为：" + book.getType() +
			    	"\n图书单价为：" + book.getPrice() + 
			    	"\n图书出版社为：" + book.getPublisher() + 
			    	"\n图书库存为：" + book.getNumber() + 
			    	"\n图书折扣为：" + book.getDiscount() + 
			    	"\n图书星级为：" + book.getReview_star() + 
			    	"\n参与评级人数 ：" + book.getReview_number() + 
			    	"\n\n具体评论 ：" + str
			    	;
		}
        
    }
	
	@GetMapping("/books/adding")
	public String toAddbook(Model model) {
		return "publisher/add";
	}
	
	@PostMapping("/books/adding")
    public String addbook(Books book) throws Exception {
		booksservice.addbooks(book , controller.customer_id);
		return "redirect:/books/allbooks";
    }
	
	//图书下架并更新通知
	@PostMapping("/books/removal")
	 public String deleteBook(Books book)throws Exception {
		 System.out.println("内容为：" + book.getName());
		 booksservice.removebook(book , controller.customer_id);
		 info infor = new info(controller.customer_id,book.getPublisher(),book.getId(),book.getName(),new Date(System.currentTimeMillis()).toString());
		 infos.infos_insert(infor);
		 return "redirect:/books/allbooks";
	 }
	
	@PutMapping("/books/changing/discount")
    public String updatebook_discount(@RequestBody Books book, @RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception{
		Manager manager = managerservice.if_manager(id, password);
		if(manager.getId().equals("-1") || manager.getId().equals("-2"))
    	{
    		return "身份认证失败！";
    	}
    	else if(manager.getType().equals("M"))
    	{
    		return "抱歉，您无权限对该订单进行操作~";
    	}
		Books book1 = booksservice.changebooks_discount(book, id, password);
	 	if(book1 == null)
	 	{
	 		return "抱歉，系统中并无该图书，请更换！";
	 	}
	 	else if(book1.getId().equals("-1"))
	 	{
	 		return "身份认证失败！";
	 	}
	 	else if(book1.getId().equals("-2"))
	 	{
	 		return "抱歉，您无权限对该图书进行修改！";
	 	}
	 	else if(book1.getId().equals("-3"))
	 	{
	 		return "抱歉，您的账号当前为无效状态，修改失败~";
	 	}
	 	else
	 	{
	 		return "修改书籍成功！\n书目ID：" + book1.getId() + 
					"\n书目名字：" + book1.getName() + 
					"\n书目单价：" + book1.getPrice() +
					"\n书目折扣情况：" + book1.getDiscount() +
					"\n书目类型：" + book1.getType() + 
					"\n书目库存：" + book1.getNumber() +
					"\n书目出版社：" + book1.getPublisher();
	 	}	 
 }

	@PutMapping("/books/changing/number")
    public String updatebook_number(@RequestBody Books book, @RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception{
		Manager manager = managerservice.if_manager(id, password);
		if(manager.getId().equals("-1") || manager.getId().equals("-2"))
    	{
    		return "身份认证失败！";
    	}
    	else if(manager.getType().equals("M"))
    	{
    		return "抱歉，您无权限对该订单进行操作~";
    	}
		Books book1 = booksservice.changebooks_number(book, id, password);
	 	if(book1 == null)
	 	{
	 		return "抱歉，系统中并无该图书，请更换！";
	 	}
	 	else if(book1.getId().equals("-1"))
	 	{
	 		return "抱歉，身份认证失败！";
	 	}
	 	else if(book1.getId().equals("-2"))
	 	{
	 		return "抱歉，您无权限对该图书进行修改！";
	 	}
	 	else if(book1.getId().equals("-3"))
	 	{
	 		return "抱歉，您的账号当前为无效状态，修改失败~";
	 	}
	 	else
	 	{
	 		return "修改书籍成功！\n书目ID：" + book1.getId() + 
					"\n书目名字：" + book1.getName() + 
					"\n书目单价：" + book1.getPrice() +
					"\n书目折扣情况：" + book1.getDiscount() +
					"\n书目类型：" + book1.getType() + 
					"\n书目库存：" + book1.getNumber() +
					"\n书目出版社：" + book1.getPublisher();
	 	}	 
 }
 
	 
}
