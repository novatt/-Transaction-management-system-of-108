package com.example.demo.SHOPCAR;

import java.util.Collection;
import java.util.List;

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

import com.example.demo.controller;
import com.example.demo.BOOKS.Books;
import com.example.demo.BOOKS.Booksservice;
import com.example.demo.CUSTOMER.Customer;
import com.example.demo.CUSTOMER.Customerservice;

@Controller
public class Shopcarcontroller{

	@Autowired
    private Shopcarservice shopcarservice;
	
	@Autowired
	private Customerservice customerservice;
	
	@Autowired
	private Booksservice booksservice;
	
	
	@GetMapping("/shopping_cart")
	public String shopcar(Model model) throws Exception {
		    	
	    int change_id = Integer.parseInt(controller.customer_id);	
	    Collection<Shopcar> shopcar = shopcarservice.get_order(change_id);
//	    Book book = booksservice.booksdetails_one();
    	model.addAttribute("shopcar", shopcar); 
//    	model.addAttribute("book",book);
    	return "shopcar/list";	   
	}
	
	
	@GetMapping("/shopping_cart/adding")
	public String toAddshopping_cart(Model model) {
		Collection<Books> book = booksservice.booksdetails_all();
		model.addAttribute("book", book);
		return "shopcar/add";
	}
	
	@PostMapping("/shopping_cart/adding")
    public String addshopping_cart(Shopcar shopcar) throws Exception {
			int change_id = Integer.parseInt(controller.customer_id);
			shopcarservice.addingorder(change_id, shopcar);
			return "redirect:";
    }
	
	@GetMapping("/shopping_cart/{book_id}")
	public String toUpdateshopping_cart(@PathVariable("book_id") String book_id , Model model) throws Exception {
		int change_id = Integer.parseInt(controller.customer_id);
		Shopcar shopcar = shopcarservice.get_one_order(change_id, book_id);
		Books book = shopcarservice.changingorder(change_id, shopcar);
		model.addAttribute("shopcar", shopcar);
		model.addAttribute("book", book);
		return "shopcar/update";
	}
	
	@PostMapping("/shopping_cart/changing")
    public String updateshopcar(Shopcar shopcar) throws Exception{
 			int change_id = Integer.parseInt(controller.customer_id);
	 		shopcarservice.changingorder(change_id, shopcar);
			return "redirect:";
	}
	
	
	@GetMapping("/shopping_cart/removal/{book_id}")
    public String deleteorder(@PathVariable("book_id") String book_id , Model model) throws Exception {
			int change_id = Integer.parseInt(controller.customer_id);		
	    	shopcarservice.removeorder(change_id,book_id);
	    	return "redirect:/shopping_cart";
	    }
}
