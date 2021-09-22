package com.fiapster.backlog.dto;

import java.io.Serializable;

public class ValidaLoginDTO implements Serializable{
	private static final long serialVersionUID = 1L;
		
	private String senha;
	
	public ValidaLoginDTO() {
		
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
