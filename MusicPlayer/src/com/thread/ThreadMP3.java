package com.thread;

import java.io.FileInputStream;

import javazoom.jl.player.Player;

import com.dto.MusicaDTO;

public class ThreadMP3 extends GenericThread {
	
	private MusicaDTO music;	
	
	public ThreadMP3(MusicaDTO music) {
		this.music = music;
	}
	
	@Override
	public void run() {
		try {			
			new Player(new FileInputStream(this.music.getArquivo().getAbsolutePath())).play();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}