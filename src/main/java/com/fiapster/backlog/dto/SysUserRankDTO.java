package com.fiapster.backlog.dto;

import java.io.Serializable;

public class SysUserRankDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	private int pontos;
	
	private String cargo;
	
	private int nivel;
	
	private String itens;

	public SysUserRankDTO() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getItens() {
		return itens;
	}

	public void setItens(String itens) {
		this.itens = itens;
	}
	
}
