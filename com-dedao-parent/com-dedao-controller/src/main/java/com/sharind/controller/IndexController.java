package com.sharind.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/index")
public class IndexController extends Object{


	
	@RequestMapping("/index")
	public String index()
	{
		return "index/index";
	}
		
	@RequestMapping("/index2")
	@ResponseBody
	public Map<String, Object> index2()
	{
		Map<String, Object> map = new HashMap<>();
		map.put("dasjkd", "dahjdjka");
		return map;
	}
	
	//MusicController musicController;
	//MusicController sObject;
//	@RequestMapping("/music")
//	public void music(@RequestParam Integer status) throws Exception
//	{
//		
//		if (status==1) {
//			String pathname = "E:\\kugou\\a.mp3";
//			File file = new File(pathname);
//			MusicController musicController = new MusicController(file);
//			musicController.xuanze(status);
//			sObject = musicController;
//		}
//		if(status == 2)
//		{
//			sObject.xuanze(status);
//		}
//		
//	}
	@RequestMapping("/music")
	public void music(@RequestParam String  status) throws Exception
	{
		
			String pathname = "E:\\kugou\\a.mp3";
			
			 new MusicController().xuanze(status,pathname);
			
		
	}
	
	@RequestMapping("/close")
	public void  close()
	{
		MusicController.close();
	}

}
