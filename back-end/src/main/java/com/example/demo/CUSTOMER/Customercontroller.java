package com.example.demo.CUSTOMER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Customercontroller {

	@Autowired
	private Customerservice customerservice;
	
	@GetMapping("/accounts/customer_details")
	public String Customer_details(@RequestParam(value = "id", defaultValue = "0") int id,
			@RequestParam(value = "password", defaultValue = "0") String password,Model model) throws Exception
	{
		Customer customer = customerservice.customerdetails(id,password);
		model.addAttribute("msg" , "用户id为：" + customer.getId() + "   \n用户当前积分为：" + customer.getScore() + "   \n用户的地址为：" + customer.getAddress());
    	return "Customer_details";
	}
	
	@PostMapping("/accounts/register")
	public String customer_register(@RequestBody Customer customer) throws Exception {
		Customer customer1 = customerservice.addcustomer(customer);
		if(customer1.getId() == -2)
		{
			return "注册失败，账号已经被使用，请重新输入！";
		}
		
		return "注册成功！" + "\n用户id为：" + customer.getId() + "\n用户密码为：" + customer.getPassword() + "\n用户当前积分为：" + customer.getScore() + "\n用户的地址为：" + customer.getAddress();
    }
	
	@PutMapping("/accounts/changing/password")
    public String updateCustomer_possword(@RequestBody Customer customer, @RequestParam(value = "id", defaultValue = "0") int id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception{
	    	Customer customer1 = customerservice.updateCustomer_password_ById(customer,id,password);
	    	if(customer1.getId() == -2)
		    {
		    	return "账号输入有误!";
		    }
		    else if(customer1.getId() == -1)
		    {
		    	return "账号密码不匹配!";
		    }
	    	return "修改密码成功!" + "\n用户id为：" + customer1.getId() + "\n用户新密码为：" + customer1.getPassword() + "\n用户当前积分为：" + customer1.getScore() + "\n用户的地址为：" + customer1.getAddress()
	    	+ "\n请妥善保管好您的新密码信息！";
	    }
	
	@PutMapping("/accounts/changing/address")
    public String updateCustomer_addresss(@RequestBody Customer customer, @RequestParam(value = "id", defaultValue = "0") int id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception{
	    	Customer customer1 = customerservice.updateCustomer_address_ById(customer,id,password);
	    	if(customer1.getId() == -2)
		    {
		    	return "账号输入有误!";
		    }
		    else if(customer1.getId() == -1)
		    {
		    	return "账号密码不匹配!";
		    }
		    else if(customer1.getId() == -3)
		    {
		    	return "请输入你要修改为的地址!";
		    }
	    	return "修改收货地址成功!" + "\n用户id为：" + customer1.getId() + "\n用户的新收货地址为：" + customer1.getAddress();
	    }
}
