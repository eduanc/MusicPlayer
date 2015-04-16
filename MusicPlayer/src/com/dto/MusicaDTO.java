package com.dto;

import java.io.File;

public class MusicaDTO implements Comparable<MusicaDTO> {
	public static final int F_MP3 = 0;
	public static final int F_WAV = 1;
	public static final int F_OGG = 2;

	String nome;
	String autor;
	File arquivo;
	int posicao;
	int duracao;
	int formato;

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
	
	
}
