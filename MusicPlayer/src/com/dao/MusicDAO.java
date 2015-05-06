package com.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.xml.sax.SAXException;

import com.dto.ListElementDTO;
import com.dto.MusicDTO;
import com.util.DataUtil;

public class MusicDAO {

	private String path = "";
	private final String MOST_PLAYED_PATH = "xml/mostPlayed.xml";

	public MusicDAO(){
		
	}
	
	public MusicDAO(String path){		
		this.path = path;
	}
			
	public boolean imprint(List<ListElementDTO> list) {
		Element config = new Element("playlist");
		Document document = new Document(config);
		
		Element date = new Element("date");
		date.setText(DataUtil.DataHoraForStringPadraoH(new Date()));
		config.addContent(date);
		
		for (ListElementDTO item : list) {
			Element music = new Element("music");
			
			Element name = new Element("name").setText(item.getMusic().getName());
			Element author = new Element("author").setText(item.getMusic().getAuthor());
			Element album = new Element("album").setText(item.getMusic().getAlbum());
			Element file = new Element("file").setText(item.getMusic().getFile().getAbsolutePath());
			//Element position = new Element("position").setText(item.getPosition()+"");
			Element duration = new Element("duration").setText(item.getMusic().getDuration()+"");
			Element format = new Element("format").setText(item.getMusic().getFormat()+"");
			
			music.setAttribute("position", item.getPosition()+"");
			music.addContent(name);
			music.addContent(author);
			music.addContent(album);
			music.addContent(file);
			//music.addContent(position);
			music.addContent(duration);
			music.addContent(format);
			config.addContent(music);
		}
		
		XMLOutputter xout = new XMLOutputter();
		try {
			//criando o arquivo de saida			
			BufferedWriter file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),"UTF-8"));
			//imprimindo o xml no arquivo
			xout.output(document, file);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<ListElementDTO> read() {

		List<ListElementDTO> list = new ArrayList<ListElementDTO>();

		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Element config = doc.getRootElement();
		List playlist = config.getChildren("music");
		
		for (Iterator item = playlist.iterator(); item.hasNext();) {
			Element element = (Element) item.next();
			MusicDTO music = new MusicDTO();
			music.setFile(new File(element.getChildText("file")));
			if (music.getFile().exists()) {
				music.setName(element.getChildText("name"));
				music.setAuthor(element.getChildText("author"));
				music.setAlbum(element.getChildText("album"));
				music.setDuration(Integer.parseInt(element.getChildText("duration")));
				music.setFormat(Integer.parseInt(element.getChildText("format")));
				int position = Integer.parseInt(element.getAttributeValue("position"));
				list.add(new ListElementDTO(music, position));
			}
		}
		
		Collections.sort(list);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<MusicDTO, Integer> readMostPlayed() {
		
		HashMap<MusicDTO, Integer> map = new HashMap<MusicDTO,Integer>();
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(MOST_PLAYED_PATH);
		} catch (Exception e) {
			//e.printStackTrace();
			return new HashMap<MusicDTO, Integer>();
		}
		
		Element config = doc.getRootElement();
		List<Element> playlist = config.getChildren("music");
		
		for (Iterator<Element> item = playlist.iterator(); item.hasNext();) {
			Element element = (Element) item.next();
			MusicDTO music = new MusicDTO();
			music.setName(element.getChildText("name"));
			music.setAuthor(element.getChildText("author"));
			music.setAlbum(element.getChildText("album"));
			music.setFile(new File(element.getChildText("file")));
			music.setDuration(Integer.parseInt(element.getChildText("duration")));
			music.setFormat(Integer.parseInt(element.getChildText("format")));
			int counter = Integer.parseInt(element.getChildText("counter"));
			map.put(music, counter);
		}
		
		return map;
	}
	
	public Integer getMostPlayedTotal() {
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(MOST_PLAYED_PATH);
		} catch (Exception e) {
			//we.printStackTrace();
			return 0;
		}
		
		Element config = doc.getRootElement();
		if (config.getChildText("total") != null) {
			return Integer.parseInt(config.getChildText("total"));
		} else {
			return 0;
		}
	}
	
	public boolean imprintMostPlayed(HashMap<MusicDTO, Integer> map) {
		Element config = new Element("playlist");
		Document document = new Document(config);
		
		Element date = new Element("date");
		date.setText(DataUtil.DataHoraForStringPadraoH(new Date()));
		config.addContent(date);
		
		Iterator<Map.Entry<MusicDTO, Integer>> it = map.entrySet().iterator();
		int total = 0;
	    while (it.hasNext()) {
	        Map.Entry<MusicDTO, Integer> pair = it.next();
	        MusicDTO item = pair.getKey();
	        int itemCounter = pair.getValue();
	        Element music = new Element("music");
			
			Element name = new Element("name").setText(item.getName());
			Element author = new Element("author").setText(item.getAuthor());
			Element album = new Element("album").setText(item.getAlbum());
			Element file = new Element("file").setText(item.getFile().getAbsolutePath());
			Element duration = new Element("duration").setText(item.getDuration()+"");
			Element format = new Element("format").setText(item.getFormat()+"");
			Element counter = new Element("counter").setText(itemCounter+"");
			
			music.addContent(name);
			music.addContent(author);
			music.addContent(album);
			music.addContent(file);
			music.addContent(duration);
			music.addContent(format);
			music.addContent(counter);
			config.addContent(music);
			
			total += itemCounter;
	        
	        it.remove();
	    }
	    
	    Element elTotal = new Element("total");
	    elTotal.setText(total+"");
	    config.addContent(elTotal);
	    
	    XMLOutputter xout = new XMLOutputter();
		try {
			//criando o arquivo de saida			
			BufferedWriter file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MOST_PLAYED_PATH),"UTF-8"));
			//imprimindo o xml no arquivo
			xout.output(document, file);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	public void incrementMostPlayed(MusicDTO music) {
		HashMap<MusicDTO, Integer> map = readMostPlayed();
		int c = map.get(music) == null ? 1 : map.get(music) + 1;
		map.put(music, c);
		imprintMostPlayed(map);
	}
	
	public void edit(List<ListElementDTO> playlist, MusicDTO music){
		
		try {
			writeMetadata(music);
		} catch (IOException | SAXException  e) {
			e.printStackTrace();
		}
		
		if(!path.equals("")){
			imprint(playlist);
		}
	}
		
	public void writeMetadata(MusicDTO music) throws IOException, SAXException {			
		AudioFile f = null;
		try {
			f = AudioFileIO.read(music.getFile());
		} catch (CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		
		Tag tag =  f.getTag();
						
		try {
			tag.setField(FieldKey.ARTIST, music.getAuthor());
			tag.setField(FieldKey.TITLE, music.getName());
			tag.setField(FieldKey.ALBUM, music.getAlbum());			
			
		} catch (KeyNotFoundException | FieldDataInvalidException e) { 
			e.printStackTrace();
		}
			
		try {
			AudioFileIO.write(f);
			f.commit();
		} catch (CannotWriteException e) {
			e.printStackTrace();
		}		
		
		loadMetaData(music);
	}
	
	public void loadMetaData(MusicDTO music) throws IOException, SAXException {				      
		AudioFile f = null;
		try {
			f = AudioFileIO.read(music.getFile());
			AudioHeader audioHeader = f.getAudioHeader();
			Tag tag = f.getTag();		
			
			music.setAuthor(tag.getFirst(FieldKey.ARTIST));
			music.setName(tag.getFirst(FieldKey.TITLE));
			music.setAlbum(tag.getFirst(FieldKey.ALBUM));
			music.setDuration(audioHeader.getTrackLength()*1000);
		} catch (CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
			e.printStackTrace();
			music.setName(music.getFile().getName());
		}
		
		music.setFormat(music.readFormat());			
	}
			
	/*
	public void writeMetadata(MusicDTO music) throws IOException, SAXException, TikaException {		
		InputStream input = new FileInputStream(music.getFile());
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		
		metadata.set("title", music.getName());
		metadata.set("meta:author", music.getAuthor());
		metadata.set("xmpDM:duration", music.getDuration()+"");										
						
	
		System.out.println(input.available());
		
		/*
	    byte[] buffer = new byte[input.available()];
	    input.read(buffer);
	 	    
	    
	    File targetFile = new File(music.getFile().getAbsolutePath());
	    OutputStream outStream = new FileOutputStream(targetFile);
	    outStream.write(buffer);
	    outStream.close();
		input.close();
	    
		System.out.println(metadata.get("meta:author"));
		//loadMetaData(music);
		 * 		 
	}		
	
	public void loadMetaData(MusicDTO music) throws IOException, SAXException, TikaException {
		InputStream input = new FileInputStream(music.getFile());
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		input.close();
		
		System.out.println(metadata.get("meta:author"));
		
		music.setName(metadata.get("title") != null ? metadata.get("title") : "");		
		music.setAuthor(metadata.get("meta:author") != null ? metadata.get("meta:author") : "");
		music.setDuration(metadata.get("xmpDM:duration") != null ? (int) Float.parseFloat(metadata.get("xmpDM:duration")) : 0);
		music.setFormat(music.readFormat());
		
		
		
		// List all metadata
		/*
		String[] metadataNames = metadata.names();
		 
		for(String name : metadataNames){
			System.out.println(name + ": " + metadata.get(name));
		}
	}*/
}