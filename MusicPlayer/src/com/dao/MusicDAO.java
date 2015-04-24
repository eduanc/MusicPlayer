package com.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.dto.MusicDTO;
import com.util.DataUtil;

public class MusicDAO {

	final static String FILENAME = "playlist";
	final static String LOCALHOST = "xml/";

	public boolean imprint(List<MusicDTO> list) {
		Element config = new Element("playlist");
		Document document = new Document(config);
		
		Element date = new Element("date");
		date.setText(DataUtil.DataHoraForStringPadraoH(new Date()));
		config.addContent(date);
		
		for (MusicDTO item : list) {
			Element music = new Element("music");
			
			Element name = new Element("name").setText(item.getName());
			Element author = new Element("author").setText(item.getAuthor());
			Element file = new Element("file").setText(item.getFile().getAbsolutePath());
			Element position = new Element("position").setText(item.getPosition()+"");
			Element duration = new Element("duration").setText(item.getDuration()+"");
			Element format = new Element("format").setText(item.getFormat()+"");
			
			music.addContent(name);
			music.addContent(author);
			music.addContent(file);
			music.addContent(position);
			music.addContent(duration);
			music.addContent(format);
			config.addContent(music);
		}
		
		XMLOutputter xout = new XMLOutputter();
		try {
			//criando o arquivo de saida
			BufferedWriter file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOCALHOST +  FILENAME + ".xml"),"UTF-8"));
			//imprimindo o xml no arquivo
			xout.output(document, file);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<MusicDTO> read() {

		List<MusicDTO> list = new ArrayList<MusicDTO>();

		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(LOCALHOST + FILENAME + ".xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Element config = doc.getRootElement();
		List playlist = config.getChildren("music");
		
		for (Iterator item = playlist.iterator(); item.hasNext();) {
			Element element = (Element) item.next();
			MusicDTO music = new MusicDTO();
			music.setName(element.getChildText("name"));
			music.setAuthor(element.getChildText("author"));
			music.setFile(new File(element.getChildText("file")));
			music.setPosition(Integer.parseInt(element.getChildText("position")));
			music.setDuration(Integer.parseInt(element.getChildText("duration")));
			music.setFormat(Integer.parseInt(element.getChildText("format")));
			list.add(music);
		}
		
		Collections.sort(list);
		
		return list;
	}
}