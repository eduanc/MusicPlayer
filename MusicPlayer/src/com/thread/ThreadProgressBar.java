package com.thread;

import javax.swing.JLabel;
import javax.swing.JSlider;

public class ThreadProgressBar extends Thread {

	private JSlider bar;
	private JLabel lblTime;
	private int value;
	private int max;
	
	private final int SLEEP_INTERVAL = 500;

	public ThreadProgressBar(int value, int max, JSlider bar, JLabel lblTime) {
		this.value = value;
		this.max = max;
		this.bar = bar;
		this.lblTime = lblTime;
	}

	@Override
	public void run() {
		while (value < max) {
			bar.setValue(100 * value / max);
			
			//System.out.println((value / (60000) % 60) + ":" + (value / 1000) % 60 + " (" + (max / (60000) % 60) + ":" + (max / 1000) % 60 + ")");
			try {
				Thread.sleep(SLEEP_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			value += SLEEP_INTERVAL;
			
			StringBuilder time = new StringBuilder();
			time.append((value / (60000) % 60));
			time.append(":");
			time.append((value / 1000) % 60 < 10 ? "0" : "");
			time.append((value / 1000) % 60);
			time.append(" (");
			time.append((max / (60000) % 60) );
			time.append(":");
			time.append((max / 1000) % 60 < 10 ? "0" : "");
			time.append((max / 1000) % 60);
			time.append(")");
			lblTime.setText(time.toString());
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
