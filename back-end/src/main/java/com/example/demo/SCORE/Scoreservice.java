package com.example.demo.SCORE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Scoreservice {

	@Autowired
	private Scoredao scoredao;
	
	public List<Score> get_order(int customer_id) throws Exception {  
		
		return scoredao.queryorder(customer_id);	
	}   
}
