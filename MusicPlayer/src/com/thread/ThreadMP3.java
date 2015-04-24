package com.thread;

import java.io.FileInputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;

import com.dto.MusicDTO;
import com.gui.PlayerGUI;

public class ThreadMP3 extends GenericThread {
	
	private MusicDTO music;
	private int begin;
	
	public ThreadMP3(int begin, MusicDTO music) {
		this.music = music;
		this.begin = begin;
	}
	
	@Override
	public void run() {
		try {
			new AdvancedPlayer(new FileInputStream(this.music.getFile().getAbsolutePath())).play(begin, Integer.MAX_VALUE);
			
			/*  passa para a proxima musica quando essa acabar
			 *  quando uma musica acaba, o selected na GUI vai para null
			 *  dai o index vira -1, entao tem que passar a posicao atual dessa musica +2
			 *  +1 para anular o index -1 e +1 para ir para a proxima musica  			
			 */
			new PlayerGUI().changeMusic(this.music.getPosition()+2);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}