package com.example.demo.CUSTOMER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class Customerservice {

	@Autowired
	private Customerdao customerdao;
	
	public Customer customerdetails(int id , String password) throws Exception {
		Customer customer1 = customerdao.queryCustomerById(id);
		Customer customer_return = new Customer();
		
		if(customer1 == null)
		{
			customer_return.setId(-2);
			return customer_return;
		}
		else if(customer1.getPassword().equals(password))
		{
			return customer1;
		}
		else
		{
			customer_return.setId(-1);
			return customer_return;
		}
	}
	
	public Customer query(int id) throws Exception {
		Customer customer1 = customerdao.queryCustomerById(id);
		return customer1;		
	}
	
	public Customer addcustomer(Customer customer) throws Exception {
		Customer customer1 = customerdao.queryCustomerById(customer.getId());
		Customer customer2 = new Customer();
		customer2.setId(-2);
//		Customer customer3 = new Customer();
		if(customer1 != null)
		{
			return customer2;
		}
		return customerdao.addcustomer(customer);
	}
	
	public Customer updateCustomer_password_ById(Customer customer , int id ) throws Exception {
		Customer customer1 = customerdao.queryCustomerById(id);
		Customer customer2 = new Customer();
		customer2.setId(-2);
		Customer customer3 = new Customer();
		if(customer1 == null)
		{
			return customer2;
		}
//		else if(!customer1.getPassword().equals(password))
//		{
//			return customer3;
//		}
		return customerdao.updateCustomer_password_ById(id  , customer);
		
	}
	
	public Customer updateCustomer_address_ById(Customer customer , int id) throws Exception {
		Customer customer1 = customerdao.queryCustomerById(id);
		Customer customer2 = new Customer();
		customer2.setId(-2);
		Customer customer3 = new Customer();
		if(customer1 == null)
		{
			return customer2;
		}
		else if(customer.getAddress() == null)
		{
			customer2.setId(-3);
			return customer2;//没填地址
		}
		return customerdao.updateCustomer_address_ById(id ,  customer);
		
	}
}
