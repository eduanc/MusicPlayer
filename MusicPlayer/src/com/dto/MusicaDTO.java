package com.dto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class MusicaDTO implements Comparable<MusicaDTO> {
	public static final int F_DESCONHECIDO = -1;
	public static final int F_MP3 = 0;
	public static final int F_WAV = 1;
	public static final int F_OGG = 2;

	String nome;
	String autor;
	File arquivo;
	int posicao;
	int duracao;
	int formato;
	
	public MusicaDTO() {
		
	}
	
	public MusicaDTO(File file, int posicao) {
		this.arquivo = file;
		this.posicao = posicao;
		try {
			loadMetadata();
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public int getFormato() {
		return formato;
	}

	public void setFormato(int formato) {
		this.formato = formato;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	@Override
	public int compareTo(MusicaDTO o) {
		if (this.getPosicao() > o.getPosicao()) {
			return 1;
		} else if (this.getPosicao() < o.getPosicao()) {
			return -1;
		} else {
			return this.getNome().compareTo(o.getNome());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MusicaDTO [nome=");
		builder.append(nome);
		builder.append(", autor=");
		builder.append(autor);
		builder.append(", arquivo=");
		builder.append(arquivo);
		builder.append(", posicao=");
		builder.append(posicao);
		builder.append(", duracao=");
		builder.append(duracao);
		builder.append(", formato=");
		builder.append(formato);
		builder.append("]");
		return builder.toString();
	}
	
	private int lerFormato() {
		
		if (arquivo.getName().split("\\.", -1)[0].equalsIgnoreCase("mp3")) {
			return F_MP3;
		} else if (arquivo.getName().split("\\.", -1)[0].equalsIgnoreCase("wav")) {
			return F_WAV;
		} else if (arquivo.getName().split("\\.", -1)[0].equalsIgnoreCase("ogg")) {
			return F_OGG;
		} else {
			return F_DESCONHECIDO;
		}
	}
	
	public void loadMetadata() throws IOException, SAXException, TikaException {
		InputStream input = new FileInputStream(arquivo);
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		input.close();
		
		this.setNome(metadata.get("title") != null ? metadata.get("title") : "");
		this.setAutor(metadata.get("meta:author") != null ? metadata.get("meta:author") : "");
		this.setDuracao(metadata.get("xmpDM:duration") != null ? (int) Float.parseFloat(metadata.get("xmpDM:duration"))/1000 : 0);
		this.setFormato(lerFormato());
		
		// List all metadata
		/*String[] metadataNames = metadata.names();
		 
		for(String name : metadataNames){
			System.out.println(name + ": " + metadata.get(name));
		}*/
	}
	
	public void writeMetadata () throws IOException, SAXException, TikaException {
		InputStream input = new FileInputStream(arquivo);
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		
		metadata.set("title", this.getNome());
		metadata.set("meta:author", this.getAutor());
		metadata.set("xmpDM:duration", (int) this.getDuracao()*1000+"");
		
		input.close();
		System.out.println(metadata.get("title"));
	}
	
	
}
