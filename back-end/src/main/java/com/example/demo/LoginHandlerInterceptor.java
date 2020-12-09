package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginHandlerInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request , HttpServletResponse response , Object handler) throws Exception{
		
		Object loginUser = request.getSession().getAttribute("id");
		
		if(loginUser == null)
		{
			request.setAttribute("msg", "您好，请先登入");
			request.getRequestDispatcher("/accounts/login").forward(request,response);
			return false;
		}
		else
		{
			return true;
		}
	
	}
}
