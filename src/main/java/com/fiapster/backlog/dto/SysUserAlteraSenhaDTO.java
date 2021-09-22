package com.fiapster.backlog.dto;

import java.io.Serializable;

public class SysUserAlteraSenhaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
		
	private String senhaAtual;
	private String novaSenha;
	
	public SysUserAlteraSenhaDTO() {
		
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}
}
