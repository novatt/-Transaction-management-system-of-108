package com.example.demo.ORDER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.BOOKS.Books;
import com.example.demo.BOOKS.Booksdao;
import com.example.demo.CUSTOMER.Customerdao;
import com.example.demo.MANAGER.Manager;
import com.example.demo.MANAGER.Managerdao;
import com.example.demo.SHOPCAR.Shopcar;
import com.example.demo.SHOPCAR.Shopcardao;

@Service
public class Orderservice {
	
	@Autowired
	private Managerdao managerdao;
	
	@Autowired
	private Shopcardao shopcardao;
	
	@Autowired
	private Booksdao booksdao;

	@Autowired
	private Orderdao orderdao;
	
	@Autowired
	private Customerdao customerdao;
	
	public List<Order> get_order_oneday_in(String id  , String date) throws Exception {  
		return orderdao.queryorder_oneday_in(id , date);	
	}
	
	public List<Order> get_order_onemonth_in(String id  , String date) throws Exception {  
		return orderdao.queryorder_onemonth_in(id , date);	
	}
	
	public List<Order> get_order_period_in(String id  , String date_start ,String date_end) throws Exception {  
		return orderdao.queryorder_period_in(id , date_start , date_end);	
	}
	
	public List<Order> get_order(int customer_id) throws Exception {  
			
		return orderdao.queryorder(customer_id);	
	}
	
	public List<Order> get_order_publisher(String id) throws Exception {  
		
		return orderdao.queryorder_publisher(id);	
	}
	
	public List<Order> get_order_publisher_newpost(String id) throws Exception {  
		
		return orderdao.queryorder_publisher_newpost(id);	
	}
	
	public List<Order> get_order_publisher_finish(String id) throws Exception {  
		
		return orderdao.queryorder_publisher_finish(id);	
	}
	
	public List<Order> get_order_publisher_newreturn(String id) throws Exception {  
		
		return orderdao.queryorder_publisher_newreturn(id);	
	}
	
	public Order addingorder(int customer_id , Order order) {
		Shopcar shopcar1 = shopcardao.queryorderbybook_id(customer_id, order.getBook_id());
		Books book = booksdao.queryBookById(order.getBook_id());
		double score_now = customerdao.get_score(customer_id);
		Order order1 = new Order();
		if(book == null)//不存在这本书
		{
			order1.setBook_id("-1");
			System.out.print("1");
			return order1;
		}
		else if(shopcar1 == null)//无此订单
		{
			order1.setBook_id("-3");
			System.out.print("2");
	    	return order1;
		}
		else if(book.getNumber() < shopcar1.getNumber())//库存不足
	    {
			order1.setBook_id("-2");
			System.out.print("3");
	    	return order1;
	    }
		else if(score_now < order.getScore())
		{
			order1.setBook_id("-4");//所剩积分不足
			System.out.print("4");
			return order1;
		}
		else
		{		
			int count = orderdao.insertorder(customer_id, shopcar1 , order.getScore());
			System.out.print(count);
			return orderdao.queryorderbyorder_id("" + customer_id + order.getBook_id() + count);
		}
		
	}
	
	public Order identifyorder(String order_id) {
		Order order = orderdao.queryorderbyorder_id(order_id);
		Order order1 =  new Order();
		if(order == null)//无订单
		{
			order1.setBook_id("-1");
			return order1;
		}
		else if(order.getIf_post() == 0)//没发货
		{
			order1.setBook_id("-2");
			return order1;
		}
		else if(order.getIf_identify() == 1)//已经确认了
		{
			order1.setBook_id("-3");
			return order1;
		}
		else
		{
			orderdao.updateorder_identify_ById(order_id);
			return order;
		}
	}
	
	public Order returnorder(String order_id) {
		Order order = orderdao.queryorderbyorder_id(order_id);
		Order order1 =  new Order();
		if(order == null)//无订单
		{
			order1.setBook_id("-1");
			return order1;
		}
		else if(order.getIf_post() == 0)//没发货
		{
			order1.setBook_id("-2");
			return order1;
		}
		else if(order.getIf_identify() == 1)//已确认收货
		{
			order1.setBook_id("-3");
			return order1;
		}
		else if(order.getIf_return() == 1)//已经提交退货申请
		{
			order1.setBook_id("-5");
			return order1;
		}
		else if(order.getIf_return() == 2)//已经批准退货申请
		{
			order1.setBook_id("-6");
			return order1;
		}
		else
		{
			orderdao.updateorder_return_ById(order_id);
			return order;
		}
	}
	
	public Order returnorder_publisher(String id , String order_id) {
		Order order = orderdao.queryorderbyorder_id(order_id);
		Order order1 =  new Order();
		if(order == null)//无订单
		{
			order1.setBook_id("-1");
			return order1;
		}
		Books book = booksdao.queryBookById(order.getBook_id());
		if(!book.getPublisher().equals(id))//无权限
		{
			order1.setBook_id("-2");
			return order1;
		}
		else if(order.getIf_return() == 0)//没有退货请求
		{
			order1.setBook_id("-4");
			return order1;
		}
		else if(order.getIf_identify() == 1)//已确认收货
		{
			order1.setBook_id("-3");
			return order1;
		}
		else if(order.getIf_return() == 2)
		{
			order1.setBook_id("-5");
			return order1;
		}
		else
		{
			orderdao.updateorder_return_publisher_ById(order_id);
			return order;
		}
	}
	
	public Order postorder(String id , String order_id) {
		Order order1 = orderdao.queryorderbyorder_id(order_id);
		Order order2 = new Order();
		if(order1 == null)
		{
			order2.setBook_id("-2");
			return order2;//无此订单
		}
		Books book = booksdao.queryBookById(order1.getBook_id());
		if(book == null)
		{
			order2.setBook_id("-1");
			return order2;//无此书
		}
		else if(!book.getPublisher().equals(id))
		{
			order2.setBook_id("-3");
			return order2;//无权访问
		}
		else if(order1.getIf_post() == 1)
		{
			order2.setBook_id("-4");
			return order2;//已经发货了
		}
		else
		{
			orderdao.updateorder_post_ById(order_id);
			return order1;//成功修改
		}
	}
}
