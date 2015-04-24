package com.dto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MusicDTO implements Comparable<MusicDTO> {
	public static final int F_DESCONHECIDO = -1;
	public static final int F_MP3 = 0;
	public static final int F_WAV = 1;
	public static final int F_OGG = 2;
	public static final int F_FLAC = 3;

	private String name;
	private String author;
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
			loadMetaData();
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
		}
	}

	private int readFormat() {
		String format = file.getName().split("\\.", -1)[0].toLowerCase();
		
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
	
	public void loadMetaData() throws IOException, SAXException, TikaException {
		InputStream input = new FileInputStream(file);
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		input.close();
		
		this.setName(metadata.get("title") != null ? metadata.get("title") : "");		
		this.setAuthor(metadata.get("meta:author") != null ? metadata.get("meta:author") : "");
		this.setDuration(metadata.get("xmpDM:duration") != null ? (int) Float.parseFloat(metadata.get("xmpDM:duration")) : 0);
		this.setFormat(readFormat());
		
		// List all metadata
		/*
		String[] metadataNames = metadata.names();
		 
		for(String name : metadataNames){
			System.out.println(name + ": " + metadata.get(name));
		}*/
	}
	
	public void writeMetadata () throws IOException, SAXException, TikaException {
		InputStream input = new FileInputStream(file);
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		
		metadata.set("title", this.getName());
		metadata.set("meta:author", this.getAuthor());
		metadata.set("xmpDM:duration", (int) this.getDuration()+"");
		
		input.close();
		System.out.println(metadata.get("title"));
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
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MusicDTO [name=");
		builder.append(name);
		builder.append(", author=");
		builder.append(author);
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