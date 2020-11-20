package com.example.demo.BOOKS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.MANAGER.Manager;
import com.example.demo.MANAGER.Managerservice;
import com.example.demo.REVIEW.Review;
import com.example.demo.REVIEW.Reviewservice;


@RestController
public class Bookscontroller {

	@Autowired
    private Booksservice booksservice;
	
	@Autowired
	private Managerservice managerservice;
	
	@Autowired
	private Reviewservice reviewservice;
	
	@GetMapping("/books/allbooks")
    List<Books> getAllBooks() {
        return booksservice.booksdetails_all();
    }
	
	@GetMapping("/books/by_type")
    List<Books> getBooks_type(@RequestParam(value = "type", defaultValue = "0") String type) {
        return booksservice.booksdetails_type(type);
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
	
	@PostMapping("/books/adding")
    public String addBook(@RequestBody Books book , @RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password) {
            Manager manager = booksservice.addbooks(book , id , password);
            if(manager.getId().equals("-1"))
            {
            	return "抱歉，身份认证失败~";
            }
            else if(manager.getId().equals("-3"))
            {
            	return "抱歉，该图书ID已经存在，请更换~";
            }
            else if(manager.getId().equals("-2"))
            {
            	return "抱歉，您无权限上架该图书~";
            }
            else if(manager.getId().equals("-5"))
            {
            	return "抱歉，您的账号当前为无效状态，修改失败~";
            }
			return "上架书籍成功！\n书目ID：" + book.getId() + 
					"\n书目名字：" + book.getName() + 
					"\n书目单价：" + book.getPrice() +
					"\n书目折扣情况：" + book.getDiscount() +
					"\n书目类型：" + book.getType() + 
					"\n书目库存：" + book.getNumber() +
					"\n书目出版社：" + book.getPublisher();
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
 
	 @DeleteMapping("/books/removal")
	 public String deleteBook(@RequestBody Books book , @RequestParam(value = "id", defaultValue = "0") String id,
				@RequestParam(value = "password", defaultValue = "0") String password) {
		 Manager manager1 = managerservice.if_manager(id, password);
		 if(manager1.getId().equals("-1") || manager1.getId().equals("-2"))
	     {
	    	return "身份认证失败！";
	     }
		 Manager manager = booksservice.removebook(book , id , password);
         if(manager.getId().equals("-1"))
         {
         	return "抱歉，身份认证失败~";
         }
         else if(manager.getId().equals("-3"))
         {
         	return "抱歉，该图书ID不存在，请更换~";
         }
         else if(manager.getId().equals("-2"))
         {
         	return "抱歉，您无权限下架该图书~";
         }
         else if(manager.getId().equals("-5"))
         {
         	return "抱歉，您的账号当前为无效状态，修改失败~";
         }
			return "成功下架ID为" + book.getId() + "的图书";
	 }
}
