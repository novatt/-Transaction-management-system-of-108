package com.example.demo;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

//	private static final String template = "Hello, %s!";
//	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/hello")
	public String greeting(@RequestParam("username") String username ,@RequestParam("password") String password
			,Model model) {
		if(StringUtils.isEmpty(username) && password.equals("1"))
		{
			return "dashboard";
		}
		else
		{
			model.addAttribute("msg", "错误");
			return "index";
		}
	}
}