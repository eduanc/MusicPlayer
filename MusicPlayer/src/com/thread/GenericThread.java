package com.thread;

public class GenericThread extends Thread {

	private boolean paused;

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}	
}