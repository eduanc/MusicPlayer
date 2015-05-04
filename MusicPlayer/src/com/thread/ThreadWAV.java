package com.thread;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.dto.MusicDTO;

public class ThreadWAV extends Thread {

	private MusicDTO music;

	public ThreadWAV(MusicDTO music) {
		this.music = music;
	}

	public void run() {
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(music.getFile().getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	};
}