package com.player;

import com.dto.MusicDTO;
import com.thread.GenericThread;
import com.thread.ThreadMP3;

public class PlayerManager implements MusicPlayer {

	private GenericThread currentThread;
	
	@SuppressWarnings("deprecation")
	@Override
	public void play(MusicDTO music) {
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

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}
}