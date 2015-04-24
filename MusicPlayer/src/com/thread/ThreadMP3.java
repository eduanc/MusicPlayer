package com.thread;

import java.io.FileInputStream;

import javazoom.jl.player.Player;

import com.dto.MusicDTO;

public class ThreadMP3 extends GenericThread {
	
	private MusicDTO music;	
	
	public ThreadMP3(MusicDTO music) {
		this.music = music;
	}
	
	@Override
	public void run() {
		try {			
			new Player(new FileInputStream(this.music.getFile().getAbsolutePath())).play();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}