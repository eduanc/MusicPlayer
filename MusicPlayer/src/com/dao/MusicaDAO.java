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

import com.dto.MusicaDTO;
import com.util.DataUtil;

public class MusicaDAO {

	final static String NOMEDOARQUIVO = "playlist";
	final static String LOCALHOST = "xml/";

	public boolean gravar(List<MusicaDTO> lista) {
		Element config = new Element("playlist");
		Document documento = new Document(config);
		
		Element data = new Element("data");
		data.setText(DataUtil.DataHoraForStringPadraoH(new Date()));
		config.addContent(data);
		
		for (MusicaDTO item : lista) {
			Element musica = new Element("musica");
			
			Element nome = new Element("nome").setText(item.getNome());
			Element autor = new Element("autor").setText(item.getAutor());
			Element arquivo = new Element("arquivo").setText(item.getArquivo().getAbsolutePath());
			Element posicao = new Element("posicao").setText(item.getPosicao()+"");
			Element duracao = new Element("duracao").setText(item.getDuracao()+"");
			Element formato = new Element("formato").setText(item.getFormato()+"");
			
			musica.addContent(nome);
			musica.addContent(autor);
			musica.addContent(arquivo);
			musica.addContent(posicao);
			musica.addContent(duracao);
			musica.addContent(formato);
			config.addContent(musica);
		}
		
		XMLOutputter xout = new XMLOutputter();
		try {
			//criando o arquivo de saida
			BufferedWriter arquivo = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOCALHOST +  NOMEDOARQUIVO + ".xml"),"UTF-8"));
			//imprimindo o xml no arquivo
			xout.output(documento, arquivo);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
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
