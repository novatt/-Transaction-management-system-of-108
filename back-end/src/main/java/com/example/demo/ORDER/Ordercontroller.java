package com.example.demo.ORDER;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Period;
import com.example.demo.controller;
import com.example.demo.BOOKS.Books;
import com.example.demo.BOOKS.Booksservice;
import com.example.demo.CUSTOMER.Customer;
import com.example.demo.CUSTOMER.Customerservice;
import com.example.demo.MANAGER.Manager;
import com.example.demo.MANAGER.Managerservice;
import com.example.demo.SHOPCAR.Shopcar;
import com.example.demo.SHOPCAR.Shopcarservice;

@Controller
public class Ordercontroller {
	@Autowired
    private Booksservice booksservice;
	
	@Autowired
	private Orderservice orderservice;
	
	@Autowired
    private Shopcarservice shopcarservice;
	
	@Autowired
	private Customerservice customerservice;
	
	@Autowired
	private Managerservice managerservice;
	
	@GetMapping("/orders/publisher/oneday")
	public String order_publisher_oneday(@RequestBody Order order , @RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
		Manager manager = managerservice.if_manager(id, password);
		if(manager.getId().equals("-1") || manager.getId().equals("-2"))
		{
			return "抱歉，身份认证失败~";
		}
		else if(manager.getType().equals("M"))
		{
			return "抱歉，管理员身份无营业额系统~";
		}
		else if(manager.getIf_identify() == 0)
		{
			return "抱歉，您的账号处于无效状态，不可查看该系统~";
		}
		else
		{
			List<Order> order1 = orderservice.get_order_oneday_in(id,order.getTime());
			double total = 0;
			for(int i = 0 ; i < order1.size() ; i++)
			{
				total += order1.get(i).getMoney_last();
			}
			return order.getTime() + "\n出版社：" + id + "\n总销售额为:" + total + "\n具体订单详情为：\n" + order1.toString();
		}
	}
	
	@GetMapping("/orders/publisher/onemonth")
	public String order_publisher_onemonth(@RequestBody Order order , @RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
		Manager manager = managerservice.if_manager(id, password);
		if(manager.getId().equals("-1") || manager.getId().equals("-2"))
		{
			return "抱歉，身份认证失败~";
		}
		else
		{
			List<Order> order1 = orderservice.get_order_onemonth_in(id,order.getTime());
			double total = 0;
			for(int i = 0 ; i < order1.size() ; i++)
			{
				total += order1.get(i).getMoney_last();
			}
			
			return order.getTime() + "\n出版社：" + id + "\n总销售额为:" + total + "\n具体订单详情为：\n" + order1.toString();
		}
	}
	
	@GetMapping("/orders/publisher/period")
	public String order_publisher_period(@RequestBody Period period , @RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
		Manager manager = managerservice.if_manager(id, password);
		if(manager.getId().equals("-1") || manager.getId().equals("-2"))
		{
			return "抱歉，身份认证失败~";
		}
		else
		{
			List<Order> order1 = orderservice.get_order_period_in(id , period.getDate_start() , period.getDate_end());
			double total = 0;
			for(int i = 0 ; i < order1.size() ; i++)
			{
				total += order1.get(i).getMoney_last();
			}
			
			return period.getDate_start() + "-" + period.getDate_end() + "\n出版社：" + id + "\n总销售额为:" + total + "\n具体订单详情为：\n" + order1.toString();
		}
	}
	
	//按时间段查找
	@GetMapping("/orders/publisher/search/{which_type}")
	public String order_publisher_period(@PathVariable("which_type") String which_type , String beg , String end , Model model) throws Exception {
		double total = 0;
		double result[] = new double[12];
		//日查询
		if(which_type.equals("1"))
		{
			return "publisher/day_list";
		}
		//月查询
		else if(which_type.equals("2"))
		{
			double change[] = {0,0,0,0,0,0,0,0,0,0,0,0};
			for(int i = 1 ; i <= 12 ; i++)
			{
				List<Order> order;
				if(i < 10)
				{
					order = orderservice.get_order_onemonth_in(controller.customer_id, "2020-0"+i);
				}
				else
				{
					order = orderservice.get_order_onemonth_in(controller.customer_id, "2020-"+i);
				}
				model.addAttribute("order"+i, order);
				for(int j = 0 ; j < order.size() ; j++)
				{
					change[i-1] += order.get(j).getMoney_last();
				}
				total += change[i-1];
			}
			model.addAttribute("total", total);
			model.addAttribute("change", change);
			return "publisher/month_list";
		}
		//自由查询
		else if(which_type.equals("3"))
		{
			List<Order> order = orderservice.get_order_period_in(controller.customer_id , beg , end);
			for(int i = 0 ; i < order.size() ; i++)
			{
				total += order.get(i).getMoney_last();
			}
			
			for(int i = 0 ; i < 12 ; i++)
			{
				result[i] = total;
			}
			model.addAttribute("order", order);
			model.addAttribute("total", total);
			model.addAttribute("result", result);
			return "publisher/period_list";
		}
		return "publisher/period_list";
	}
	
	//支付购物车里的
	@GetMapping("/orders/adding/{book_id}")
	public String toAddorder(@PathVariable("book_id") String book_id , Model model) throws Exception {
		int change_id = Integer.parseInt(controller.customer_id);
		Customer customer = customerservice.query(change_id);
		Books book = booksservice.booksdetails_one(book_id);
		Shopcar shopcar = shopcarservice.get_one_order(change_id, book_id);
		model.addAttribute("shopcar", shopcar);
		model.addAttribute("customer", customer);
		model.addAttribute("book", book);
		return "order/add";
	}
	
	@PostMapping("/orders/adding")
    public String addorder(Order order) throws Exception{
 			int change_id = Integer.parseInt(controller.customer_id);
 			orderservice.addingorder(change_id, order);
			return "redirect:/orders/customer/all";
	}
	
	@GetMapping("/orders/customer/all")
	public String order_customer_all(Model model) throws Exception {
	   
		int change_id = Integer.parseInt(controller.customer_id);	
	    Collection<Order> order = orderservice.get_order(change_id);
    	model.addAttribute("order", order); 
    	return "order/list";	   
	}
	
	@GetMapping("/orders/manager/all")
	public String order_manager_all(Model model) throws Exception {	   
	    Collection<Order> order = orderservice.get_all_order();
    	model.addAttribute("order", order); 
    	return "manager/list";	   
	}
	
	//待发货
	@GetMapping("/orders/customer/buy")
	public String order_customer_buy(Model model) throws Exception {
	   
		int change_id = Integer.parseInt(controller.customer_id);	
	    Collection<Order> order = orderservice.get_order_buy(change_id);
    	model.addAttribute("order", order); 
    	return "order/list";	   
	}
	
	//已发货
	@GetMapping("/orders/customer/post")
	public String order_customer_post(Model model) throws Exception {
	   
		int change_id = Integer.parseInt(controller.customer_id);	
	    Collection<Order> order = orderservice.get_order_post(change_id);
    	model.addAttribute("order", order); 
    	return "order/post_list";	   
	}
	
	//申请退货的
	@GetMapping("/orders/customer/return")
	public String order_customer_return(Model model) throws Exception {
	   
		int change_id = Integer.parseInt(controller.customer_id);	
	    Collection<Order> order = orderservice.get_order_return(change_id);
    	model.addAttribute("order", order); 
    	return "order/return_list";	   
	}
	
	//用户确认收货
		@GetMapping("/orders/customer/identify/{order_id}")
		public String toidentifyshopping_cart(@PathVariable("order_id") String order_id , Model model) throws Exception {
			int change_id = Integer.parseInt(controller.customer_id);
			Customer customer = customerservice.query(change_id);
			Order order = orderservice.get_order_byid(order_id);
			Books book = booksservice.booksdetails_one(order.getBook_id());
			model.addAttribute("customer",customer);
			model.addAttribute("order", order); 
			model.addAttribute("book", book); 
			return "order/identify";
		}
		
		@PostMapping("/orders/customer/identifying")
	    public String identifyshopcar(Order order1) throws Exception{
	 			orderservice.identifyorder(order1.getOrder_id());
				return "redirect:/orders/customer/return";
		}
	
	//用户退货
	@GetMapping("/orders/customer/return/{order_id}")
	public String toUpdateshopping_cart(@PathVariable("order_id") String order_id , Model model) throws Exception {
		int change_id = Integer.parseInt(controller.customer_id);
		Customer customer = customerservice.query(change_id);
		Order order = orderservice.get_order_byid(order_id);
		Books book = booksservice.booksdetails_one(order.getBook_id());
		model.addAttribute("customer",customer);
		model.addAttribute("order", order); 
		model.addAttribute("book", book); 
		return "order/return";
	}
	
	@PostMapping("/orders/customer/returning")
    public String updateshopcar(Order order1) throws Exception{
 			orderservice.returnorder(order1.getOrder_id());
			return "redirect:/orders/customer/return";
	}
	
	//出版社收到的所有订单
	@GetMapping("/orders/publisher/all")
	public String order_publisher_all(Model model) throws Exception {	   
		Collection<Order> order = orderservice.get_order_publisher(controller.customer_id);
		model.addAttribute("order", order); 
		return "publisher/all_list";	
	}
	
	//出版社收到的所有待发货订单
	@GetMapping("/orders/publisher/post")
	public String order_publisher_post(Model model) throws Exception {	   
		Collection<Order> order = orderservice.get_order_publisher_newpost(controller.customer_id);
		model.addAttribute("order", order); 
		return "publisher/post_list";	
	}
	
	//出版社收到的所有待发货订单
	@GetMapping("/orders/publisher/post/{order_id}")
	public String order_publisher_post(@PathVariable("order_id") String order_id , Model model) throws Exception {	   
		Order order = orderservice.get_order_byid(order_id);
		Books book = booksservice.booksdetails_one(order.getBook_id());
		model.addAttribute("order", order); 
		model.addAttribute("book", book); 
		return "publisher/post";	
	}
	
	@PostMapping("/orders/publisher/posting")
    public String pushlisher_post(Order order1) throws Exception{
 			orderservice.postorder(controller.customer_id,order1.getOrder_id());
			return "redirect:/orders/publisher/post";
	}
	
	//出版社收到的所有完成订单
	@GetMapping("/orders/publisher/finish")
	public String order_publisher_finish(Model model) throws Exception {	   
		Collection<Order> order = orderservice.get_order_publisher_finish(controller.customer_id);
		model.addAttribute("order", order); 
		return "publisher/finish_list";	
	}	
		
	//出版社收到的所有待批准退货订单
	@GetMapping("/orders/publisher/return")
	public String order_publisher_return(Model model) throws Exception {	   
		Collection<Order> order = orderservice.get_order_publisher_newreturn(controller.customer_id);
		model.addAttribute("order", order); 
		return "publisher/return_list";	
	}
	
	
	//出版社退货
	@GetMapping("/orders/publisher/return/{order_id}")
	public String topushlisher_return(@PathVariable("order_id") String order_id , Model model) throws Exception {
		Order order = orderservice.get_order_byid(order_id);
		Books book = booksservice.booksdetails_one(order.getBook_id());
		model.addAttribute("order", order); 
		model.addAttribute("book", book); 
		return "publisher/return";
	}
	
	@PostMapping("/orders/publisher/returning")
    public String pushlisher_return(Order order1) throws Exception{
 			orderservice.returnorder_publisher(controller.customer_id,order1.getOrder_id());
			return "redirect:/orders/publisher/return";
	}
	
//	@GetMapping("/orders/publisher/all")
//	public List<Order> order_publisher_all(@RequestParam(value = "id", defaultValue = "0") String id,
//			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
//		    Manager manager = managerservice.if_manager(id, password);
//		    if(manager.getId().equals("-1"))
//		    {
//		    	return null;
//		    }
//		    else if(manager.getId().equals("-2"))
//		    {
//		    	return null;
//		    }
//		    else
//		    {
//		    	return orderservice.get_order_publisher(id);
//		    }		   
//	}
	
	
//	@GetMapping("/orders/publisher/post_orders")
//	public List<Order> order_publisher_post(@RequestParam(value = "id", defaultValue = "0") String id,
//			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
//		    Manager manager = managerservice.if_manager(id, password);
//		    if(manager.getId().equals("-1"))
//		    {
//		    	return null;
//		    }
//		    else if(manager.getId().equals("-2"))
//		    {
//		    	return null;
//		    }
//		    else
//		    {
//		    	return orderservice.get_order_publisher_newpost(id);
//		    }		   
//	}
	
//	@GetMapping("/orders/publisher/finished_orders")
//	public List<Order> order_publisher_finish(@RequestParam(value = "id", defaultValue = "0") String id,
//			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
//		    Manager manager = managerservice.if_manager(id, password);
//		    if(manager.getId().equals("-1"))
//		    {
//		    	return null;
//		    }
//		    else if(manager.getId().equals("-2"))
//		    {
//		    	return null;
//		    }
//		    else
//		    {
//		    	return orderservice.get_order_publisher_finish(id);
//		    }		   
//	}
	
		
	@PutMapping("/orders/publisher/posting")
    public String updateorder_post(@RequestBody Order order1, @RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception{
    	Manager manager = managerservice.if_manager(id, password);
    	
    	if(manager.getId().equals("-1") || manager.getId().equals("-2"))
    	{
    		return "身份认证失败！";
    	}
    	else if(manager.getType().equals("M"))
    	{
    		return "抱歉，您无权限对该订单进行操作，只有图书对应出版社的账号才可执行该操作~";
    	}
    	Order order = orderservice.postorder(id , order1.getOrder_id());
    	if(order.getBook_id().equals("-1"))
    	{
    		return "抱歉，您所要修改的图书ID不存在~";
    	}
    	else if(order.getBook_id().equals("-2"))
    	{
    		return "抱歉，未查到此订单~";
    	}
    	else if(order.getBook_id().equals("-3"))
    	{
    		return "抱歉，您无权限对该订单进行操作，只有图书对应出版社的账号才可执行该操作~";
    	}
    	else if(order.getBook_id().equals("-4"))
    	{
    		return "此订单已经发货啦，无需重复操作~";
    	}
    	else
    	{
    		return "订单已经发货~\n" + "订单id为：" + order.getOrder_id() + "\n用户id为：" + order.getCustomer_id() + "\n图书id为：" + order.getBook_id() + "\n图书数量为：" + order.getNumber();
    	}
    }
    
    @PutMapping("/orders/customer/identifing")
    public String updateorder_identify(@RequestBody Order order1, @RequestParam(value = "id", defaultValue = "0") int customer_id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception{
    		Customer customer = customerservice.customerdetails(customer_id,password);
    		Order order = orderservice.identifyorder(order1.getOrder_id());
	    	if(customer.getId() == -2)
		    {
		    	return "账号输入有误!";
		    }
		    else if(customer.getId() == -1)
		    {
		    	return "账号密码不匹配!";
		    }
		    else if(order.getBook_id().equals("-1"))
		    {
		    	return "抱歉，查无此订单，请检查订货单号是否正确~";
		    }
		    else if(order.getBook_id().equals("-2"))
		    {
		    	return "抱歉，您的该订单还未发货~";
		    }
		    else if(order.getBook_id().equals("-3"))
		    {
		    	return "抱歉，您已经确认过您的该订单，无需重复确认~";
		    }
	    	Books book = booksservice.booksdetails_one(order.getBook_id());
	    	return "确认订单成功!" + "\n用户id为：" + customer_id 
	    			+ "\n图书id为：" + order.getBook_id() 
	    			+ "\n图书数量为：" + order.getNumber()
	    			+ "\n图书出版社为：" + book.getPublisher();
	    }
    
//    @PutMapping("/orders/customer/returning")
//    public String updateorder_return_customer(@RequestBody Order order1, @RequestParam(value = "id", defaultValue = "0") int customer_id,
//			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception{
//    		Customer customer = customerservice.customerdetails(customer_id,password);
//    		Order order = orderservice.returnorder(order1.getOrder_id());
//	    	if(customer.getId() == -2)
//		    {
//		    	return "账号输入有误!";
//		    }
//		    else if(customer.getId() == -1)
//		    {
//		    	return "账号密码不匹配!";
//		    }
//		    else if(order.getBook_id().equals("-1"))
//		    {
//		    	return "抱歉，查无此订单，请检查订货单号是否正确~";
//		    }
//		    else if(order.getBook_id().equals("-2"))
//		    {
//		    	return "抱歉，您的该订单还未发货~";
//		    }
//		    else if(order.getBook_id().equals("-3"))
//		    {
//		    	return "抱歉，你已确认收货，无法进行退货~";
//		    }
//		    else if(order.getBook_id().equals("-5"))
//		    {
//		    	return "你已提交退货申请，无需重复申请，请耐心等待~";
//		    }
//		    else if(order.getBook_id().equals("-6"))
//		    {
//		    	return "您的退货申请已经批准，无需再进行申请~";
//		    }
//	    	Books book = booksservice.booksdetails_one(order.getBook_id());
//	    	return "提交退货申请成功!" + "\n用户id为：" + customer_id 
//	    			+ "\n图书id为：" + order.getBook_id() 
//	    			+ "\n图书数量为：" + order.getNumber()
//	    			+ "\n图书出版社为：" + book.getPublisher();
//	    }
//    
    @PutMapping("/orders/publisher/returning")
    public String updateorder_return_publisher(@RequestBody Order order1, @RequestParam(value = "id", defaultValue = "0") String id,
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
    	Order order = orderservice.returnorder_publisher(id , order1.getOrder_id());
    	if(order.getBook_id().equals("-1"))
    	{
    		return "抱歉，无此ID订单存在~";
    	}
    	else if(order.getBook_id().equals("-2"))
    	{
    		return "抱歉，您无权限对该订单进行操作~";
    	}
    	else if(order.getBook_id().equals("-3"))
    	{
    		return "抱歉，该订单已确认收货，无法进行退货~";
    	}
    	else if(order.getBook_id().equals("-5"))
    	{
    		return "你已完成该订单的退货处理，无需重复操作~";
    	}
    	else if(order.getBook_id().equals("-4"))
    	{
    		return "抱歉，未收到来自该订单的退货请求~";
    	}
	    	return "批准退货申请成功!" + "\n订单id为：" + order1.getOrder_id()
	    			+ "\n图书id为：" + order.getBook_id() 
	    			+ "\n图书数量为：" + order.getNumber()
	    			+ "\n图书出版社为：" + id;
	    }

}
