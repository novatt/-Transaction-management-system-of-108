package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class infoservice {

	@Autowired
	private infodao info;	
		
	public List<info> infos_publisher(String publisher) {
		return info.queryinfosByPublisher(publisher);
	}
	
	public List<info> infos_all() {
		return info.queryAllinfos();
	}
	
	public void infos_insert(info infos) {
		info.insertInfo(infos);
	}
	
}
