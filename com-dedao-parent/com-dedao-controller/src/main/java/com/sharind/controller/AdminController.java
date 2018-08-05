package com.sharind.controller;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping("/index")
	public void index() throws Exception
	{
		File file = new File("E:\\kugou\\薛之谦 - 丑八怪.mp3");
		@SuppressWarnings("deprecation")
		URL url = file.toURL();
		AudioClip aClip = Applet.newAudioClip(url);
		aClip.play();
		
	}
}
