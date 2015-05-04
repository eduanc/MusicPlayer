package com.thread;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.dto.MusicDTO;

public class ThreadOGG extends Thread {
	
	private MusicDTO music;

	public ThreadOGG(MusicDTO music) {
		this.music = music;
	}

	public void run() {
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(music.getFile());
			
			AudioInputStream din = null;
						
			AudioFormat base = in.getFormat();
			AudioFormat decoded = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED, 
					base.getSampleRate(), 
					16, 
					base.getChannels(), 
					base.getChannels() * 2, 
					base.getSampleRate(), 
					false);

			din = AudioSystem.getAudioInputStream(decoded, in);
			
			rawplay(decoded, din);
			
			in.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException, LineUnavailableException {
		byte[] data = new byte[4096];
		SourceDataLine line = getLine(targetFormat);
		
		if (line != null) {					
			line.start();
			int nBytesRead = 0; 
			//int nBytesWritten = 0;
			while (nBytesRead != -1) {
				nBytesRead = din.read(data, 0, data.length);
				if (nBytesRead != -1)
					//nBytesWritten = 
					line.write(data, 0, nBytesRead);
			}
						
			line.drain();
			line.stop();
			line.close();
			din.close();
		}
	}

	private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
		SourceDataLine res = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioFormat));
		res.open(audioFormat);
		return res;
	}
}