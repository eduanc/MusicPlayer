package com.player;

import com.dto.MusicDTO;
import com.thread.GenericThread;
import com.thread.ThreadMP3;

public class PlayerManager implements MusicPlayer {

	private GenericThread currentThread;
	
	@SuppressWarnings("deprecation")
	@Override
	public void play(int begin, MusicDTO music) {
		if(this.currentThread == null){			
			this.currentThread = new ThreadMP3(begin, music);
			this.currentThread.start();			
		} else if(this.currentThread.isAlive()){
			this.currentThread.resume();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void pause() {		
		this.currentThread.suspend();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void stop() {
		this.currentThread.stop();
		this.currentThread = null;
	}
	
	@Override
	public void change(MusicDTO music){
		if(this.currentThread != null){
			this.stop();
		}
		
		this.play(0, music);
	}
}