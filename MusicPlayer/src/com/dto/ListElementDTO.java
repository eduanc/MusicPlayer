package com.dto;

public class ListElementDTO implements Comparable<ListElementDTO> {

	private MusicDTO music;
	private int position;
	
	public ListElementDTO(MusicDTO music, int position) {
		this.music = music;
		this.position = position;
	}

	public MusicDTO getMusic() {
		return music;
	}

	public void setMusic(MusicDTO music) {
		this.music = music;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	@Override
	public int compareTo(ListElementDTO o) {
		if (this.getPosition() > o.getPosition()) {
			return 1;
		} else if (this.getPosition() < o.getPosition()) {
			return -1;
		} else {
			return this.getMusic().getName().compareTo(o.getMusic().getName());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ListElementDTO [music=");
		builder.append(music);
		builder.append(", position=");
		builder.append(position);
		builder.append("]");
		return builder.toString();
	}

}
