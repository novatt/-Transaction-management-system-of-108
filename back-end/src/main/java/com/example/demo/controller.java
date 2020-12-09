 package com.example.demo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
public class controller implements WebMvcConfigurer{

	static public String customer_id;
	static public int which_type = 0;
		
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
	
	@Autowired
	private infoservice infor;
	 
	
	@GetMapping("/")
	public String index() {
		return "login";
	}

		
	@GetMapping("/firstpage")
	public String firstpage() throws Exception {
		return "first";
	}
	
	@GetMapping("/info/manager")
	public String allinfo(Model model) throws Exception {
		List<info> infos = infor.infos_all();
		model.addAttribute("infos", infos);
		return "manager/info";
	}
	
	@GetMapping("/info/publisher")
	public String publisherinfo(Model model) throws Exception {
		List<info> infos = infor.infos_publisher(customer_id);
		model.addAttribute("infos", infos);
		return "publisher/info";
	}
	
	//个人信息
	@GetMapping("/accounts/detail")
	public String toaccount(Model model) throws Exception {
		int c_id = Integer.parseInt(customer_id);
	    Customer customer = customerservice.query(c_id);
		model.addAttribute("customer", customer);
		return "new_file";
	}
	
	@PostMapping("/accounts/detail")
    public String account(Customer customer) throws Exception {
			int change_id = Integer.parseInt(controller.customer_id);
			customerservice.updateCustomer_address_ById(customer, change_id);
			customerservice.updateCustomer_password_ById(customer, change_id);
			return "redirect:/accounts/detail";
    }
	
	//登入
	@GetMapping("/accounts/login")
	public String Customer_login(@RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password,
			Model model , HttpSession session) throws Exception {
			
		    Manager manager = managerservice.if_manager(id, password);
		    
		    //出版社正确
		    if(manager.getId().equals(id) && manager.getType().equals("P"))
		    {
		    	session.setAttribute("id", id);
		    	customer_id = id;
		    	which_type = 2;
		    	return "first";
		    }
		    //管理员正确
		    else if(manager.getId().equals(id) && manager.getType().equals("M"))
		    {
		    	session.setAttribute("id", id);
		    	customer_id = id;
		    	which_type = 3;
		    	return "first";
		    }
		    
		    int c_id = Integer.parseInt(id);
		    Customer customer = customerservice.customerdetails(c_id,password);
		    //默认进入的情况
		    if("0".equals(password))
		    {
		    	return "login";
		    }
		    //无此用户
		    else if(customer.getId() == -2)
		    {
		    	model.addAttribute("msg","请输入正确的用户名");
		    	return "login";
		    }
		    //输入错误
		    else if(customer.getId() == -1)
		    {
		    	model.addAttribute("msg","用户名密码不匹配，请重新输入。");
		    	return "login";
		    }
		    //普通用户正确
		    else if(customer.getId() == c_id)
		    {
		    	session.setAttribute("id", id);
		    	customer_id = id;
		    	which_type = 1;
		    	return "first";
		    }
		    else
		    {
		    	model.addAttribute("msg","用户名密码不匹配，请重新输入。");
		    	return "login";
		    }
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
		.excludePathPatterns("/login","/login.html","/index.html","/","/welcome","/welcome.html","/admin/login","/accounts/login","/css/**","/js/**","/img/**","/assets/**","/fonts/**","/image/**");
	}
	
	
//	@GetMapping("/nb")
//	Order gettest(@RequestBody Order order) {
//        return test.queryorderbybook_id(order.getOrder_id());
//    }
	

    
}
