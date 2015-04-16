package com.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.dto.MusicaDTO;

public class MusicaDAO {

	final static String NOMEDOARQUIVO = "playlist";
	final static String LOCALHOST = "xml/";

	public void gravar(List<MusicaDTO> lista) {

	}

	public List<MusicaDTO> ler() {

		List<MusicaDTO> lista = new ArrayList<MusicaDTO>();

		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(LOCALHOST + NOMEDOARQUIVO + ".xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Element config = doc.getRootElement();
		List playlist = config.getChildren("musica");
		
		for (Iterator item = playlist.iterator(); item.hasNext();) {
			Element element = (Element) item.next();
			MusicaDTO musica = new MusicaDTO();
			musica.setNome(element.getChildText("nome"));
			musica.setAutor(element.getChildText("autor"));
			musica.setArquivo(new File(element.getChildText("arquivo")));
			musica.setPosicao(Integer.parseInt(element.getChildText("posicao")));
			musica.setDuracao(Integer.parseInt(element.getChildText("duracao")));
			musica.setFormato(Integer.parseInt(element.getChildText("formato")));
			lista.add(musica);
		}
		
		Collections.sort(lista);
		
		return lista;
	}

}
