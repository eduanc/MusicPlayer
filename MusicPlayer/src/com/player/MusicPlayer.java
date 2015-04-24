package com.player;

import com.dto.MusicDTO;

public interface MusicPlayer {
	
	public void play(int begin, MusicDTO musica);
	
	public void pause();
	
	public void stop();
	
	public void change(MusicDTO musica);
		
}