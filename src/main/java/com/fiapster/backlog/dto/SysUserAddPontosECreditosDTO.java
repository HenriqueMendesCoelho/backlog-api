package com.fiapster.backlog.dto;

import java.io.Serializable;

public class SysUserAddPontosECreditosDTO implements Serializable{
	private static final long serialVersionUID = 1L;
		
	private String email;
	private int pontos;
	private int creditos;
	
	public SysUserAddPontosECreditosDTO() {	
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}
}
