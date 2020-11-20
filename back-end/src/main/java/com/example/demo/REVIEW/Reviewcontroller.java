package com.example.demo.REVIEW;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.CUSTOMER.Customer;
import com.example.demo.CUSTOMER.Customerservice;


@RestController
public class Reviewcontroller {
	
	@Autowired
	private Customerservice customerservice;
	
	@Autowired
	private Reviewservice reviewservice;
	
	@GetMapping("/reviews/by_book_id")
	public List<Review> getBook(@RequestParam(value = "id", defaultValue = "0") String book_id) throws Exception {
        return reviewservice.get_review(book_id);
    }
	
	@PostMapping("/reviews/adding")
    public String addshopping_cart(@RequestBody Review review1 , @RequestParam(value = "id", defaultValue = "0") int customer_id,
			@RequestParam(value = "password", defaultValue = "0") String password) throws Exception {
			Customer customer = customerservice.customerdetails(customer_id,password);
		    if(customer.getId() == -2)
		    {
		    	return "账号输入有误，评论商品失败！";
		    }
		    else if(customer.getId() == -1)
		    {
		    	return "账号密码不匹配，评论商品失败！";
		    }
		    if(review1.getReview_star() < 0 || review1.getReview_star() > 5)
		    {
		    	return "抱歉，评分的区间为0~5！请输入合理的评分~";
		    }
		    Review review = reviewservice.addreview(customer_id, review1);
		    if(review.getBook_id().equals("-1") || review.getBook_id().equals("-3"))
		    {
		    	return "抱歉，您的账户中无匹配的订单~";
		    }
		    
		    else if(review.getBook_id().equals("-2"))
		    {
		    	return "抱歉，你对于此订单已经评论过了，不可重复评论~";
		    }
		    else if(review.getBook_id().equals("-3"))
		    {
		    	return "抱歉，您未给出星级的评价~";
		    }
		    BigDecimal b = new BigDecimal(review.getReview_star());
			return "评论成功！\n用户ID：" + customer_id +
					"\n订单ID:" + review.getOrder_id() + 
					"\n图书ID：" + review.getBook_id() +
					"\n书目出版社：" + review.getPublisher() + 
					"\n评价星级：" + b.setScale(1, BigDecimal.ROUND_HALF_UP) + 
					"\n评论具体内容：" + review.getBody();
    }

}
