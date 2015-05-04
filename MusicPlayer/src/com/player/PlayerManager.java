package com.player;

import com.dao.MusicDAO;
import com.dto.MusicDTO;
import com.gui.PlayerGUI;
import com.thread.ThreadMP3;
import com.thread.ThreadOGG;
import com.thread.ThreadWAV;

public class PlayerManager implements MusicPlayer {

	private Thread currentThread;

	@SuppressWarnings("deprecation")
	@Override
	public void play(int begin, MusicDTO music) {
		if (this.currentThread == null) {
			switch (music.getFormat()) {
			case 0:
				this.currentThread = new ThreadMP3(begin / 26, music);
				break;
			case 1:
				this.currentThread = new ThreadWAV(music);
				break;
			case 2:
				this.currentThread = new ThreadOGG(music);
				break;
			default:
				break;
			}
			
			this.currentThread.start();
			PlayerGUI.startProgressBar(begin);
			
			if (begin == 0) {
				new MusicDAO().incrementMostPlayed(music);
			}
		} else if (this.currentThread.isAlive()) {
			this.currentThread.resume();
			PlayerGUI.resumeProgressBar();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void pause() {
		if (this.currentThread != null) {
			this.currentThread.suspend();
			PlayerGUI.pauseProgressBar();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void stop() {
		if (this.currentThread != null) {
			this.currentThread.stop();
			this.currentThread = null;
			PlayerGUI.stopProgressBar();
		}
	}

	@Override
	public void change(MusicDTO music) {
		if (this.currentThread != null) {
			this.stop();
		}

		this.play(0, music);
	}

	@Override
	public void autoChange(MusicDTO music) {
		this.currentThread = null;
		this.play(0, music);
	}

	public void askForNext() {
		PlayerGUI.autoChangeMusic(1);
	}
}