package com.example.demo.MANAGER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Managerservice {

	@Autowired
	private Managerdao managerdao;
	
	public Manager if_manager(String id , String password) {
		Manager manager = managerdao.queryManagerById(id);
		Manager manager1 = new Manager();
		if(manager == null)
		{
			manager1.setId("-1");
			return manager1;//不存在该管理员或者出版社
		}
		else if(!manager.getPassword().equals(password))
		{
			manager1.setId("-2");
			return manager1;//账号密码不匹配
		}
		else
		{
			return manager;
		}
	}
	
	public Manager insertpublisher(String id , String password) {
		Manager manager = new Manager();
		if(id == null)
		{
			manager.setId("-2");//未输入id
			return manager;
		}
		Manager manager1 = managerdao.queryManagerById(id);
		if(password == null)
		{
			manager.setId("-3");//未输入密码
			return manager;
		}
		else if(manager1 != null)
		{
			manager.setId("-1");//账号已经存在
			return manager;
		}
		else
		{
			managerdao.insertpublisher(id, password);
			return managerdao.queryManagerById(id);
		}		
	}
	
	public Manager publisher_identify(String id , String password , Manager manager) {
		Manager manager1 = managerdao.queryManagerById(id);
		Manager manager2 = managerdao.queryManagerById(manager.getId());
		Manager manager_return = new Manager();
		if(manager1 == null)
		{
			manager_return.setId("-1");
			return manager_return;//没找到号
		}
		else if(!manager1.getPassword().equals(password))
		{
			manager_return.setId("-1");
			return manager_return;//密码不对
		}
		else if(!manager1.getType().equals("M"))
		{
			manager_return.setId("-1");
			return manager_return;//权限不足
		}
		else if(manager2 == null)
		{
			manager_return.setId("-2");
			return manager_return;//没查到申请
		}
		else if(!manager2.getType().equals("P"))
		{
			manager_return.setId("-3");
			return manager_return;//这个号是个管理员
		}
		else if(manager2.getIf_identify() == 1)
		{
			manager_return.setId("-4");
			return manager_return;//已经完成认证
		}
		else
		{
			managerdao.publisher_identify(manager.getId());
			return manager;
		}
	}
}
