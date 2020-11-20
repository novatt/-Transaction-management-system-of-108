package com.example.demo.MANAGER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class Managercontroller {

	@Autowired
	private Managerservice managerservice;
	
	@PostMapping("/publisher/register")
    public String addpublisher(@RequestBody Manager manager1) throws Exception { 
		Manager manager = managerservice.insertpublisher(manager1.getId(), manager1.getPassword());
		if(manager.getId().equals("-1"))
		{
			return "该出版社账号已经存在或已申请，请核查~";
		}
		else if(manager.getId().equals("-2"))
		{
			return "您未输入ID,申请账号失败~";
		}
		else if(manager.getId().equals("-3"))
		{
			return "您未输入密码，申请账号失败~";
		}
		return "出版社账号申请成功，请等待审核通过~";
	}
	
	 @PutMapping("/manager/identifing")
	    public String publisher_identify(@RequestBody Manager manager1, @RequestParam(value = "id", defaultValue = "0") String id,
				@RequestParam(value = "password", defaultValue = "0") String password) throws Exception{
		 Manager manager = managerservice.publisher_identify(id, password, manager1);
		 if(manager.getId().equals("-1"))
		 {
			 return "抱歉，管理员身份验证失败~";
		 }
		 else if(manager.getId().equals("-2"))
		 {
			 return "抱歉，未查询到该账号的申请信息~";
		 }
		 else if(manager.getId().equals("-3"))
		 {
			 return "抱歉，该账号无需认证~";
		 }
		 else if(manager.getId().equals("-4"))
		 {
			 return "抱歉，该账号已完成认证，无需重复操作~";
		 }
		 else
		 {
			 return "认证成功！\nID为：" + manager1.getId() + " 的出版社账号可以正常使用~";
		 }
	 }
}
