package com.dto;

import java.io.File;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import com.dao.MusicDAO;

public class MusicDTO implements Comparable<MusicDTO> {
	public static final int F_DESCONHECIDO = -1;
	public static final int F_MP3 = 0;
	public static final int F_WAV = 1;
	public static final int F_OGG = 2;
	public static final int F_FLAC = 3;

	private String name;
	private String author;
	private String album;
	private File file;
	private int position;
	private int duration;
	private int format;
	
	public MusicDTO() {
		
	}
	
	public MusicDTO(File file, int position) {
		this.file = file;
		this.position = position;
		try {
			new MusicDAO().loadMetaData(this);
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
		}
	}

	public int readFormat() {
		String[] split = file.getName().split ("\\.");
		String format = split[split.length - 1].toLowerCase();
		
		//TODO: outros formatos
		
		switch (format) {
		case "mp3":
			return F_MP3;			
		case "wav":
			return F_WAV;
		case "ogg":
			return F_OGG;
		case "flac":
			return F_FLAC;
		default:
			return F_DESCONHECIDO;
		}		 	
	}
			
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getFormat() {
		return format;
	}

	public void setFormat(int format) {
		this.format = format;
	}
	
	@Override
	public int compareTo(MusicDTO o) {
		if (this.getPosition() > o.getPosition()) {
			return 1;
		} else if (this.getPosition() < o.getPosition()) {
			return -1;
		} else {
			return this.getName().compareTo(o.getName());
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof MusicDTO
				&& ((MusicDTO) obj).getFile().getAbsolutePath().equals(this.getFile().getAbsolutePath());
	}

	@Override
	public int hashCode() {
		return file.getAbsolutePath().hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MusicDTO [name=");
		builder.append(name);
		builder.append(", author=");
		builder.append(author);
		builder.append(", album=");
		builder.append(album);
		builder.append(", file=");
		builder.append(file);
		builder.append(", position=");
		builder.append(position);
		builder.append(", duration=");
		builder.append(duration);
		builder.append(", format=");
		builder.append(format);
		builder.append("]");
		return builder.toString();
	}
}