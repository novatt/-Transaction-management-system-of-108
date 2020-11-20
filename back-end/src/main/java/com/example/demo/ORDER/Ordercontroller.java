package com.example.demo.ORDER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Period;
import com.example.demo.BOOKS.Books;
import com.example.demo.BOOKS.Booksservice;
import com.example.demo.CUSTOMER.Customer;
import com.example.demo.CUSTOMER.Customerservice;
import com.example.demo.MANAGER.Manager;
import com.example.demo.MANAGER.Managerservice;
import com.example.demo.SHOPCAR.Shopcar;

@RestController
public class Ordercontroller {
	@Autowired
    private Booksservice booksservice;
	
	@Autowired
	private Orderservice orderservice;
	
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
	
	@GetMapping("/orders/customer/all")
	public List<Order> order_customer_all(@RequestParam(value = "id", defaultValue = "0") int id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
		    Customer customer = customerservice.customerdetails(id,password);
		    if(customer.getId() == -2)
		    {
		    	return null;
		    }
		    else if(customer.getId() == -1)
		    {
		    	return null;
		    }
		    else
		    {
		    	return orderservice.get_order(id);
		    }		   
	}
	
	@GetMapping("/orders/publisher/all")
	public List<Order> order_publisher_all(@RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
		    Manager manager = managerservice.if_manager(id, password);
		    if(manager.getId().equals("-1"))
		    {
		    	return null;
		    }
		    else if(manager.getId().equals("-2"))
		    {
		    	return null;
		    }
		    else
		    {
		    	return orderservice.get_order_publisher(id);
		    }		   
	}
	
	@GetMapping("/orders/publisher/post_orders")
	public List<Order> order_publisher_post(@RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
		    Manager manager = managerservice.if_manager(id, password);
		    if(manager.getId().equals("-1"))
		    {
		    	return null;
		    }
		    else if(manager.getId().equals("-2"))
		    {
		    	return null;
		    }
		    else
		    {
		    	return orderservice.get_order_publisher_newpost(id);
		    }		   
	}
	
	@GetMapping("/orders/publisher/finished_orders")
	public List<Order> order_publisher_finish(@RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
		    Manager manager = managerservice.if_manager(id, password);
		    if(manager.getId().equals("-1"))
		    {
		    	return null;
		    }
		    else if(manager.getId().equals("-2"))
		    {
		    	return null;
		    }
		    else
		    {
		    	return orderservice.get_order_publisher_finish(id);
		    }		   
	}
	
	@GetMapping("/orders/publisher/return_orders")
	public List<Order> order_publisher_return(@RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
		    Manager manager = managerservice.if_manager(id, password);
		    if(manager.getId().equals("-1"))
		    {
		    	return null;
		    }
		    else if(manager.getId().equals("-2"))
		    {
		    	return null;
		    }
		    else
		    {
		    	return orderservice.get_order_publisher_newreturn(id);
		    }		   
	}
	
	@PostMapping("/orders/adding")
    public String addorder(@RequestBody Order order1 , @RequestParam(value = "id", defaultValue = "0") int customer_id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
			Customer customer = customerservice.customerdetails(customer_id,password);			
		    if(customer.getId() == -2)
		    {
		    	return "账号输入有误，付款失败~";
		    }
		    else if(customer.getId() == -1)
		    {
		    	return "账号密码不匹配，付款失败~";
		    }	
		    if(customer.getAddress() == null)
		    {
		    	return "抱歉，您未填写您的收货地址，无法完成订单~";
		    }
		    Order order = orderservice.addingorder(customer_id, order1);
		    if(order.getBook_id().equals("-3"))
		    {
		    	return "购物车中无此订单，付款失败~";
		    }
		    else if(order.getBook_id().equals("-4"))
		    {
		    	return "您的积分不足，付款失败~";
		    }
		    else if(order.getBook_id().equals("-1"))
		    {
		    	return "抱歉，您所要购买的图书不存在~";
		    }
		    else if(order.getBook_id().equals("-2"))
		    {
		    	return "抱歉，该图书库存不足，请修改购物车中订单~";
		    }
		    
		    Books book = booksservice.booksdetails_one(order.getBook_id());
			return "订单支付成功！\n用户ID：" + customer_id +
					"\n书目名字：" + book.getName() + 
					"\n购买数量：" + order.getNumber() +
					"\n书目单价：" + book.getPrice() +
					"\n书目折扣前总价：" + order.getMoney_first() + 
					"\n书目折扣后总价：" + order.getMoney_last() + 
					"\n书目折扣情况：" + book.getDiscount() +
					"\n书目类型：" + book.getType() + 
					"\n书目出版社：" + book.getPublisher();
	}
	
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
    
    @PutMapping("/orders/customer/returning")
    public String updateorder_return_customer(@RequestBody Order order1, @RequestParam(value = "id", defaultValue = "0") int customer_id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception{
    		Customer customer = customerservice.customerdetails(customer_id,password);
    		Order order = orderservice.returnorder(order1.getOrder_id());
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
		    	return "抱歉，你已确认收货，无法进行退货~";
		    }
		    else if(order.getBook_id().equals("-5"))
		    {
		    	return "你已提交退货申请，无需重复申请，请耐心等待~";
		    }
		    else if(order.getBook_id().equals("-6"))
		    {
		    	return "您的退货申请已经批准，无需再进行申请~";
		    }
	    	Books book = booksservice.booksdetails_one(order.getBook_id());
	    	return "提交退货申请成功!" + "\n用户id为：" + customer_id 
	    			+ "\n图书id为：" + order.getBook_id() 
	    			+ "\n图书数量为：" + order.getNumber()
	    			+ "\n图书出版社为：" + book.getPublisher();
	    }
    
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
