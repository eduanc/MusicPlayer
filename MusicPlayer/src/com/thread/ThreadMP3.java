package com.thread;

import java.io.FileInputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;

import com.dto.MusicDTO;
import com.player.PlayerManager;

public class ThreadMP3 extends GenericThread {
	
	private MusicDTO music;
	private int begin;
	
	public ThreadMP3(int begin, MusicDTO music) {
		this.music = music;
		this.begin = begin;
	}
	
	@Override
	public void run() {
		try {
			new AdvancedPlayer(new FileInputStream(this.music.getFile().getAbsolutePath())).play(begin, this.music.getDuration());
		
			new PlayerManager().askForNext();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}