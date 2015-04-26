package com.thread;

import javax.swing.JSlider;

public class ThreadProgressBar extends GenericThread {

	private JSlider bar;
	private int value;
	private int max;
	
	private final int SLEEP_INTERVAL = 500;

	public ThreadProgressBar(int value, int max, JSlider bar) {
		this.value = value;
		this.max = max;
		this.bar = bar;
	}

	@Override
	public void run() {
		while (value < max) {
			bar.setValue(100 * value / max);
			try {
				Thread.sleep(SLEEP_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			value += SLEEP_INTERVAL;
		}
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
