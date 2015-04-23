package com.player;

import com.dto.MusicaDTO;

public interface MusicPlayer {
	
	public void play(MusicaDTO musica);
	
	public void pause();
	
	public void stop();
}