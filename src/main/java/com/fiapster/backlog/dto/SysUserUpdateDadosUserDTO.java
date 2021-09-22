package com.fiapster.backlog.dto;

import java.io.Serializable;

public class SysUserUpdateDadosUserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
		
	private String nome;
	private String email;
	private String cargo;
	
	public SysUserUpdateDadosUserDTO() {
		
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getCargo() {
		return cargo;
	}
	
}
