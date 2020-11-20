 package com.example.demo;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.BOOKS.Books;
import com.example.demo.BOOKS.Booksservice;
import com.example.demo.CUSTOMER.Customer;
import com.example.demo.CUSTOMER.Customerservice;
import com.example.demo.MANAGER.Manager;
import com.example.demo.MANAGER.Managerservice;
import com.example.demo.ORDER.Order;
import com.example.demo.ORDER.Orderdao;
import com.example.demo.ORDER.Orderservice;
import com.example.demo.REVIEW.Review;
import com.example.demo.REVIEW.Reviewservice;
import com.example.demo.SHOPCAR.Shopcar;
import com.example.demo.SHOPCAR.Shopcarservice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class controller {

	@Autowired
    private Orderdao test;
	
	@Autowired
    private Booksservice booksservice;
	
	@Autowired
    private Shopcarservice shopcarservice;
	
	@Autowired
	private Customerservice customerservice;
	
	@Autowired
	private Managerservice managerservice;
	
	@Autowired
	private Orderservice orderservice;
	 
	
	@GetMapping("/")
	public String index() {
		return "welcome";
	}
	
//	@GetMapping("/level")
//	public String level() {
//		return "level";
//	}
	
//	@GetMapping("/accounts/login")
//	public String Customer_login(@RequestParam(value = "id", defaultValue = "0") int id,
//			@RequestParam(value = "password", defaultValue = "0") String password ,Model model) throws Exception {
//		    Customer customer = customerservice.customerdetails(id,password);
//		    if(customer.getId() == -2)
//		    {
////		    	model.addAttribute("msg", "账号输入有误!");
////				return "index";
//		    	return "账号输入有误!";
//		    }
//		    else if(customer.getId() == -1)
//		    {
////		    	model.addAttribute("msg", "账号密码不匹配!");
////				return "index";
//		    	return "账号密码不匹配!";
//		    }
//		    else
//		    {
////		    	return "Customer";//登入成功
//		    	return "level";
//		    	//return "用户id为：" + customer.getId() + "   \n用户当前积分为：" + customer.getScore() + "   \n用户的地址为：" + customer.getAddress();
//		    }
//	}
//	
	@GetMapping("/accounts/login")
	public String Customer_login(@RequestParam(value = "id", defaultValue = "0") int id,
			@RequestParam(value = "password", defaultValue = "0") String password,Model model) throws Exception {
		    Customer customer = customerservice.customerdetails(id,password);
		    if(customer.getId() == -2)
		    {
//		    	model.addAttribute("msg", "账号输入有误!");
//				return "index";
		    	return "index";
		    }
		    else if(customer.getId() == -1)
		    {
//		    	model.addAttribute("msg", "账号密码不匹配!");
//				return "index";
		    	return "index";
		    }
		    else
		    {
//		    	return "Customer";//登入成功
		    	return "level";
		    	//return "用户id为：" + customer.getId() + "   \n用户当前积分为：" + customer.getScore() + "   \n用户的地址为：" + customer.getAddress();
		    }
	}
	
	
//	@GetMapping("/nb")
//	Order gettest(@RequestBody Order order) {
//        return test.queryorderbybook_id(order.getOrder_id());
//    }
	

    
}
