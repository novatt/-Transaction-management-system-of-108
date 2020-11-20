package com.example.demo.SHOPCAR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BOOKS.Books;
import com.example.demo.CUSTOMER.Customer;
import com.example.demo.CUSTOMER.Customerservice;

@RestController
public class Shopcarcontroller {

	@Autowired
    private Shopcarservice shopcarservice;
	
	@Autowired
	private Customerservice customerservice;
	
	@GetMapping("/shopping_cart")
	public List<Shopcar> shopcar(@RequestParam(value = "id", defaultValue = "0") int id,
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
		    	return shopcarservice.get_order(id);
		    }
		   
	}
	
	@PostMapping("/shopping_cart/adding")
    public String addshopping_cart(@RequestBody Shopcar shopcar , @RequestParam(value = "id", defaultValue = "0") int id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
			Customer customer = customerservice.customerdetails(id,password);
		    if(customer.getId() == -2)
		    {
		    	return "账号输入有误,加入购物车失败！";
		    }
		    else if(customer.getId() == -1)
		    {
		    	return "账号密码不匹配，加入购物车失败！";
		    }
		    Books book = shopcarservice.addingorder(id, shopcar);
		    if(book == null)
		    {
		    	return "抱歉，购物车中已有包含该ID的图书存在~";
		    }
		    else if(book.getId().equals("-2"))
		    {
		    	return "抱歉，该图书库存不足~";
		    }
		    else if(book.getId().equals("-1"))
		    {
		    	return "抱歉，您所要添加的图书不存在~";
		    }
			return "加入购物车成功！\n用户ID：" + id +
					"\n书目名字：" + book.getName() + 
					"\n选购数量：" + shopcar.getNumber() +
					"\n书目单价：" + book.getPrice() +
					"\n书目折扣情况：" + book.getDiscount() +
					"\n书目类型：" + book.getType() + 
					"\n书目出版社：" + book.getPublisher();
    }
	
	
	@PutMapping("/shopping_cart/changing")
    public String updateshopcar(@RequestBody Shopcar shopcar, @RequestParam(value = "id", defaultValue = "0") int id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception{
	 		Customer customer = customerservice.customerdetails(id,password);
	 		Books book = shopcarservice.changingorder(id, shopcar);
		    if(customer.getId() == -2)
		    {
		    	return "账号输入有误,管理购物车失败！";
		    }
		    else if(customer.getId() == -1)
		    {
		    	return "账号密码不匹配，管理购物车失败！";
		    }
		    else if(book == null)
		    {
		    	return "抱歉，购物车中无此订单~";
		    }
		    else if(book.getId().equals("-2"))
		    {
		    	return "抱歉，该图书库存不足，修改失败~";
		    }
		    else if(book.getId().equals("-1"))
		    {
		    	return "抱歉，您所要修改的图书ID不存在~";
		    }
		    return "购物车管理成功!\n修改后的订单后：\n" + "\n用户id为：" + id 
	    			+ "\n书目名字：" + book.getName() + 
	    			"\n选购数量：" + shopcar.getNumber() +
	    			"\n书目单价：" + book.getPrice() +
	    			"\n书目折扣情况：" + book.getDiscount() ;
		    }
	
	@DeleteMapping("/shopping_cart/removal")
    public String deleteorder(@RequestBody Shopcar shopcar , @RequestParam(value = "id", defaultValue = "0") int id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
			Customer customer = customerservice.customerdetails(id,password);			
	    	if(customer.getId() == -2)
		    {
		    	return "账号输入有误，购物车删除订单失败!";
		    }
		    else if(customer.getId() == -1)
		    {
		    	return "账号密码不匹配，购物车删除订单失败!";
		    }
	    	shopcarservice.removeorder(id, shopcar.getBook_id());
	    	return "购物车删除订单成功!" + "\n用户id为：" + id ;
	    }
}
