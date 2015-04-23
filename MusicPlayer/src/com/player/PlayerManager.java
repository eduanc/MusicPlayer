package com.player;

import com.dto.MusicaDTO;
import com.thread.GenericThread;
import com.thread.ThreadMP3;

public class PlayerManager implements MusicPlayer {

	private GenericThread currentThread;
	
	@SuppressWarnings("deprecation")
	@Override
	public void play(MusicaDTO music) {
		if(currentThread == null){			
			this.currentThread = new ThreadMP3(music);
			this.currentThread.start();			
		} else if(this.currentThread.isPaused()){
			this.currentThread.resume();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void pause() {
		this.currentThread.setPaused(true);
		this.currentThread.suspend();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void stop() {
		this.currentThread.stop();
		this.currentThread = null;
	}
}