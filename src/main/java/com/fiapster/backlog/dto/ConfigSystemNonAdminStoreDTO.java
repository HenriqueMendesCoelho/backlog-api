package com.fiapster.backlog.dto;

import java.io.Serializable;

public class ConfigSystemNonAdminStoreDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int preco_pontual;
	private int preco_coroa;
	private int preco_superLimpo;
	private int preco_persistente;
	private int preco_cruz;
	private int preco_alcool;
	
	public ConfigSystemNonAdminStoreDTO() {
		
	}

	public int getPreco_pontual() {
		return preco_pontual;
	}

	public void setPreco_pontual(int preco_pontual) {
		this.preco_pontual = preco_pontual;
	}

	public int getPreco_coroa() {
		return preco_coroa;
	}

	public void setPreco_coroa(int preco_coroa) {
		this.preco_coroa = preco_coroa;
	}

	public int getPreco_superLimpo() {
		return preco_superLimpo;
	}

	public void setPreco_superLimpo(int preco_superLimpo) {
		this.preco_superLimpo = preco_superLimpo;
	}

	public int getPreco_persistente() {
		return preco_persistente;
	}

	public void setPreco_persistente(int preco_persistente) {
		this.preco_persistente = preco_persistente;
	}

	public int getPreco_cruz() {
		return preco_cruz;
	}

	public void setPreco_cruz(int preco_cruz) {
		this.preco_cruz = preco_cruz;
	}

	public int getPreco_alcool() {
		return preco_alcool;
	}

	public void setPreco_alcool(int preco_alcool) {
		this.preco_alcool = preco_alcool;
	}
	
}
