package com.sharind.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Value;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicController {
	Player player;
	File music;
	
	
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public File getMusic() {
		return music;
	}

	public void setMusic(File music) {
		this.music = music;
	}

//	public MusicController(File file)
//	{
//		this.music = file;
//	}
	
	public void xuanze(String status,String music) throws FileNotFoundException, JavaLayerException
	{
		BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(music));
		player = new Player(buffer);
		if(status.equals("1"))
		{
			
			player.play();
		}
		if (status.equals("2")) {
			player.close();
			
		}
			
	}
	
	public static void close()
	{
		player.close();
	}
	
}
