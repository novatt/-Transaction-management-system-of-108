package com.example.demo.SCORE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.CUSTOMER.Customer;
import com.example.demo.CUSTOMER.Customerservice;
import com.example.demo.SHOPCAR.Shopcar;

@RestController
public class Scorecontroller {

	@Autowired
    private Scoreservice scoreservice;
	
	@Autowired
    private Customerservice customerservice;
	
//	@GetMapping("/score_list")
//	public List<Score> score_list(@RequestParam(value = "id", defaultValue = "0") int id,
//			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
//		    Customer customer = customerservice.customerdetails(id,password);
//		    if(customer.getId() == -2)
//		    {
//		    	return null;
//		    }
//		    else if(customer.getId() == -1)
//		    {
//		    	return null;
//		    }
//		    else
//		    {
//		    	return scoreservice.get_order(id);
//		    }
//		   
//	}
//	
	@GetMapping("/accounts/score_list")
	public List<Score> score_list_byid(@RequestParam(value = "id", defaultValue = "0") int id,
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
		    	return scoreservice.get_order(id);
		    }
		   
	}
	
}
